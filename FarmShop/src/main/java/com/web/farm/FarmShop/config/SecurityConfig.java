package com.web.farm.FarmShop.config;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired// Sử dụng @Lazy để trì hoãn việc khởi tạo bean
    @Lazy
    private AccountService accountService;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //cung cap nguon du lieu
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(username -> {
            try {
                Account user = accountService.findById(username).get();
                String password = bCryptPasswordEncoder.encode(user.getPassword());
                String[] roles = user.getAuthorities().stream()
                        .map(er -> er.getRole().getId())
                        .collect(Collectors.toList()).toArray(new String[0]);
                return User.withUsername(username).password(password).roles(roles).build();
            }catch (NoSuchElementException e){
                throw new UsernameNotFoundException(username + "not found");
            }
        });
    }
    //phan quyen su dung
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/site/cart/**").authenticated()
                .antMatchers("/site/order/**").authenticated()
                .antMatchers("/admin/**").hasAnyRole("ADM")
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/view")
                .defaultSuccessUrl("/login/success",false)
                .failureUrl("/login/error");

        http.exceptionHandling()
                .accessDeniedPage("/security/");
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/site");
    }

    //co che ma hoa mk
    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

