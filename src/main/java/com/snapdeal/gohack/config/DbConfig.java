package com.snapdeal.gohack.config;
import javax.annotation.Resource;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;



@Configuration
@PropertySource("file:${user.home}/properties/platform.properties")
public class DbConfig {

	@Resource
	private Environment environment;
	
	
	@Value("${mysql.insertstoredProcName:wth_insert}")
	private String insertstoredProcName;
	

	@Value("${mysql.countStoredProcName:wth_count}")
	private String countStoredProcName;


	@Bean
	public DataSource setHackDataSource(){
	     DataSource dataSource = new DataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("mysql.driver"));
		dataSource.setUrl(environment.getRequiredProperty("mysql.jdbcurl"));
		dataSource.setUsername(environment.getRequiredProperty("mysql.user"));
		dataSource.setPassword(environment.getRequiredProperty("mysql.password"));
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate setJdbcTemplate(){
		JdbcTemplate jdbcTemplate= new JdbcTemplate();
		jdbcTemplate.setDataSource(setHackDataSource());
		return jdbcTemplate;
	}
	
	@Bean(name="insert")
	public SimpleJdbcCall setJdbcSimpleCallForInsert(){
		SimpleJdbcCall simpleJdbc= new SimpleJdbcCall(setHackDataSource()).
				withProcedureName(insertstoredProcName);
		return simpleJdbc;
	}
	
	@Bean(name="count")
	public SimpleJdbcCall setJdbcSimpleCallForCount(){
		SimpleJdbcCall simpleJdbc= new SimpleJdbcCall(setHackDataSource()).
				withProcedureName(countStoredProcName);
		return simpleJdbc;
	}


}
