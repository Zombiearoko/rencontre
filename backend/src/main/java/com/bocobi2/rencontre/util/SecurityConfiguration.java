package com.bocobi2.rencontre.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import com.bocobi2.rencontre.model.UserDetailsServices;

@Configuration

@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   // @Autowired
   // private UserDetailsService userDetailsService;


    @Autowired
    private UserDetailsServices userDetailsService;

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
    
   // @Bean
   // public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
       // return super.userDetailsService();
   // }

    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**");
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().defaultSuccessUrl("/resource")
                .and().logout().and().authorizeRequests()
                .antMatchers("/index.html", "/home.html", "/login.html", "/", "/access", "/logout").permitAll().anyRequest()
                .authenticated()
                .and().csrf().disable();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}