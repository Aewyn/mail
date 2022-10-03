package be.vdab.mail.mailing;

import be.vdab.mail.domain.Lid;
import be.vdab.mail.exceptions.KanMailNietVerzendenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class LidMailing {
    private final JavaMailSender sender;
    private final String username;

    public LidMailing(JavaMailSender sender, @Value("${spring.mail.username}") String username){
        this.sender = sender;
        this.username = username;
    }
    public void stuurMailNaRegistratie(Lid lid, String ledenURL){
        try{
            var message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(lid.getEmailAdres());
            message.setSubject("Geregistreerd");
            message.setText("Je bent nu lid. Je nummer is: " + lid.getId());
            sender.send(message);
        } catch (MailException e){
            throw new KanMailNietVerzendenException(e);
        }
    }
}
