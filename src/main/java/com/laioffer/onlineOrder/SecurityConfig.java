package com.laioffer.onlineOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource; //connect to DB

    //needs to override authentication and authorization methods
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception { //search to get user data
        //authentication manager
        //AuthenticationManagerBuilder -- tell Filter where to find userid and password
        // -method1: inMemoryAuthentication() --> save user dta in memory -- might be risky
        // -method2: jdbcAuthentication() --> Filter class to connect to DB, and go to Tables to find user data
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, enabled FROM customers WHERE email = ?") //sql; ? is a placeholder
                .authoritiesByUsernameQuery("SELECT email, authorities FROM authorities WHERE email = ?");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //Http builder configurations for authorize requests and form login
        //define what API needs authorization
        //csrf -- cross site request forgery attack
        http.csrf().disable()
            .formLogin()
            .failureForwardUrl("/login?error=true");

        //string in .hasAuthority("string") is defined in customerDao, assigned when signUp
        http.authorizeRequests()
                .antMatchers("/order/*", "/cart", "/checkout").hasAnyAuthority("ROLE_USER")
                .anyRequest().permitAll();
    }

    //no password encoder in this proj
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
