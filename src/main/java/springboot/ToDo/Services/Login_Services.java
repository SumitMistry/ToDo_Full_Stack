package springboot.ToDo.Services;

import org.springframework.stereotype.Service;

@Service
public class Login_Services {

    public    boolean validateLogin(String uid, String pass){
        if (uid.contains("@") && pass.length()==4){ return true;}
            else return false;
    }
}
