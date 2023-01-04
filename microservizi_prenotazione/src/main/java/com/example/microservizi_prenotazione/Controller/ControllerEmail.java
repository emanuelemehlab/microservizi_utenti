package com.example.microservizi_prenotazione.Controller;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;


@Component
public class ControllerEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendSimpleMessage(String to, String subject, String text) {

        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            mimeMessage.setRecipient(Message.RecipientType.TO,
//                    new InternetAddress(to));
//            mimeMessage.setFrom(new InternetAddress("pietro.vitale23@gmail.com"));
//            mimeMessage.setText(text);
//            mimeMessage.setSubject(subject);
//            mailSender.send(mimeMessage);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setText(text);
            msg.setFrom("mailgratis60@gmail.com");
            msg.setSubject(subject);
            JavaMailSenderImpl mails = new JavaMailSenderImpl();
            mails.setHost("smtp.gmail.com");
            mails.setPort(587);
            mails.setUsername("mailgratis60@gmail.com");
		    mails.setPassword("ehivzvhaurwdlacf");

            Properties props = mails.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            setMailSender(mails);
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }


}
