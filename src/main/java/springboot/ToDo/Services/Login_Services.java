package springboot.ToDo.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Login_Services {
//
///////////////////////// OLD HARD-Coded login validation -Not in use - SAFE TO DELETE
//    public boolean validateLogin(String uid, String pass){
//        return uid.contains("@") && pass.length() == 4;
//    }
//

    /////////////////////// new Spring security feature
    public String get_username_from_login_from_spring_Security() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = auth.getName();
        System.out.println(uname);
        return uname;
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


}


