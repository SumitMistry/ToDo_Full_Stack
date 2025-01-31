package springboot.ToDo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.User;
import springboot.ToDo.Services.User_Signup_Services;

@Controller
@RequestMapping(value = "/signup/")
@SessionAttributes({"uid_email", "pass", "totally" })
public class User_Signup_Controller {

    private final User_Signup_Services user_Signup_services;

    public User_Signup_Controller(User_Signup_Services user_Signup_services){
        super();
        this.user_Signup_services = user_Signup_services;
    }

    static{ }

    // GET - Insert
    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    public String get_signup_page( ){
        return "signup";
    }

    // POST - Insert
    @RequestMapping(value = "insert", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> add_user(@RequestParam(value = "usr") User user ){
        User retrieved_output =  user_Signup_services.insert_user(user);
        return new ResponseEntity<>(retrieved_output, HttpStatus.CREATED); // 201
    }

}
