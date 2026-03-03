package com.sgt.expense_tracker.service;

import com.sgt.expense_tracker.Service.EmailSendService;
import com.sgt.expense_tracker.Service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@SpringBootTest
public class EmailSendServiceTest {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    EmailSendService emailSendService;
    @Test
    public void sendEmail(){
        String to="eshikarupani2510@gmail.com";
        String token="test";
            emailSendService.sendEmail(to,token);
        }

    }

