package springboot.ToDo.SecurityConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@Configuration
@ConfigurationProperties
public class SpringSecurityConfiguration {

    // GEnerally in comapnies, we connect this Clas with below 2 methods to get user login data:
    // 1. LDAP / database use to fetch user details...
    // 2. In memory userDetailsManager ----------> this is used here to start leaning Spr.Secu-session-1

    static {        // These static default values of login to be used for : filterchain_1() method

    }


    // Password encoder algorithm set
    @Bean
    public PasswordEncoder passwordEncoder_method(){
        return new BCryptPasswordEncoder();
    }
    // Create User details Manager - This is exclusively for in memory only.
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

    // HOW any specific url to be excluded / put in exception from login-security module, like health check of bluey..etc...
    // Every chain of request is being passed through this filter automatically by default in spring security...
    //            Without this method  Forbidden Error
    //            =  Whitelabel Error Page :(type=Forbidden, status=403)

    @Bean
    public SecurityFilterChain filterchain_1(HttpSecurity httpSecurity) throws Exception {

        // Step-1 : PROTECT all URLS
        // pass all request through below so all request get authenticated
        httpSecurity.authorizeHttpRequests(
                // pass through any request below function so it get authenticated
                auth1 -> auth1.anyRequest().authenticated()
        );

        // Step-2: Login form shown for unauthorized access
        // For all the above request, show the springboot login form to user.. with the default features as below:
        httpSecurity.formLogin(Customizer.withDefaults());  // .wuthDefaults() is static method, so we need ot pass Defaults into static variables

        // Step-3: CSRF disable / filer /exception adjustment to be able to access:  "/h2-console"
        // Now, time to add exclusion from the above filter...
        httpSecurity.csrf(c -> c.disable());

        // Step-4: Enable the use of Frames in our App
        httpSecurity.headers(h->h.frameOptions(c->c.disable()));


        SecurityFilterChain s1 = httpSecurity.build();
        return s1;
    }




}


