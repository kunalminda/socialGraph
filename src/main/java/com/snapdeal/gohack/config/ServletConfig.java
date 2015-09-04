package com.snapdeal.gohack.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.snapdeal.gohack.core.Application;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ServletConfig extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}
