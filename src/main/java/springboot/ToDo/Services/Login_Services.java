package springboot.ToDo.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.ToDo.Model.User;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;
import springboot.ToDo.SecurityConfig.SpringSecurityConfiguration;

import java.util.Optional;

@Service
public class Login_Services {

    @Autowired
    private Repo_DAO_User_JPA repo_dao_user_jpa;


    @Autowired
    private SpringSecurityConfiguration springSecurityConfiguration;

//    @Autowired
//    private SpringSecurityConfig3 springSecurityConfig3;

    /////////////////////// new Spring security feature
    public String get_username_from_login_from_spring_Security() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // this username is being taken from main login-->spring Security, so my login2 page user data is different.
        String uname = auth.getName();
        System.out.println(uname);
        return uname;
    }
    public String get_userDETAILS_from_login_from_spring_Security() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // this username is being taken from main login-->spring Security, so my login2 page user data is different.
        String s1= repo_dao_user_jpa.findByUsername(auth.getName()).get().toString();
        return s1;
    }

    Logger l1 = LoggerFactory.getLogger(Class.class); // or   getLogger(Login_Controller.class)

    public String get_logged_user_Full_details(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String details1 = auth.getName();
        String details2 = auth.getDetails().toString();
        String details4 = auth.toString();
        String details5 = auth.getAuthorities().toString();
        String f = ("** CURRENT USER AUTH = "+ details1 +" "+  details2 +" " +" "+ details4 +" " + details5 );
        return f;
    }


    // VALIDATION: Login check / VALIDATION
    // Verify / match user entered password is Match or NOT ?
    public boolean validate_login_raw_pass_match_to_db_encoded_pass(String input_user, String input_pass){


        Optional<User> userOptional = repo_dao_user_jpa.findByUsername(input_user);

        // retrieve encoded password
        String encoded_pass = userOptional.map(User::getPassword_encoded).orElse(null);

        // use match function ---> to confirm input raw pass = = encoded pass or not..
        //boolean check = springSecurityConfiguration.passwordEncoder_method().matches( input_pass, encoded_pass );
        boolean check = true;  //springSecurityConfig3.passwordEncoder_method().matches( input_pass, encoded_pass );

        return check;
    }



}








///////////////////////// OLD HARD-Coded login validation -Not in use - SAFE TO DELETE
//    public boolean validateLogin(String uid, String pass){
//        return uid.contains("@") && pass.length() == 4;
//    }
