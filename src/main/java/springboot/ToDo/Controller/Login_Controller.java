package springboot.ToDo.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import springboot.ToDo.Services.Login_Services;



// ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
// (@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>

@Controller
//@RequestMapping("/")
@SessionAttributes({"uid_email", "pass", "totally" })  // when you want to store a value in whole session, use this.
@Tag(name = "Controller# 5 = Login Controller Management", description = "SM: Operations related to Login Controller") // Now, APIs will be grouped under "Todo Management" in Swagger UI. ✅ // used to group related API endpoints in the Swagger UI. It helps organize APIs by functionality, making them easier to understand and navigate.
// you have to pass this values from frontend variable standpoint, so it is <uid> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid" is the frontend variable, passing this will be able to save as session. it will work..
public class Login_Controller {

    final Logger l1 = LoggerFactory.getLogger(Class.class); // or   getLogger(Login_Controller.class)

    @Autowired
    private Login_Services login_services;

    /**
     * 9️⃣ Login Page Handling (JSP Page)  //////////////////////   OLD / NOT USED  //////////////////////////////
     */
    @RequestMapping(value = { "/login2", "login2" }, method = RequestMethod.GET)
    public String get_login_page2(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout,
                                  ModelMap modelMap) {

        // Check if user is already authenticated and prevent looping
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/welcome1"; // 🚀 Redirect authenticated users to the welcome page
        }

        if (error != null) {
            modelMap.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (logout != null) {
            modelMap.addAttribute("logoutMessage", "You have been logged out.");
        }

        modelMap.addAttribute("prefill_login_email_old2_a", "sumit@america.com");
        modelMap.addAttribute("prefill_login_email_old2_b", "1");

        return "login_old2"; // JSP Page
    }




/////////////////////   LOGIN - GET - (Spring Security)    ///////////////
////  This method work to only and only Fetches 2 items [[[[..... 1) entered_username 2) entered pass]]] from login page and variable get created....
////  username retrieved from user's login entry point // HTML tag label are "pass" and "uid_email", retrieving what use entered in frontend.
    @RequestMapping(value = {"/", "login" }, method = RequestMethod.GET)
    public String get_welcome_page(ModelMap modelMap){

        String retrived_user = login_services.get_username_from_login_from_spring_Security();
        String retrived_user_details = login_services.get_logged_user_Full_details();
                    System.out.println( "Loggged in by:---> "+retrived_user + " "+ retrived_user_details);
                    l1.info("Loggged in by:--->" +retrived_user + "\n" +retrived_user_details );

        // username print from modelmap //removing hard-coded username, passing from well organized method...
        //modelMap.addAttribute("uid_email", "login@spring.Security");
        modelMap.addAttribute("uid_email", retrived_user);
        // I can build password method here too...

        modelMap.addAttribute("login_auth_success_1", "= "+ login_services.get_userDETAILS_from_login_from_spring_Security());
        modelMap.addAttribute("login_auth_success_2", "= "+ login_services.get_logged_user_Full_details());


        // DONT turn on below 2 line code. turning on will print secret key in Log file at/// DEBUG 2568 --- [ToDo] [nio-8080-exec-9] o.s.web.servlet.view.JstlView  ///
        // Also then Git push will throw an error and hit need to reset, git reset, refer to my file in this repo at root "git_cmd"
        // System.out.println("\n\n\n\n\n" + System.getenv( "open_ai_api_key_sm"));
        //modelMap.addAttribute("modelmap_env_var_open_api_key", System.getenv( "open_ai_api_key_sm"));


        return "welcome1";
    }


}










//
//    @RequestMapping(value = { "/login1", "login1" }, method = RequestMethod.GET)
//    public String get_login_page1(@RequestParam(value = "error", required = false) String error,
//                                  @RequestParam(value = "logout", required = false) String logout,
//                                  ModelMap modelMap) {
//
//        // Check if user is already authenticated and prevent looping
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
//            return "redirect:/welcome1"; // 🚀 Redirect authenticated users to the welcome page
//        }
//
//            if (error != null) {
//                modelMap.addAttribute("errorMessage", "Invalid username or password.");
//            }
//            if (logout != null) {
//                modelMap.addAttribute("logoutMessage", "You have been logged out.");
//            }
//
//        modelMap.addAttribute("prefill_login_email_old1_a", "sumit@america.com");
//        modelMap.addAttribute("prefill_login_email_old1_b", "1");
//
//        return "login_old1";
//    }









//    // CHECK MATCH sign-in //  RAW Pass --vs-- ENCODED Pass
//    @RequestMapping(value = {"/", "login" }, method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public String isPassMAtch(@RequestParam(value = "uid_email") String  incoming_username,
//                              @RequestParam(value = "pass") String  incoming_pass,
//                              ModelMap modelMap){
//        //if username's raw pass vs same user's encrypted pass matches, then allow to go further / show "list"
//        if (user_Signup_services.validate_User_Login(incoming_username, incoming_pass) == true){
//            return  "welcome1"; // "list" ; --> whatever you want to display we can do here...
//        }
//        else {
//            modelMap.addAttribute("authmsg_signup", "UsrNm / Password wrong, something not matching....");
//            return "signup";
//        }
//    }





//    ///////////////////////   LOGIN (REGULAR GOOD UI)    ///////////////
//    @RequestMapping(value = { "/login1"}, method = RequestMethod.GET)
//    public String get_login_page1(ModelMap modelMap){
//        modelMap.put("prefill_login_old1", "login1@new.ui");
//        return "login_old1";
//    }
//    @RequestMapping(value = {"login1"}, method = RequestMethod.POST)
//    public String actual_login_happens_here1( @RequestParam("uid_email") String usernr,      //  @RequestParam is used to extract individual parameter values from the request URL or submitted form data
//                                             @RequestParam("pass") String passw,      // @RequestParam annotation binds Servlet request(from HTML) parameters to method argument
//                                            ModelMap modelMap){
//        // Step-1 Validation
//        modelMap.put("uid_email", usernr);
//        modelMap.put("pass", passw);
//        boolean validation_result = login_services.validateLogin(usernr, passw);
//        l1.debug("PRE-validation-check:" + modelMap + validation_result +"  " +usernr + passw);
//        // Step-2 Login failed
//        if (!validation_result){
//            modelMap.addAttribute("authmsg", "Auth Failed...    <p>\n" +
//                    "            Hint1: uid must have @\n" +
//                    "            <p>\n" +
//                    "            Hint2: pass must be 4 length");
//            return "login_old1";
//        }
//        // Step-3 Login Pass
//        return "welcome1";
//    }
//





/////////////////////////  LOGIN + PASS VALIDATION --- (My made) OLD   ///////////////
//    @RequestMapping(value = { "/login2"}, method = RequestMethod.GET)
//    public String get_login_page2(ModelMap modelMap){
//
//        modelMap.addAttribute("prefill_login_email_old2_a", "sumit@america.com");
//        modelMap.addAttribute("prefill_login_email_old2_b", "1");
//
//        //modelMap.put("prefill_login_pass_old2", "1"); // we can pre-fill/ pre-populate password in the form here at login page.
//        return "login_old2";
//    }
