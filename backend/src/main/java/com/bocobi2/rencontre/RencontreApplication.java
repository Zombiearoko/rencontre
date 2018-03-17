package com.bocobi2.rencontre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.bocobi2.rencontre.util.JwtFilter;

//import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class RencontreApplication extends SpringBootServletInitializer   {

	
	
//	@Bean
//    public FilterRegistrationBean jwtFilter() {
//        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new JwtFilter());
//        registrationBean.addUrlPatterns("/api/*");
//
//        return registrationBean;
//    }
	
	
	
	public static void main(String[] args) {
		//SpringApplication.run(RencontreApplication.class, args);
		SpringApplication.run(RencontreApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(RencontreApplication.class);
	}
	
	
	
}
