package com.snapdeal.gohack.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class EmailConfig {


	@Value("${email.host:smtp.gmail.com}")
	private String host;


	@Value("${email.port:587}")
	private String port;

	@Value("${email.username:wth_mailer@snapdeal.com}")
	private String userName;

	@Value("${email.password:HOLman@124}")
	private String password;

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
		mailSender.setUsername(userName);
		mailSender.setPassword(password);
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}
}
