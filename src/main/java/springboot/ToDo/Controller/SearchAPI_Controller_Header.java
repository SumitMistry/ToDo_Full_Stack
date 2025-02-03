package springboot.ToDo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_JPA;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SessionAttributes({"uid_email", "pass", "totally"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will not work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..
public class SearchAPI_Controller_Header<T> {


    private static final Logger l1 = LoggerFactory.getLogger(Class.class);

    ///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
    private final ToDo_Services toDo_Services;

    private final Repo_DAO_SpringData_JPA repo_dao_springData_jpa;



    public SearchAPI_Controller_Header(ToDo_Services toDo_Services, Repo_DAO_SpringData_JPA repo_dao_springData_jpa, Login_Services loginServices) {
        super();
        this.toDo_Services = toDo_Services;
        this.repo_dao_springData_jpa =repo_dao_springData_jpa;
    }

    static {
        // this to set initial static block, will initialize once only...
    }


    @RequestMapping(value = {"dateRangePicker", "/dateRangePicker", "api/todo/dateRangePicker", "api/todo/dateRangePicker/ "}, method = RequestMethod.GET)
    public String dateRangeFinder(@RequestParam(name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                  @RequestParam(name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                  ModelMap modelMap){

        List<Todo> list_todos = repo_dao_springData_jpa.findCreationDateRange(fromDate,toDate);

        if (! list_todos.isEmpty()) {
            modelMap.addAttribute("listMapVar", list_todos);
            modelMap.addAttribute("totally", list_todos.size());
        } else {
            modelMap.addAttribute("listMapVar", new ArrayList<>());  // Empty list if no results
            modelMap.addAttribute("totally", 0);
        }
        return "index";
    }

    @RequestMapping(value = {"searchAPI", "/searchAPI", "api/todo/searchAPI", "api/todo/searchAPI/ "}, method = {RequestMethod.GET,  RequestMethod.POST})
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

}





