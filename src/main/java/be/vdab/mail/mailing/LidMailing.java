package be.vdab.mail.mailing;

import be.vdab.mail.domain.Lid;
import be.vdab.mail.exceptions.KanMailNietVerzendenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class LidMailing {
    private final JavaMailSender sender;
    private final String username;

    private final String emailAdresWebMaster;

    public LidMailing(JavaMailSender sender, @Value("${spring.mail.username}") String username, @Value("${emailAdresWebMaster}") String emailAdresWebMaster){
        this.sender = sender;
        this.username = username;
        this.emailAdresWebMaster = emailAdresWebMaster;
    }

    @Async
    public void stuurMailNaRegistratie(Lid lid, String ledenURL){
        try{
            var message = sender.createMimeMessage();
            var helper = new MimeMessageHelper(message);
            helper.setFrom(username);
            helper.setTo(lid.getEmailAdres());
            helper.setSubject("Geregistreerd");
            var urlVanDeLidInfo = ledenURL+"/"+lid.getId();
            var tekst = "<h1>Je bent nu lid.</h1>Je nummer is: " + lid.getId() + "." + "Je ziet je info <a href='" + urlVanDeLidInfo + "'>hier</a>";
            helper.setText(tekst, true);
            sender.send(message);
        } catch (MailException | MessagingException  e){
            throw new KanMailNietVerzendenException(e);
        }
    }

    public void stuurMailMetAantalLeden(long aantalLeden){
        try{
            var message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(emailAdresWebMaster);
            message.setSubject("Aantal leden");
            message.setText(aantalLeden + " leden");
            sender.send(message);
        }catch (MailException ex){
            throw new KanMailNietVerzendenException(ex);
        }
    }
}
