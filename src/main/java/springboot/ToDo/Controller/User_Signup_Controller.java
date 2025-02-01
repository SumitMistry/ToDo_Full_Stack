package springboot.ToDo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.User;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;
import springboot.ToDo.Services.User_Signup_Services;

@Controller
@RequestMapping(value = "/")
@SessionAttributes({"uid_email", "pass", "totally" })
public class User_Signup_Controller {

    @Autowired
    private Repo_DAO_User_JPA repo_dao_user_jpa;

    private final User_Signup_Services user_Signup_services;

    public User_Signup_Controller(User_Signup_Services user_Signup_services){
        super();
        this.user_Signup_services = user_Signup_services;
    }

    static{ }

    // INSERT + RAW + (GET)
    @RequestMapping(value = {"signup", "signup/"}, method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    public String get_signup_page(ModelMap modelmap){
        // (This is to pre-populate / pre-parse data)
        modelmap.addAttribute("prefill_signup_email", "test1@abc.xyz" );
        modelmap.addAttribute("prefill_signup_pass", "1" );

        return "signup";
    }

    // INSERT + RAW + (POST)
    @RequestMapping(value = {"signup", "signup/"}, method = {RequestMethod.POST, RequestMethod.PUT})
    //@ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String add_user(@RequestParam(value = "uid_email") String incoming_username,
                           @RequestParam(value = "pass") String incoming_password,
                           ModelMap modelMap){

        // this returns back html page with "success written on body"
        if ( incoming_password.isEmpty() ||  incoming_username.isEmpty()){
            modelMap.addAttribute("authmsg_signup", "Failed...");
        }else {
            // adding RAW values, NON-ENCODED
            User retrieved_output =  user_Signup_services.insert_user_Bcrypted_encoded(incoming_username, incoming_password);
            modelMap.addAttribute("authmsg_signup", "Success: " + retrieved_output.toString());
        }
        return "signup";   // throw new ResponseStatusException(HttpStatusCode.valueOf(779), " USername / pass is empty");
        // this return JSON
        //return new ResponseEntity<>(retrieved_output, HttpStatus.CREATED); // 201
    }


    // Verify / match user entered password is Match or NOT ?
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    public String validate_User_Login(@RequestParam(value = "uid_email") String input_username,
                                      @RequestParam(value = "pass") String input_pass,
                                      ModelMap modelMap){

        boolean ans = user_Signup_services.validate_User_Login(input_username,input_pass);

        if(ans == true){
            modelMap.addAttribute("authmsg_login2","Success: Pass Matching = " + repo_dao_user_jpa.findByUsername(input_username));
        } else if (ans == false) {
            modelMap.addAttribute("authmsg_login2","Failed: Pass Mismatch = " + repo_dao_user_jpa.findByUsername(input_username));
        }
        return "signup";
    }


}
