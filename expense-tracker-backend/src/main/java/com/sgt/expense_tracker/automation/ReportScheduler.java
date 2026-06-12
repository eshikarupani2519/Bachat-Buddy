package com.sgt.expense_tracker.automation;

import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Repository.TransactionRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import com.sgt.expense_tracker.Service.EmailSendService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportScheduler {
    @Autowired
    EmailSendService emailSendService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendReport() throws MessagingException {
        System.out.println("sending report..");


        emailSendService.sendReport();
    }
}
