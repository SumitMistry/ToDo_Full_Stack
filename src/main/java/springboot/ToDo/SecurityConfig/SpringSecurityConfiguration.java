package springboot.ToDo.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springboot.ToDo.Model.UserAuth;
import springboot.ToDo.Repository.Repo_DAO_UserAuth_JPA;

import java.util.Optional;
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
    private Repo_DAO_UserAuth_JPA repo_dao_user_Auth_jpa;


    // Password encoder algorithm set
    @Bean
    public PasswordEncoder passwordEncoder_method(){  return new BCryptPasswordEncoder();
    }




    // Create UserAuth details Manager - This is exclusively for in memory only.
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

                Optional<UserAuth> optionalUser = repo_dao_user_Auth_jpa.findByUsername(username);
                if (optionalUser.isEmpty()) {
                    throw new RuntimeException("UserAuth not found");
                }
                UserAuth userAuth = optionalUser.get();
                return User.withUsername(userAuth.getUsername())
                        .password(userAuth.getPassword_encoded()) // Ensure password is encoded
//                        .roles(userAuth.getUser_role()) // Assign role dynamically
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
            UserAuth userAuth = fetchUserFromDatabase(username);
            return buildUserDetails(userAuth);
        }
    }

    private UserAuth fetchUserFromDatabase(String username) {
        Optional<UserAuth> optionalUser = repo_dao_user_Auth_jpa.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("UserAuth not found");
        }
        return optionalUser.get();
    }

    private UserDetails buildUserDetails(UserAuth userAuth) {
        return User.withUsername(userAuth.getUsername())
                .password(userAuth.getPassword_encoded()) // Ensure password is encoded
                //.roles(userAuth.getUser_role()) // Assign role dynamically
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/signup", "/welcome1", "welcome1/", "/error", "error/" , "/error" , "/login1", "/login2", "signup/", "login1/", "login1/**", "login2/", "/logout", "logout", "logout/", "/logout/**", "logout/**")) // to specify which request paths should be excluded from CSRF protection. Requests to these paths will not require a CSRF token.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("login2", "login2/", "/error", "error/"  ,"login1", "login1/", "/login2", "/signup","/signup/**", "/signup/", "login", "/login", "login/", "logout", "/logout", "logout/").permitAll() // Allow signup page access
                        .requestMatchers("/WEB-INF/jsp/**", "META-INF/resources/WEB-INF/jsp/common/", "META-INF/resources/WEB-INF/jsp/common", "/META-INF/resources/WEB-INF/jsp/common").permitAll() // Allow access to JSP files
                        .requestMatchers("/webjars/**", "/css/**", "/js/**", "/images/**", "/common/**", "/common", "common/").permitAll() // Allow static resources // The issue here is that Spring Security is blocking access to static resources (JS, CSS, Bootstrap, jQuery) before login. This is why your signup.jsp page appears broken before authentication but works fine after login.
                        .requestMatchers("/WEB-INF/jsp/common/**").permitAll() // Allow access to JSP files
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow static resources
                        .anyRequest().authenticated() // Secure all other pages
                )
                .formLogin(form -> form
                        .loginPage("/login2")  // Ensure this matches your form action

                                // This is where login POST requests are sent
                                // please supply this to POSTMAN for login : POST http://localhost:8080/login_perform2 with BODY= x-www-form-urlencoded
                        .loginProcessingUrl("/login_perform2")  // Specifies the URL where login requests should be submitted

                        // .loginProcessingUrl("/login2")  // Specifies the URL where login requests should be submitted'

                        .defaultSuccessUrl("/", true)  // Redirects after successful login
                        .usernameParameter("uid_email")  // Match form field name
                        .passwordParameter("pass")       // Match form field name
                        .failureUrl("/login2?error=true")  // Redirect on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Enable logout via GET
                        .logoutSuccessUrl("/login2?logout=true") // Redirect after logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // Enable GET for logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

                http.csrf(csrf -> csrf.disable()); // This disables CSRF entirely, making it easy to test with Postman but not secure for production. //Disables CSRF protection for the entire application.

        return http.build();
    }

//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//
//        // Allow GET requests for below pages...
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/signup", "/welcome1", "/login1", "/login2", "signup/", "welcome1/", "login1/", "login1/**", "login2/", "/logout", "logout", "logout/", "/logout/**", "logout/**")); // Disable CSRF only for these endpoints
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("login2", "login2/","login1", "login1/", "/login2", "/signup", "/signup/", "login", "/login", "login/", "logout", "/logout", "logout/").permitAll() // Allow signup page access
//                        .requestMatchers("/WEB-INF/jsp/**").permitAll() // Allow access to JSP files
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow static resources
//                        .anyRequest().authenticated() // Secure all other pages
//                )
//
//                .formLogin(form -> form
//                        .loginPage("/login1")  // Ensure this matches your form action
//                        .loginProcessingUrl("/login_perform")  // Specifies the URL where login requests should be submitted
//                        .defaultSuccessUrl("/", true)  // Redirects after successful login
//                        .usernameParameter("uid_email")  // Match form field name
//                        .passwordParameter("pass")       // Match form field name
//                        .failureUrl("/login1?error=true")  // Redirect on failure
//                        .permitAll()
//                );
//
//
//
////  1) Allow Logout via GET 2) Allow Logout via POST
////        Spring Security expects logout requests to be sent as a POST request, not GET.
//
//        http.logout(logout -> logout
//                         .logoutUrl("/logout")
//                         .logoutSuccessUrl("/login1?logout=true")
//                         .invalidateHttpSession(true)
//                         .deleteCookies("JSESSIONID")
//                         .permitAll());
//
//
//
//        return http.build();
//    }




//     HOW any specific url to be excluded / put in exception from login-security module, like health check of bluey..etc...
//     Every chain of request is being passed through this filter automatically by default in spring security...
//                Without this method  Forbidden Error
//                =  Whitelabel Error Page :(type=Forbidden, status=403)
//     The provided filterchain_1 method secures all application endpoints by requiring authentication. It uses Spring Security’s default login page for user authentication and allows access to the H2 database console by disabling CSRF protection and frame restrictions.
//     This configuration is ideal for development purposes but requires additional considerations for production environments.
//    @Bean
//    public SecurityFilterChain filterchain_default_login(HttpSecurity httpSecurity) throws Exception {
//
//        // Step-1 : PROTECT all URLS
//        // pass all request through below so all request get authenticated
//        httpSecurity.authorizeHttpRequests(
//                // pass through any request below function so it get authenticated
//                auth1 -> auth1.requestMatchers("login", "/login2", "/signup").permitAll()  // Allow access to login page
//                                        .anyRequest().authenticated()); // it will make sure, all request Requires authentication for all types IN/OUT
//
//
//        // Step-2: Login form shown for unauthorized access
//        // For all the above request, show the springboot login form to user.. with the default features as below:
//        httpSecurity.formLogin(Customizer.withDefaults());  // .withDefaults() is static method, so we need ot pass Defaults into static variables
////        httpSecurity.formLogin(form -> form
////                .loginPage("/custom-login")  // Specifies the custom login page URL
////                .loginProcessingUrl("/process-login")  // Specifies the URL where login requests should be submitted
////                .defaultSuccessUrl("/home", true)  // Redirects after successful login
////                .failureUrl("/login?error=true")  // Redirects if login fails
////                .usernameParameter("email")  // Custom username field name
////                .passwordParameter("pass")  // Custom password field name
////                .permitAll()  // Allows everyone to access the login page
////        );
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

