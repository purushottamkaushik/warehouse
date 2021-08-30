package com.warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)  //  using userDetailService Impl class and use method loadUsername...
                .passwordEncoder(passwordEncoder);  // Password Encoder to decrypt the password 
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        // authorization
        http

                .authorizeRequests()
                .antMatchers("/rest/**","/user/login","/user/setup").permitAll()
                .antMatchers("/user/create/","/user/register").hasAnyAuthority("ADMIN","APPUSER")
                .antMatchers("/uom/**","/st/**","/om/**","/wh/**","/part/**","/doc/**")
                .hasAnyAuthority("ADMIN","APPUSER")
                .antMatchers("/po/**","/grn/**","/so/**","/sp/**")
                .hasAuthority("APPUSER")
                .antMatchers("/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()

                // login details
                .and()
                .formLogin()
                .loginPage("/user/login") // GET Request for showing the page
                .loginProcessingUrl("/user/setup") // URL For Submission
                .defaultSuccessUrl("/user/setup",true)
                .failureUrl("/user/login?error")

                // Logout details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/user/login?logout")
                // Exceptipn Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/denied")

        ;
    }
}
