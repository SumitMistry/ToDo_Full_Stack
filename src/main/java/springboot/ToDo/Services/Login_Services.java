package springboot.ToDo.Services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Login_Services {

/////////////////////// OLD HARD-Coded login validation -Not in use - SAFE TO DELETE
    public boolean validateLogin(String uid, String pass){
        if (uid.contains("@") && pass.length()==4){ return true;}
            else return false;
    }


    /////////////////////// new Spring security feature
    public String get_username_from_login_from_spring_Security() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = auth.getName();
        System.out.println(uname);
        return uname;
    }


}


