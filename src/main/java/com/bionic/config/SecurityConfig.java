/**
 * Security Configuration
 */
package com.bionic.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.bionic.service.UserDetailServiceImpl;

/**
 * @author vitalii.levash
 * @version 0.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(getShaPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/**").access("hasRole('USER') or hasRole('ADMIN')")		                        
		                        .antMatchers("/admin**").access("hasRole('ADMIN')")
		                        .and().formLogin().and().exceptionHandling()
				.accessDeniedPage("/403");

		http.formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check").failureUrl("/login?error")
				.usernameParameter("j_username").passwordParameter("j_password").permitAll();

		http.logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true);
        
	}

	@Bean
	public ShaPasswordEncoder getShaPasswordEncoder() {
		return new ShaPasswordEncoder();
	}
}
