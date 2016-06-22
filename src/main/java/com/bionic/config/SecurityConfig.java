package com.bionic.config;

import com.bionic.config.security.LimitLoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * @author vitalii.levash
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
/*
    @Autowired
    private UserDetailsService userDetailService;
*/
    @Autowired
    private LimitLoginAuthenticationProvider limitLoginAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //    limitLoginAuthenticationProvider.setUserDetailsService(userDetailsService());
        limitLoginAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        auth.authenticationProvider(limitLoginAuthenticationProvider);
       //auth.userDetailsService(userDetailService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointBean())
                .and()
//              .sessionManagement()
//              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//              .and()
//              .sessionManagement()
//              .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/rest/api/users/**")
                .authenticated()
                .and()
                .httpBasic();

        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                // указываем action с формы логина
                .loginProcessingUrl("/j_spring_security_check")
                // указываем URL при неудачном логине
                .failureUrl("/")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutUrl("/logout")
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/")
                // делаем не валидной текущую сессию
                .invalidateHttpSession(true);
    }

    /**
     * @return Crypto password
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return Entry Point
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPointBean() {
        return (request, response, authException) -> response.sendError(SC_UNAUTHORIZED, "Access Denied");
    }
}
