package br.com.stock.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailUtil {

    @Autowired
    private MailSender mailSender;

    public void sendMail(String to, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Informações das Negociações");
        message.setText(body);
        mailSender.send(message);
    }

}

