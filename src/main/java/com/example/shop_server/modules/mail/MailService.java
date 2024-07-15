package com.example.shop_server.modules.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    public JavaMailSender emailSender;
    public void sendMail(Option option) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("projectmodules5@gmail.com");

        String[] toArray = new String[option.getTo().size()];
        option.getTo().toArray(toArray);
        message.setTo(toArray);

        message.setSubject(option.getSubject());
        message.setText(option.getText());
        emailSender.send(message);
    }
    public void sendMailHtml(Option option) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("projectmodules5@gmail.com");
            helper.setTo(option.getTo().toArray(new String[0]));
            helper.setSubject(option.getSubject());

            // Sử dụng nội dung từ Option
            String htmlContent = option.getText();
            helper.setText(htmlContent, true); // true indicates that the text is HTML

            emailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý exception
            e.printStackTrace();
        }
    }




}
