package com.bocobi2.rencontre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bocobi2.rencontre.model.Customer;
//import com.bocobi2.rencontre.model.Test;
import com.bocobi2.rencontre.repositories.CustomerRepository;

//import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class RencontreApplication extends SpringBootServletInitializer   {

	
	public static void main(String[] args) {
		SpringApplication.run(RencontreApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(RencontreApplication.class);
	}
	
	
}
