package springboot.ToDo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.UserAuth;
import springboot.ToDo.Model.UserProfile0;
import springboot.ToDo.Model.UserProfile1;
import springboot.ToDo.Services.User_Signup_Services;

@Controller
//@RequestMapping(value = "/")
@SessionAttributes({"uid_email", "pass", "totally" })
public class User_Signup_Controller {


    private final User_Signup_Services user_Signup_services;

    public User_Signup_Controller(User_Signup_Services user_Signup_services){
        super();
        this.user_Signup_services = user_Signup_services;
    }
    static{ }



    // SIGNUP: INSERT + RAW + (GET)
    @RequestMapping(value = {"signup", "signup/"}, method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    public String get_signup_page(ModelMap modelmap){
        // (This is to pre-populate / pre-parse data)
        modelmap.addAttribute("prefill_signup_email", "sumit@americas.com" );
        modelmap.addAttribute("prefill_signup_pass", "1" );

        return "signup";
    }




    //////////////////////  SIGNUP: INSERT + (RAW pass) + (ENCODED pass)  POST  ////////////////
    @RequestMapping(value = {"signup", "signup/"}, method = {RequestMethod.POST, RequestMethod.PUT})
    //@ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String signup_insert_raw_pass_and_encoded_pass(@RequestParam(value = "uid_email") String incoming_username,
                                                          @RequestParam(value = "pass") String incoming_password,
                                                          @RequestParam(value = "role", required = false /** doing this "false" to handle null role error manually **/) String[] roles,
                                                          ModelMap modelMap){


        // this returns back html page with "fail"  //  "success written on body"
                if (roles == null || roles.length == 0) {
                    modelMap.addAttribute("authmsg_signup", "Failed... : role is blank");
                }
                else if (incoming_username == null || incoming_username.trim().isEmpty()) { // trim used to remove white space char test case
                    modelMap.addAttribute("authmsg_signup", "Failed... : incoming_username.isEmpty() ");
                }
                else if (incoming_password == null || incoming_password.trim().isEmpty()) {
                    modelMap.addAttribute("authmsg_signup", "Failed... : incoming_password.isEmpty() ");
                }

        else {
            // Adding RAW pass into USER object
            UserAuth retrieved_output_after_raw_pass_added =  user_Signup_services.signup_insert_raw_pass(incoming_username, incoming_password);
            modelMap.addAttribute("authmsg_signup", "Success[RawPass_added]: " + retrieved_output_after_raw_pass_added.toString());

            // Adding ENCODED pass  + ROLE  into USER object
            UserAuth retrieved_output_andNowAdding_EncodedPass_Role = user_Signup_services.signup_insert_encoded_pass_and_role(  incoming_username,  incoming_password,  roles);
            modelMap.addAttribute("authmsg_signup1", "Success[ENCODED_pass + ROLE added]: " + retrieved_output_andNowAdding_EncodedPass_Role.toString());

            // Adding Sign-Up data into table <UserProfile0>
            UserProfile0 profile0_object = user_Signup_services.add_ONLY_username_in_UserProfile0(incoming_username);
            modelMap.addAttribute("authmsg_signup2", "Success[added into <UserProfile0> table]: " + profile0_object.toString());

            // Adding Sign-Up data into table <UserProfile1>
            UserProfile1 profile1_obj = user_Signup_services.add_ONLY_username_in_UserProfile1(incoming_username);
            modelMap.addAttribute("authmsg_signup3", "Success[added into <UserProfile1> table]: " + profile1_obj.toString());
        }


        return "signup";   // throw new ResponseStatusException(HttpStatusCode.valueOf(779), " USername / pass is empty");
        // this return JSON
        //return new ResponseEntity<>(retrieved_output, HttpStatus.CREATED); // 201
    }


}














//    // Verify / match user entered password is Match or NOT ?
//    @RequestMapping(value = {"validate", "validate/"}, method = RequestMethod.GET)
//    public String validate_User_Login(@RequestParam(value = "uid_email") String input_username,
//                                      @RequestParam(value = "pass") String input_pass,
//                                      ModelMap modelMap){
//
//        boolean ans = user_Signup_services.validate_User_Login(input_username,input_pass);
//
//        if(ans == true){
//            modelMap.addAttribute("authmsg_login2","Success: Pass Matching = " + repo_dao_user_jpa.findByUsername(input_username));
//        } else if (ans == false) {
//            modelMap.addAttribute("authmsg_login2","Failed: Pass Mismatch = " + repo_dao_user_jpa.findByUsername(input_username));
//        }
//        return "signup";
//    }

