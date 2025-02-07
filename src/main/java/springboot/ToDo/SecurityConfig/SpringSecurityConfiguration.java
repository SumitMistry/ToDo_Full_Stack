package springboot.ToDo.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Configuration
@ConfigurationProperties
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Value("${login.adminUsername2}")  // this variable is set in Application.properties / Environment variable / used @@ConfigurationProperties @Value // Application.Properties
    private String adminUsername2;
    @Value("${login.adminPass2}") // this variable is set in Application.properties / Environment variable / used @@ConfigurationProperties @Value // Application.Properties
    private String adminPass2;
    @Value("${login.adminRole2}") // this variable is set in Application.properties / Environment variable / used @@ConfigurationProperties @Value // Application.Properties
    private String adminRole2;
    @Value("${login.adminRole3}") // this variable is set in Application.properties / Environment variable / used @@ConfigurationProperties @Value // Application.Properties
    private String adminRole3;

    // Generally in companies, we connect this Clas with below 2 methods to get user login data:
    // 1. LDAP / database use to fetch user details...
    // 2. In memory userDetailsManager ----------> this is used here to start leaning Spr.Secu-session-1

    //static { }

    @Autowired
    private Repo_DAO_User_JPA repo_dao_user_jpa;


    // Password encoder algorithm set
    @Bean
    public PasswordEncoder passwordEncoder_method(){  return new BCryptPasswordEncoder();
    }




    // Create User details Manager - This is exclusively for in memory only.
    // # if you configure an InMemoryUserDetailsManager() method in your application explicitly, the properties set in application.properties for spring.security.user.name and spring.security.user.password will not take effect
    // Spring Boot's default behavior for these properties (spring.security.user.*) only applies if you do not provide your own custom UserDetailsService or SecurityFilterChain configuration.
    // By defining an InMemoryUserDetailsManager, you're overriding the default auto-configuration for the in-memory user, and Spring ignores the spring.security.user.* properties.
//    @Bean
//    public InMemoryUserDetailsManager configure_each_user_detail() {
//
//        //fetching username password data from Application.properties
//        // Initialize the manager without duplicates
//
//        // USER=1 HARD CODED user here
//        UserDetails user1 = createNewUSer("sumit@bofa.com", "1",new String[]{"ADMIN","DEVELOPER"} );
//        // USER=2 ENV VAR from Application.properties --> @VALUE
//        UserDetails user2 = createNewUSer(adminUsername2, adminPass2, new String[]{adminRole2,adminRole3});
//
//        // USER=3 by default from Application.properties --> spring.security.user.name= .....
//
//        InMemoryUserDetailsManager im = new InMemoryUserDetailsManager(user1, user2);
//        // Pass users as a Set to eliminate duplicates
//        return im;
//    }



//      This takes USER data from db directly to authenticate.
//      To turn ON this, turn Off InMemoryDb method above.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

            authProvider.setUserDetailsService(username -> {

                Optional<springboot.ToDo.Model.User> optionalUser = repo_dao_user_jpa.findByUsername(username);
                if (optionalUser.isEmpty()) {
                    throw new RuntimeException("User not found");
                }
                springboot.ToDo.Model.User user = optionalUser.get();
                return User.withUsername(user.getUsername())
                        .password(user.getPassword_encoded()) // Ensure password is encoded
//                        .roles(user.getUser_role()) // Assign role dynamically
                        .build();
            });

        authProvider.setPasswordEncoder(passwordEncoder_method());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // Custom UserDetailsService Implementation
    private  class CustomUserDetailsService implements UserDetailsService {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            springboot.ToDo.Model.User user = fetchUserFromDatabase(username);
            return buildUserDetails(user);
        }
    }

    private springboot.ToDo.Model.User fetchUserFromDatabase(String username) {
        Optional<springboot.ToDo.Model.User> optionalUser = repo_dao_user_jpa.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optionalUser.get();
    }

    private UserDetails buildUserDetails(springboot.ToDo.Model.User user) {
        return User.withUsername(user.getUsername())
                .password(user.getPassword_encoded()) // Ensure password is encoded
                //.roles(user.getUser_role()) // Assign role dynamically
                .build();
    }


    // @Bean //---> if I add Bean here, the double time this method will execute and App will Fail to Start
    // we dont need bean becasue this dependency is being automatically called into method=configure_each_user_detail()
    // turning @Bean ON will create duplicate user in the framework and will fail to start
    public UserDetails createNewUSer(String u_name, String pass_rd, String[] roleee){
        // generate pass decode algorithm--> to be used in next step
        // Lambda function
        Function<String,String> pass_encoder_algo =  input  ->  passwordEncoder_method().encode(input) ;

        // creating user method core logic with ENCRYPTION ON
        UserDetails user_generating =
                User
                        .builder()
                        .passwordEncoder(pass_encoder_algo)
                        .username(u_name)
                        .password(pass_rd)
                        .roles(roleee)  // role= could be "ADMIN, "USER" //   .roles("ADMIN, "USER") --> can be replaced
                        .build();

        return user_generating;
    }


//     HOW any specific url to be excluded / put in exception from login-security module, like health check of bluey..etc...
//     Every chain of request is being passed through this filter automatically by default in spring security...
//                Without this method  Forbidden Error
//                =  Whitelabel Error Page :(type=Forbidden, status=403)
//     The provided filterchain_1 method secures all application endpoints by requiring authentication. It uses Spring Security’s default login page for user authentication and allows access to the H2 database console by disabling CSRF protection and frame restrictions.
//     This configuration is ideal for development purposes but requires additional considerations for production environments.
    @Bean
    public SecurityFilterChain filterchain_default_login(HttpSecurity httpSecurity) throws Exception {

        // Step-1 : PROTECT all URLS
        // pass all request through below so all request get authenticated
        httpSecurity.authorizeHttpRequests(
                // pass through any request below function so it get authenticated
                auth1 -> auth1.requestMatchers("login", "/login2", "/signup").permitAll()  // Allow access to login page
                                        .anyRequest().authenticated()); // it will make sure, all request Requires authentication for all types IN/OUT


        // Step-2: Login form shown for unauthorized access
        // For all the above request, show the springboot login form to user.. with the default features as below:
        httpSecurity.formLogin(Customizer.withDefaults());  // .withDefaults() is static method, so we need ot pass Defaults into static variables
//        httpSecurity.formLogin(form -> form
//                .loginPage("/custom-login")  // Specifies the custom login page URL
//                .loginProcessingUrl("/process-login")  // Specifies the URL where login requests should be submitted
//                .defaultSuccessUrl("/home", true)  // Redirects after successful login
//                .failureUrl("/login?error=true")  // Redirects if login fails
//                .usernameParameter("email")  // Custom username field name
//                .passwordParameter("pass")  // Custom password field name
//                .permitAll()  // Allows everyone to access the login page
//        );


        // Step-3: CSRF disable / filer /exception adjustment to be able to access:  "/h2-console"
        // Now, time to add exclusion from the above filter...
        httpSecurity.csrf(c -> c.disable()); // doing this for h2 dB

        // Step-4: Enable the use of Frames in our App
        httpSecurity.headers(h->h.frameOptions(c->c.disable()));
        SecurityFilterChain s1 = httpSecurity.build();

        return s1;
    }


//
//    @Bean
//    public SecurityFilterChain filterchain_custom_login(HttpSecurity httpSecurity) throws Exception {
//
//        // Step-1 : PROTECT all URLS
//        // pass all request through below so all request get authenticated
//        httpSecurity.authorizeHttpRequests(
//                // pass through any request below function so it get authenticated
//                auth1 -> auth1.requestMatchers("/login2", "/signup").permitAll().anyRequest().authenticated() ); // it will make sure, all request Requires authentication for all types IN/OUT
//
//
//        // Step-2: Login form shown for unauthorized access
//        // For all the above request, show the springboot login form to user.. with the default features as below:
////        httpSecurity.formLogin(Customizer.withDefaults());  // .wuthDefaults() is static method, so we need ot pass Defaults into static variables
//
//        httpSecurity.formLogin(form -> form
//                .loginPage("/login2")  // Ensure this matches the GET mapping
//                .loginProcessingUrl("/login2") // Change from `/perform_login` to `/login2`
//                .defaultSuccessUrl("/welcome1", true)
//                .failureUrl("/login2?error=true")
//                .permitAll()
//        );
//
//
//        // Step-3: CSRF disable / filer /exception adjustment to be able to access:  "/h2-console"
//        // Now, time to add exclusion from the above filter...
//        httpSecurity.csrf(c -> c.disable()); // doing this for h2 dB
//
//        // Step-4: Enable the use of Frames in our App
//        httpSecurity.headers(h->h.frameOptions(c->c.disable()));
//        SecurityFilterChain s1 = httpSecurity.build();
//
//        return s1;
//    }


    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");  // resolver.setPrefix("/META-INF/resources/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

}












/*
This working best default one--------------------------------------------

    public SecurityFilterChain filterchain_default_login(HttpSecurity httpSecurity) throws Exception {

        // Step-1 : PROTECT all URLS
        // pass all request through below so all request get authenticated
        httpSecurity.authorizeHttpRequests(
                // pass through any request below function so it get authenticated
                auth1 -> auth1.anyRequest().authenticated() ); // it will make sure, all request Requires authentication for all types IN/OUT


        // Step-2: Login form shown for unauthorized access
        // For all the above request, show the springboot login form to user.. with the default features as below:
        httpSecurity.formLogin(Customizer.withDefaults());  // .wuthDefaults() is static method, so we need ot pass Defaults into static variables

        // Step-3: CSRF disable / filer /exception adjustment to be able to access:  "/h2-console"
        // Now, time to add exclusion from the above filter...
        httpSecurity.csrf(c -> c.disable()); // doing this for h2 dB

        // Step-4: Enable the use of Frames in our App
        httpSecurity.headers(h->h.frameOptions(c->c.disable()));
        SecurityFilterChain s1 = httpSecurity.build();

        return s1;
    }
*/

