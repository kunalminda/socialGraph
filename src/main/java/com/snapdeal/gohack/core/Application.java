package com.snapdeal.gohack.core;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAutoConfiguration
@ComponentScan("com.snapdeal")
@EnableAsync
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);	
	}

}