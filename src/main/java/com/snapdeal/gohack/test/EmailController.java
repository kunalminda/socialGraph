package com.snapdeal.gohack.test;

import java.util.HashMap;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

@RestController
public class EmailController {


	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;

	@RequestMapping(value="/send/email/{email}" ,method=RequestMethod.GET)
	public void  shootEmail(@PathVariable String email)
	{
		shootIdeaSubmissionEmail(email);
	}


	public void shootIdeaSubmissionEmail(final String email){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				HashMap<String,String> templateValues= new HashMap<String, String>();
				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(email));
				mimeMessage.setFrom(new InternetAddress(email));
				mimeMessage.setSubject("Thank you for your submission");
				TemplateLoader loader = new ClassPathTemplateLoader();
				loader.setPrefix("/templates");
				loader.setSuffix(".html");
				Handlebars handlebars = new Handlebars(loader);

				Template template = handlebars.compile("submission");
				String text = template.apply(templateValues);
				mimeMessage.setText(text, "utf-8", "html");
			}
		};

		try {
			this.javaMailSenderImpl.send(preparator);
		}
		catch (MailException ex) {

		}
	}


}
