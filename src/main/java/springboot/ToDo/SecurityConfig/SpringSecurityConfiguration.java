package springboot.ToDo.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.function.Function;

@Configuration
//@ConfigurationProperties
public class SpringSecurityConfiguration {

    // 1. LDAP / database use to fetch user details...
    // 2. In memory userDetailsManager ----------> this is used here to start leaning Spr.Secu-session-1

    @Bean
    public InMemoryUserDetailsManager configure_user_detail(){

        Function<String,String> pass_encoder_algo =  input  ->  passwordEncoder_method().encode(input) ;
        UserDetails user11 =
                        User
                        .builder()
                        .passwordEncoder(pass_encoder_algo)
                        .username("s")
                        .password("1")
                        .roles("USER", "ADMIN").build();

        InMemoryUserDetailsManager iMud = new InMemoryUserDetailsManager(user11);
        return  iMud;
    }


    @Bean
    public PasswordEncoder passwordEncoder_method(){
        return new BCryptPasswordEncoder();
    }


}






/*
    @Bean
    public InMemoryUserDetailsManager configure_user_detail1(){
              OLD Deprecated method for password encoding.
                UserDetails user1 =  User.withDefaultPasswordEncoder().username("s").password("1").roles("USER", "ADMIN").build();
                InMemoryUserDetailsManager user1_in_memory = new InMemoryUserDetailsManager(user1);

Function<String, String> encoded_pass = input -> passwordEncoder().encode(input);

UserDetails user1 =
                User
                .builder()
                .passwordEncoder( encoded_pass)
                .username("s")
                .password("1")
                .roles("USER", "ADMIN").build();
InMemoryUserDetailsManager user1_in_memory = new InMemoryUserDetailsManager(user1);
        return  user1_in_memory;
    }

@Bean
public PasswordEncoder passwordEncoder(){
    PasswordEncoder passEncode1 = new BCryptPasswordEncoder();
    return passEncode1;
}
 */