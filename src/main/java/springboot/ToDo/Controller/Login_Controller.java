package springboot.ToDo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import springboot.ToDo.Services.Login_Services;


/*
ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
(@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"uid_email", "pass"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid" is the frontend variable, passing this will be able to save as session. it will work..
public class Login_Controller {
    Logger l1 = LoggerFactory.getLogger(Class.class); // or   getLogger(Login_Controller.class)

    @Autowired
    private Login_Services login_services;

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
        l1.debug("PRE-validation-check:" + modelMap.toString() + validation_result +"  " +usernr + passw);
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
        l1.debug("PRE-validation-check:" + modelMap.toString() + validation_result +"  " +usernr + passw);
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
        // username print from modelmap
        modelMap.addAttribute("uid_email", "login1@spring.security");
        return "welcome1";
    }

}
