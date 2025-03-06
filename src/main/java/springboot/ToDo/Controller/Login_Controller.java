package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
// (@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>

@Controller
//@RequestMapping("/")
@SessionAttributes({"uid_email", "pass", "totally" })  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid" is the frontend variable, passing this will be able to save as session. it will work..
public class Login_Controller {

    private static final Logger l1 = LoggerFactory.getLogger(Class.class); // or   getLogger(Login_Controller.class)

    @Autowired
    private Login_Services login_services;


    ///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
    private final ToDo_Services toDo_Services;

    private final Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa;

    public Login_Controller(ToDo_Services toDo_Services, Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa){
        super();
        this.toDo_Services = toDo_Services;
        this.repo_dao_springData_todo_jpa = repo_dao_springData_todo_jpa;

    }


    static {
        // this to set initial static block, will initialize once only...
    }


    @RequestMapping(value = {"/api/todo/dateRangePicker"}, method = RequestMethod.GET)
    public String dateRangeFinder(@RequestParam(name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                  @RequestParam(name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                  ModelMap modelMap){

        List<Todo> list_todos = repo_dao_springData_todo_jpa.findCreationDateRange(fromDate,toDate);

        if (! list_todos.isEmpty()) {
            modelMap.addAttribute("listMapVar", list_todos);
            modelMap.addAttribute("totally", list_todos.size());
        } else {
            modelMap.addAttribute("listMapVar", new ArrayList<>());  // Empty list if no results
            modelMap.addAttribute("totally", 0);
        }
        return "index";
    }

    @RequestMapping(value = {"/api/todo/searchAPI"}, method = {RequestMethod.GET,  RequestMethod.POST})
    public String findByKeyword(@RequestParam(name = "searchKey") String keyword,
                                ModelMap modelMap){

        List<Todo> todoList = toDo_Services.findByKeyword(keyword);

        System.out.println(todoList.size());

        if (! todoList.isEmpty()) {
            modelMap.addAttribute("listMapVar", todoList);
            modelMap.addAttribute("totally", todoList.size());
        } else {
            modelMap.addAttribute("listMapVar", new ArrayList<>());  // Empty list if no results
            modelMap.addAttribute("totally",0);
        }
        return "index";
    }

    //////////////////// INSERT - SpringDataJPA SQL == insert3 (GET/POST)
    @RequestMapping(value = { "/api/todo/insert3" }, method = RequestMethod.GET)
    public String get_SprData_JPA_insert(ModelMap modelMap) {
        // 🔹THIS GET Method - Returns a pre-filled Todo object in frontend page using ModelMapping...

        // this is used just to get count=size of list
        int list_size = toDo_Services.findbyALL().size();
        //pre-filling add tags in forms automatically populated for users as easy to use.
        Todo t1 = new Todo(list_size + 1, (String) modelMap.get("uid_email"), "Hardcoded-world", LocalDate.now(), LocalDate.now().plusYears(1), false, null);

        modelMap.addAttribute("todo_obj_spring_data_jpa2", t1);

        return "insert3_SprDataJPA"; // this returns (jsp file)=view without @RESPONSEBODY
    }

    @RequestMapping(value = { "/api/todo/insert3" }, method = RequestMethod.POST)
    public String post_SprData_JPA_insert(ModelMap modelMap,
                                          @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,
                                          BindingResult bindingResult, Errors err) {

        //use binding result to find error while validating / entering data
        if (err.hasErrors() || bindingResult.hasErrors()) {
//            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            return "redirect:insert3_SprDataJPA";
            return "insert3_SprDataJPA";
        }

        // post data to backend SQL
        repo_dao_springData_todo_jpa.save(todo_obj_spring_data_jpa2);

        // get data view
        return "redirect:/api/todo/list"; // validation will not be displayed because we have 2 different mpodels, both displaying on same page=List
    }



// ------------------------------------------------------------------------------
// ---------------------------------- JSON --------------------------------------
// ------------------------------------------------------------------------------
//   If you decide to make your application a pure ""REST API"" (returning JSON instead of rendering a JSP page), ...
//   .....then you'd need to change it to return JSON, like this:
    /////----------------- INSERT - SpringDataJPA SQL == insert4 (GET/POST) --------JSON

    @RequestMapping(value = { "/api/todo/insert4" }, method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> insert4(
            @RequestBody @Valid Todo todo_obj_spring_data_jpa2,
            BindingResult bindingResult) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }

        // Save to database
        repo_dao_springData_todo_jpa.save(todo_obj_spring_data_jpa2);

        return ResponseEntity.ok("Todo inserted successfully");
    }





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

        return "login_old2";
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
