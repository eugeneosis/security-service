package com.ru.microservice.config;


import com.ru.microservice.service.SecurityDetailsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityDetailsService securityDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @SneakyThrows
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
            auth
                .userDetailsService(securityDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @SneakyThrows
    @Override
    protected void configure(HttpSecurity http) {

            http.
                    authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated()
                    .and().csrf().disable()
                    .formLogin()
                    .loginPage("/login")
                    .loginPage("/")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/admin/home")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login").and().exceptionHandling();
    }


    @SneakyThrows
    @Override
    public void configure(WebSecurity web) {
            web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}