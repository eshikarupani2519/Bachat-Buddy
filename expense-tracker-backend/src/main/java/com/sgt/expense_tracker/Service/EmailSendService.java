package com.sgt.expense_tracker.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(String to,String token){
//        create the message to be sent
        MimeMessage message=mailSender.createMimeMessage();
//        create helper that could help you writing the message
        MimeMessageHelper messageHelper=new MimeMessageHelper(message);
        try{
            messageHelper.setTo(to);
            messageHelper.setSubject("Reset Password-Expense Tracker");
//            messageHelper.setText(token,"<h1>heyy!!trying html for first time</h1>");
            messageHelper.setText(buildHtml(token),true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public String buildHtml(String token){
      return "<div style=\" margin:auto;\">\n" +
              "    <h1 style=\"text-align: center;color: rgb(0, 65, 130);\">Password Reset</h1>\n" +
              "    <p style=\"text-align: center;\">A request for password reset has been made for your account.Below is the password reset link.</p>\n" +
              "    <p style=\"color: red;text-align: center;font-weight: bold;\">The link is active only for 15 minutes.</p>\n" +
              "    <a style=\"padding:1rem;margin:auto;\n" +
              "    background-color: rgb(26, 129, 231); \n" +
              "    color: white;border-radius: 5px;border:none;\" href=\"http://localhost:4200/resetPassword/"+token+"\">Reset password</a>\n" +
              "</div>";
    }
}
