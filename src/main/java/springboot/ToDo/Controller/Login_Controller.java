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
@Controller
@RequestMapping("api/todo/")
@SessionAttributes({"uid", "pass"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid" is the frontend variable, passing this will be able to save as session. it will work..

public class Login_Controller {
    Logger l1 = LoggerFactory.getLogger(Class.class); // or   getLogger(Login_Controller.class)


    @Autowired
    private Login_Services login_services;


    @RequestMapping(value = "/login2", method = RequestMethod.GET)
    public String get_login_page(){
        return "login_get2";
    }

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String actual_login_happens_here(@RequestParam("uid") String usernr,      //  @RequestParam is used to extract individual parameter values from the request URL or submitted form data
                                            @RequestParam("pass") String passw,      // @RequestParam annotation binds Servlet request(from HTML) parameters to method argument
                                            ModelMap modelMap){

        // Step-1 Validation
        modelMap.put("uid", usernr);
        modelMap.put("pass", passw);
        boolean validation_result = login_services.validateLogin(usernr, passw);
        l1.debug("PRE-validation-check:" + modelMap.toString() + validation_result +"  " +usernr + passw);

        // Step-2 Login failed
        if (!validation_result){
            modelMap.addAttribute("authmsg", "Auth Failed...");
            return "login_get2";
        }

        // Step-3 Login Pass
        return "welcome1";
    }
}
