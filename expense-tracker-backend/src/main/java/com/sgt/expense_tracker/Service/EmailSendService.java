package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Model.Transactions;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Repository.TransactionRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmailSendService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    Logger logger= LoggerFactory.getLogger(EmailSendService.class);
    public void sendEmail(String to, String token) {
//        create the message to be sent
        MimeMessage message = mailSender.createMimeMessage();
//        create helper that could help you writing the message
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {

            messageHelper.setTo(to);
            messageHelper.setSubject("Reset Password-Expense Tracker");
//            messageHelper.setText(token,"<h1>heyy!!trying html for first time</h1>");
            messageHelper.setText(buildHtml(token), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String buildHtml(String token) {
        return "<div style=\" margin:auto;\">\n" +
                "    <h1 style=\"text-align: center;color: rgb(0, 65, 130);\">Password Reset</h1>\n" +
                "    <p style=\"text-align: center;\">A request for password reset has been made for your account.Below is the password reset link.</p>\n" +
                "    <p style=\"color: red;text-align: center;font-weight: bold;\">The link is active only for 15 minutes.</p>\n" +
                "    <a style=\"padding:1rem;margin:auto;\n" +
                "    background-color: rgb(26, 129, 231); \n" +
                "    color: white;border-radius: 5px;border:none;\" href=\"http://localhost:4200/resetPassword/" + token + "\">Reset password</a>\n" +
                "</div>";
    }

    public void sendReport() throws MessagingException {

        try {
           List<User> users= userRepository.getAllUsers();
            for(int i=0;i<users.size();i++) {
                //        create the message to be sent
                MimeMessage message = mailSender.createMimeMessage();
//        create helper that could help you writing the message
                MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
              byte[] report=  prepareReport(users.get(i));
//                send mail
                messageHelper.setTo(users.get(i).getEmail());
                messageHelper.setSubject("Monthly Report-Bachat buddy");
                Resource resource=new ByteArrayResource(report);

                messageHelper.addAttachment("Monthly report.pdf",resource);
                messageHelper.setText(buildReport(), true);

                mailSender.send(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public String buildReport(){
        return "<h3 style=\"display: flex;justify-content: center;color: dodgerblue;\">\n" +
                "    Your Monthly report for Bachat Buddy\n" +
                "</h3>\n" +
                "<div style=\"display: flex;justify-content: center;\">This is the attached report for your last month from "+ LocalDate.now().minusMonths(1) +" to "+LocalDate.now().minusDays(1)+"  on Bachat Buddy</div>";
    }
    private byte[] prepareReport(User user){
//        study about thime leaf for generating pdfs,html,charts,etc
        LocalDate start=LocalDate.now().minusMonths(1);
        LocalDate end=LocalDate.now().minusDays(1);
//                get transactions
        List<Transactions> trans=transactionRepository.get(user.getId(),null,start,end,null,null,null,null,null);
        logger.info(trans.toString());
//        storing buffer(RAM)
        ByteArrayOutputStream out=new ByteArrayOutputStream();
//        doc jisme likhna hai
        Document document=new Document();
//        link the ram to our doc(store doc in ram)
        PdfWriter.getInstance(document,out);
//        populating document
        document.open();
        document.add(new Paragraph("Monthly report"));
        document.add(new Paragraph("Generated on: "+LocalDate.now()));
        PdfPTable table = new PdfPTable(3);
        table.addCell("Date");
        table.addCell("Category");
        table.addCell("Amount");
        for(Transactions t:trans){
            table.addCell(new Paragraph(t.getDateOfTransaction().toString()));
            table.addCell(new Paragraph(t.getCategory_name()));
            table.addCell(new Paragraph(String.valueOf(t.getAmount())));
        }
        document.add(table);
        document.close();
        return out.toByteArray();

    }
}