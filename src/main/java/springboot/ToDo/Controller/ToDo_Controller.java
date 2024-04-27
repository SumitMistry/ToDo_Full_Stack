package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("api/todo/")
@SessionAttributes({"uid_email", "pass"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..

public class ToDo_Controller {

    Logger l1 = LoggerFactory.getLogger(Class.class);

    ///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
                            private final ToDo_Services toDo_services;

                            public ToDo_Controller(ToDo_Services toDo_services) {
                                super();
                                this.toDo_services = toDo_services;
                            }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll_todos(ModelMap modelMap) {
        List<Todo> outputList = toDo_services.listAllToDo();
        modelMap.addAttribute("listMapVar", outputList);
        l1.debug("POST-validation-check:" + modelMap + outputList.toString());
        return "listall";
    }


///////////////////////////     INSERT 2  MANUAL     ///////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/insert2", method = RequestMethod.GET)
    public String get_insert_todo2() {
        return "insert2";
    }

    @RequestMapping(value = "/insert2", method = RequestMethod.POST)
    public String post_insert_todo(@RequestParam String id, @NonNull @RequestParam String username, @NonNull @RequestParam String description, @RequestParam LocalDate creationDate, @RequestParam LocalDate targetDate, @RequestParam boolean done, ModelMap modelMap) {

        //handling 0 or -1 values
        if (!id.isEmpty()) {
            if (Integer.valueOf(id) <= 0) {
                modelMap.addAttribute("id_err_msg1", "id must be 1 or positive");
                modelMap.addAttribute("id_err_msg2", id);
                return "insert2";
            }
        }
        l1.info("--------------------.> now adding the forntend data ---> backend calling services toDo_services.insert_todo ");
        toDo_services.insert_todo(id, username, description, creationDate, targetDate, done);
        return // after insert is done, show all todos
                "redirect:list"; // this redirect to listall.jsp VIEW using /list
        //redierct: {Always put url endpoint name, NOT JSP}
    }


///////////////////////////     INSERT   Automatic     ///////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String get_insert_todo(ModelMap modelMap) {

        // making data ready to pre-fill in todo_object
        String u_retrived = (String) modelMap.get("uid_email");
        Todo todo_obj = new Todo(toDo_services.listAllToDo().size() + 1, u_retrived, "HelloWorld", LocalDate.now(), LocalDate.now().plusYears(1), false);

        // this injects our pre-fill the data from object to model to front_end
        modelMap.put("todooo", todo_obj);

        return "insert"; // return  VIEW OUTPUT = insert.jsp // no @ResponseBody
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String post_insert_data(ModelMap modelMap,
                                   @Valid @ModelAttribute("todooo") Todo todooo,   //   @Valid can be used to validate beans "BEANS" ONLY
                                   BindingResult bindingResult) {

        // binding validation errors if found in form, user will be redirected back to the same page.
        // Errors will be hidden bcos of this...
        if (bindingResult.hasErrors()) {
            int x = bindingResult.getErrorCount();
            System.out.println(x);
            l1.info(" error  count = " + x);
            return "insert";    //  --> "redirect:insert" returns   /insert   endpoint
            // return "insert";   --> return "insert"    returns    insert.jsp
        }

        // retrieving username(=u_retrived) and hard coding username(u_retrived) intentionally into object while inserting as below.
        // so basically username is NOT TWO way binding.
        // except user, everything else will be 2 way binding...
        String u_retrived = (String) modelMap.get("uid_email");
        toDo_services.insert_todo(String.valueOf(todooo.getId()), u_retrived, todooo.getDescription(), todooo.getCreationDate(), todooo.getTargetDate(), todooo.getDone());
        return "redirect:list"; //    For such redirects you put ENDPOINT which is "/list"
    }

///////////////////////////     DELETE     ///////////////////////////
    @RequestMapping(value = "delete")
    public String deleteByID(@RequestParam(value = "id") int id) {
        toDo_services.deleteByID(id);
        System.out.println("-------------------------------------------------deleted:::" + id);
        l1.info("DELETEDD::::::::" + id);
        l1.info("-------------------------------------------------deleted:::" + id);
        l1.info("DELETEDD::::::::" + id);
        //return "listall";
        return "redirect:list";
    }

///////////////////////////     FindBYID     ///////////////////////////
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public String findByID_from_List(@RequestParam(value = "id") int id, ModelMap modelMap){
        List<Todo> list1 =  toDo_services.findByID_from_List(id);
        modelMap.addAttribute("listMapVar", list1);
        return "listall";

    }


///////////////////////////     UPDATE GET + POST     ///////////////////////////
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String show_UpdateByID_page(ModelMap modelMap, @RequestParam(value = "id") int id) {

        // Retrieved current record=data
        List<Todo> current_data =  toDo_services.findByID_from_List(id);
        Todo td0 = current_data.get(0);

        // delete current record
        deleteByID(id);

                //        // HArd coded data
                //        Todo td1 = new Todo(99, "i Gayo User", "HelloWorld", LocalDate.now(), LocalDate.now().plusYears(1), false);

        // To inject our pre-fill the data from object to model to front_end
        modelMap.put("todooo", td0);

        // insertion done in above step now , reloading Listing page
        return "insert";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String post_UpdateByID_page(  ModelMap modelMap,
                                         @RequestParam(value = "id") int id,
                                         @ModelAttribute Todo todoo9,
                                         BindingResult bindingResult
                                       ) {
        // if validation error handle here, handle here:
        if (bindingResult.hasErrors()){
                            System.out.println(" YOU HAVE ERRRRRRORS....in INPUT VALIDATIONs"); l1.info(" YOU HAVE ERRRRRRORS....in INPUT VALIDATIONs");
            return "insert";
        }

        // if no validation error: Insert data here
        toDo_services.insert_todo(String.valueOf(todoo9.getId()) ,todoo9.getUsername(), todoo9.getDescription(), todoo9.getCreationDate(), todoo9.getTargetDate(), todoo9.getDone());
        return "redirect:list";
    }



    @RequestMapping(value = "attach", method = RequestMethod.GET)
    public String get_attach_function(ModelMap modelMap){
                                //modelMap.addAttribute("button_code", "success");
        return "listall";
    }

}



