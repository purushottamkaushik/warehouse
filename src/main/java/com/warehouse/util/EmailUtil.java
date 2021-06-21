package com.warehouse.util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;


@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender sender;

    public boolean sendEmail(String to, String[] cc, String[] bcc,
                             String subject, String text, MultipartFile multipartFile) {

        boolean sent = false;
        try {
            // Creating mime message
            MimeMessage message = sender.createMimeMessage();
            // Creating a helper to send message
            MimeMessageHelper helper = new MimeMessageHelper(message, multipartFile != null);

            // Setting properties
            helper.setTo(to);
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }
            if (bcc != null && bcc.length > 0) {
                helper.setBcc(bcc);
            }

            helper.setSubject(subject);
            helper.setText(text);

            if (multipartFile != null) {
                helper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
            }
            sender.send(message);
            sent = true;
            // Mail sent
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sent;
    }

    // Overloaded methods for handling the different cases
    public boolean sendEmail(String to, String subject, String text) {
        return sendEmail(to, null, null, subject, text, null);
    }
    // Overloaded methods for handling the different cases
    public boolean sendEmail(String to , String subject , String text, MultipartFile file) {
        return sendEmail(to,null,null,subject,text,file);
    }


}
