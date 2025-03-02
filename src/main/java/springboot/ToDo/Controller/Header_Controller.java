package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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


@Controller
//@RequestMapping(value = "/")
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SessionAttributes({"uid_email", "pass", "totally"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will not work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..
public class Header_Controller<T> {


    private static final Logger l1 = LoggerFactory.getLogger(Class.class);

    ///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
    private final ToDo_Services toDo_Services;

    private final Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa;



    public Header_Controller(ToDo_Services toDo_Services, Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa, Login_Services loginServices) {
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


}





