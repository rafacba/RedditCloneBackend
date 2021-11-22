package com.rafa.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rafa.exceptions.RedditException;
import com.rafa.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	//private final MailContentBuilder mailContentBuilder;
	private final JavaMailSender mailSender;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("rafa@redditClone.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			//messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
			messageHelper.setText(notificationEmail.getBody());
		};
		
		try {
			mailSender.send(messagePreparator);
			//Para usar log debo poner @Slf4j en la clase
			log.info("Activation email sent!!!");
		} catch(MailException e) {
			throw new RedditException("Exception ocurred when sending mail to "+ notificationEmail.getRecipient());
		}
	}

}
