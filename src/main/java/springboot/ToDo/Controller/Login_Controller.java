package springboot.ToDo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.User_Signup_Services;


/*
ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
(@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"uid_email", "pass", "totally" })  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid" is the frontend variable, passing this will be able to save as session. it will work..
public class Login_Controller {


    Logger l1 = LoggerFactory.getLogger(Class.class); // or   getLogger(Login_Controller.class)

    @Autowired
    private Login_Services login_services;

    @Autowired
    private User_Signup_Services user_Signup_services;

    ///////////////////////   LOGIN (REGULAR GOOD UI)    ///////////////
    @RequestMapping(value = { "/login1"}, method = RequestMethod.GET)
    public String get_login_page1(ModelMap modelMap){
        modelMap.put("prefill_login_old_get1", "login1@new.ui");
        return "login_old_get1";
    }
    @RequestMapping(value = {"login1"}, method = RequestMethod.POST)
    public String actual_login_happens_here1( @RequestParam("uid_email") String usernr,      //  @RequestParam is used to extract individual parameter values from the request URL or submitted form data
                                             @RequestParam("pass") String passw,      // @RequestParam annotation binds Servlet request(from HTML) parameters to method argument
                                            ModelMap modelMap){
        // Step-1 Validation
        modelMap.put("uid_email", usernr);
        modelMap.put("pass", passw);
        boolean validation_result = login_services.validateLogin(usernr, passw);
        l1.debug("PRE-validation-check:" + modelMap + validation_result +"  " +usernr + passw);
        // Step-2 Login failed
        if (!validation_result){
            modelMap.addAttribute("authmsg", "Auth Failed...    <p>\n" +
                    "            Hint1: uid must have @\n" +
                    "            <p>\n" +
                    "            Hint2: pass must be 4 length");
            return "login_old_get1";
        }
        // Step-3 Login Pass
        return "welcome1";
    }


///////////////////////  LOGIN (My made) OLD   ///////////////
    @RequestMapping(value = { "/login2" }, method = RequestMethod.GET)
    public String get_login_page2(ModelMap modelMap){

        modelMap.put("prefill_login_old_get1", "login2@smMade.ui");
        return "login_old_get2";
    }
    @RequestMapping(value = {"login2"}, method = RequestMethod.POST)
    public String actual_login_happens_here2( @RequestParam("uid_email") String usernr,      //  @RequestParam is used to extract individual parameter values from the request URL or submitted form data
                                             @RequestParam("pass") String passw,      // @RequestParam annotation binds Servlet request(from HTML) parameters to method argument
                                             ModelMap modelMap){
        // Step-1 Validation
        modelMap.put("uid_email", usernr);
        modelMap.put("pass", passw);
        boolean validation_result = login_services.validateLogin(usernr, passw);
        l1.debug("PRE-validation-check:" + modelMap + validation_result +"  " +usernr + passw);
        // Step-2 Login failed
        if (!validation_result){
            modelMap.addAttribute("authmsg", "Auth Failed...    <p>\n" +
                    "            Hint1: uid must have @\n" +
                    "            <p>\n" +
                    "            Hint2: pass must be 4 length");
            return "login_old_get2";
        }
        // Step-3 Login Pass
        return "welcome1";
    }


///////////////////////   LOGIN (Spring Security)    ///////////////
    @RequestMapping(value = {"/", "login" }, method = RequestMethod.GET)
    public String get_login_page(ModelMap modelMap){

        // username retrieved from user's login entry point // HTML tag label are "pass" and "uid_email", retrieving what use entered in frontend.
        String retrived_user = login_services.get_username_from_login_from_spring_Security();
        String retrived_user_details = login_services.get_user_Full_details();
        System.out.println( "Loggged in by:---> "+retrived_user + " "+ retrived_user_details);
        l1.info("Loggged in by:--->" +retrived_user + "\n" +retrived_user_details );

        // username print from modelmap //removing hard-coded username, passing from well organized method...
        //modelMap.addAttribute("uid_email", "login@spring.Security");
        modelMap.addAttribute("uid_email", retrived_user);
        // I can build password method here too...

        return "welcome1";
    }


    // CHECK MATCH sign-in //  RAW Pass --vs-- ENCODED Pass
    @RequestMapping(value = {"/", "login" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String isPassMAtch(@RequestParam(value = "uid_email") String  incoming_username,
                              @RequestParam(value = "pass") String  incoming_pass,
                              ModelMap modelMap){
        //if username's raw pass vs same user's encrypted pass matches, then allow to go further / show "list"
        if (user_Signup_services.validate_User_Login(incoming_username, incoming_pass) == true){
            return  "welcome1"; // "list" ; --> whatever you want to display we can do here...
        }
        else {
            modelMap.addAttribute("authmsg_signup", "UsrNm / PAssword wrong, something not matching....");
            return "redirect:signup";
        }
    }

}
