package springboot.ToDo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.User;
import springboot.ToDo.Services.User_Signup_Services;

@Controller
@RequestMapping(value = "/")
@SessionAttributes({"uid_email", "pass", "totally" })
public class User_Signup_Controller {

    private final User_Signup_Services user_Signup_services;

    public User_Signup_Controller(User_Signup_Services user_Signup_services){
        super();
        this.user_Signup_services = user_Signup_services;
    }

    static{ }

    // GET - Insert
    @RequestMapping(value = {"signup", "signup/"}, method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    public String get_signup_page(ModelMap modelmap){
        // (This is to pre-populate / pre-parse data)
        modelmap.addAttribute("prefill_signup_email", "kk@kk.kk" );
        modelmap.addAttribute("prefill_signup_pass", "1" );

        return "signup";
    }

    // POST - Insert
    @RequestMapping(value = {"signup", "signup/"}, method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> add_user(@RequestParam(value = "uid_email") String username,
                                         @RequestParam(value = "pass") String password){

        User user_data_front_frontend = new User(username,password);

        // adding RAW values, NON-ENCODED
        User retrieved_output =  user_Signup_services.insert_user_raw(user_data_front_frontend);
        return new ResponseEntity<>(retrieved_output, HttpStatus.CREATED); // 201
    }

}
