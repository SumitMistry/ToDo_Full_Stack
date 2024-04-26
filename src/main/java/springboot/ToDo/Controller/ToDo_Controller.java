package springboot.ToDo.Controller;

import jakarta.validation.constraints.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("api/todo/")
@SessionAttributes({"uid", "pass"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid" is the frontend variable, passing this will be able to save as session. it will work..

public class ToDo_Controller {

    Logger l1 = LoggerFactory.getLogger(Class.class);

///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
                        private ToDo_Services toDo_services;
                        public ToDo_Controller(ToDo_Services toDo_services){
                            super();
                            this.toDo_services = toDo_services;
                        }

    @RequestMapping("/list")
    public String listAll_todos(ModelMap modelMap){
        List<Todo> outputList =  toDo_services.listAllToDo();
        modelMap.addAttribute("listMapVar",outputList);      l1.debug("POST-validation-check:" + modelMap.toString() + outputList.toString());
        return "listall";
    }


    ///////////////////////////     INSERT 2       ///////////////////////////////////////////////////////////////////
    @RequestMapping(value="/insert2" , method=RequestMethod.GET)
    public String get_insert_todo2(){
         return "insert2";
    }

    @RequestMapping(value="/insert2" , method=RequestMethod.POST)
    public String post_insert_todo(@RequestParam String id,
                                  @NonNull @RequestParam String username,
                                  @NonNull @RequestParam String description,
                                   @RequestParam LocalDate creationDate,
                                   @RequestParam LocalDate targetDate,
                                   @RequestParam boolean done, ModelMap modelMap){

        //handling 0 or -1 values
        if (!id.isEmpty()){
            if (  Integer.valueOf(id) <=0  ){
                modelMap.addAttribute("id_err_msg1","id must be 1 or positive");
                modelMap.addAttribute("id_err_msg2",id);
                return "insert2";
            }
        }
                l1.info("--------------------.> now adding the forntend data ---> backend calling services toDo_services.insert_todo ");
        toDo_services.insert_todo(id, username,description,creationDate, targetDate, done);
        return // after insert is done, show all todos
                "redirect:list"; // this redirect to listall.jsp VIEW using /list
        //redierct: {Always put url endpoint name, NOT JSP}
    }



///////////////////////////     INSERT        ///////////////////////////////////////////////////////////////////
    @RequestMapping(value  = "/insert", method= RequestMethod.GET)
    public String get_insert_todo(ModelMap modelMap){
        Todo todo = new Todo(toDo_services.listAllToDo().size()+1,"Unkn","HelloWorld",LocalDate.now(), LocalDate.now().plusYears(1),false );
        modelMap.put("todooo", todo);
        return "insert"; // return  VIEW OUTPUT = insert.jsp // no @ResponseBody
    }
    @RequestMapping(value  = "/insert", method= RequestMethod.POST)
    public String post_insert_data(ModelMap modelMap, Todo todooo){
        toDo_services.insert_todo(String.valueOf(todooo.getId()), todooo.getUsername(), todooo.getDescription(), todooo.getCreationDate(), todooo.getTargetDate(), todooo.getDone());
        return "redirect:list"; //    For such redirects you put ENDPOINT which is "/list"
    }



}



