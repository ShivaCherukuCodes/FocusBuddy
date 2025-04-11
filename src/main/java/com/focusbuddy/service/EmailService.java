package com.focusbuddy.service;


import com.focusbuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendWelcomeEmail(User userDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(userDTO.getEmail());
            helper.setSubject("🎉 Welcome to FocusBuddy!");

//            // Load the image from resources
//            ClassPathResource image = new ClassPathResource("static/logo.png");
//            helper.addInline("logoImage", image); // "logoImage" is the cid

            String htmlContent = "<div style='font-family: Arial, sans-serif; text-align: center;'>"
//                    + "<img src='cid:logoImage' alt='FocusBuddy Logo' style='height: 100px; margin-bottom: 20px;'/>"
                    + "<h1 style='color: #2e86de;'>Welcome to FocusBuddy!</h1>"
                    + "<p style='font-size: 16px;'>Hi <b>" + userDTO.getUsername() + "</b>,</p>"
                    + "<p style='font-size: 14px;'>You have successfully registered with FocusBuddy. Stay productive 💪</p>"
                    + "<hr style='margin: 20px 0;'>"
                    + "<p style='font-size: 12px; color: #999;'>This is an automated email, please do not reply.</p>"
                    + "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Or use a proper logger
        }
    }

}
