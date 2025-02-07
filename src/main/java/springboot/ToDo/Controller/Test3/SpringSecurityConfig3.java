package springboot.ToDo.Controller.Test3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
//@Configuration
public class SpringSecurityConfig3 {

//
//
//    @Configuration
//    @EnableWebSecurity
//    public class SecurityConfig {
//
//        @Bean
//        public class SecurityConfig extends We {
//
//
//            @Bean
//            public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//                httpSecurity.formLogin(i -> i.loginPage("/login").permitAll());
//
//                httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//
//                return  httpSecurity.build();
//            }
//

}
