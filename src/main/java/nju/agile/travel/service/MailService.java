package nju.agile.travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by echo on 2019/1/8.
 */
@Component
public class MailService {

    @Value("${email.from}")
    private String fromAddress;

    @Value("${email.subject}")
    private String subject;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toAddress, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setText(text);
        message.setSubject(subject);
        mailSender.send(message);
    }

}
