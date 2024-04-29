package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
//import springboot.ToDo.Model.removed.Todo;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
    ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
    (@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>
        this value can be returning as array int[] or String[], example in update() POST method...below
 */

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
        //List<Todo> outputList = toDo_services.listAllToDo();

        List<Todo> list1 = toDo_Services.findbyALL();
        modelMap.addAttribute("listMapVar", list1);
        l1.debug("POST-validation-check:" + modelMap + l1.toString());
        return "listall";
    }



///////////////////////////     FindBYID     ///////////////////////////
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public String findByID_from_List(@RequestParam(value = "id") int id, ModelMap modelMap){

        System.out.println(toDo_Services.findByID(id));
        //List<Todo> list1 =  toDo_services.findByID_from_List(id);
        modelMap.addAttribute("listMapVar", toDo_Services.findByID(id));
        return "listall";

    }



    @RequestMapping(value = "attach", method = RequestMethod.GET)
    public String get_attach_function(ModelMap modelMap){
        //modelMap.addAttribute("button_code", "success");
        return "listall";
    }

///////////////////////// WORKING


///////////////////////////////////////
    ////////////////////////////////
    //////////////////////////
    ////////////////////
    //////////////
    /////////
    ///



    @Autowired
    private ToDo_Services toDo_Services;

    //////////////////// This end point ONLY used to ADD HARDCODED values into SQL
    @RequestMapping(value = {"hardcode1"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String sprData_jpa_hard_code_data(ModelMap modelMap){

        // ADD: locally add to LIST
        List<Todo> list1 = new ArrayList<>();
        list1.add(new Todo(1,"Sumit@gmail.com", "TableDesc-1", LocalDate.of(2021,02,8), LocalDate.of(2048,10,7),true));
        list1.add(new Todo(2,"MIstrSS@microso.com", "Vrajwilback", LocalDate.of(1987,12,22), LocalDate.of(2031,01,15),true));
        list1.add(new Todo(3,"RuthBVraj@nakamo.com", "Faint2024", LocalDate.of(2004,11,22), LocalDate.of(2025,11,25),false));

        l1.info("\n ----> CONTROLLER: ADDING FAKE hard-coded DATA...");

        list1.addAll(toDo_Services.findbyALL());
        // Map above list to frontend to display on UI
        modelMap.addAttribute("listMapVar",list1);

        // ADD: sent list to SQL
        toDo_Services.insert_list_data_springDataJpa(list1);

        return "listall";
    }

//////////////////// INSERT - SpringDataJPA SQL == insert3 (GET/POST)
    @RequestMapping(value = "/insert3", method = RequestMethod.GET)
    public String get_SprData_JPA_insert(ModelMap modelMap){
        List<Todo> list1 = toDo_Services.findbyALL();

        Todo t1 = new Todo(list1.size() + 1, (String) modelMap.get("uid_email"), "HelloWorld", LocalDate.now(), LocalDate.now().plusYears(1), false);
        modelMap.put("todo_obj_spring_data_jpa2", t1);
        return "insert3_SprDataJPA"; // this returns (jsp file)=view without @RESPONSEBODY
    }
    @RequestMapping(value = "/insert3", method = RequestMethod.POST)
    public String post_SprData_JPA_insert( ModelMap modelMap,
                                           @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2 ,
                                           BindingResult bindingResult, Errors err) {

        //use binding result to find error while validating / entering data
        if (err.hasErrors()  || bindingResult.hasErrors()){
//            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            return "redirect:insert3_SprDataJPA";
            return "insert3_SprDataJPA";
        }


        // add data from front end viewing only
        List<Todo> list1 = toDo_Services.findbyALL();
        list1.add(todo_obj_spring_data_jpa2);
        modelMap.addAttribute("listMapVar",list1);

        // post data to backend SQL
        toDo_Services.insert_list_data_springDataJpa(list1);

        // get data view
        return "listall"; // validation will not be displayed because we have 2 different mpodels, both displaying on same page=List
    }


    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String show_UpdateByID_page(ModelMap modelMap, @RequestParam(value = "id") int id) {

        // Retrieved current record=data
        List<Todo> td0 =  toDo_Services.findByID(id); //toDo_services.findByID_from_List(id);
        System.out.println("--->" + td0);
        //Todo td0 = current_data.get(0);


        // To inject our pre-fill the data from object to model to front_end
        // This model is dupming data into insert3_SprDataJPA
        modelMap.put("todo_obj_spring_data_jpa2", td0.get(0));

        // insertion done in above step now , reloading Listing page
        return "insert3_SprDataJPA";
    }

    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String post_UpdateByID_page(  ModelMap modelMap,
                                         @RequestParam("id") int[] id2, // this result in [ old val, new val], we need new val by user so
                                         @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,
                                         BindingResult bindingResult,
                                         Errors err
    ) {
        // if validation error handle here, handle here:
        if (bindingResult.hasErrors() || err.hasErrors()){
            System.out.println(" YOU HAVE ERRRRRRORS....in INPUT VALIDATIONs"); l1.info(" YOU HAVE ERRRRRRORS....in INPUT VALIDATIONs");
            return "insert3_SprDataJPA";
        }

        // New record object
        Todo t1 = new Todo(id2[1], //this is new id at 1st index
                todo_obj_spring_data_jpa2.getUsername(),
                todo_obj_spring_data_jpa2.getDescription(),
                todo_obj_spring_data_jpa2.getCreationDate(),
                todo_obj_spring_data_jpa2.getTargetDate(),
                todo_obj_spring_data_jpa2.getDone() );

        // if no validation error: update data here
        // delete current old record from index 0 of REQ PARAM value, so we can replace with new data
        toDo_Services.updateByID(id2[0], t1);

        return "redirect:list";
    }


    ///////////////////////////     DELETE     ///////////////////////////
    @RequestMapping(value = "delete")
    public String deleteByID(@RequestParam(value = "id") int id) {
        toDo_Services.deleteByID_springDataJPA(id);
        //toDo_services.deleteByID(id); // this was old implementation for  deleting from local list.
        l1.info("DELETEDD::::::::" + id);
        //return "listall";
        return "redirect:list";
    }






/////////////////////////////     INSERT 2  MANUAL     ///////////////////////////////////////////////////////////////////
//    @RequestMapping(value = "/insert2", method = RequestMethod.GET)
//    public String get_insert_todo2() {
//        return "insert2";
//    }
//
//    @RequestMapping(value = "/insert2", method = RequestMethod.POST)
//    public String post_insert_todo(@RequestParam String id, @NonNull @RequestParam String username, @NonNull @RequestParam String description, @RequestParam LocalDate creationDate, @RequestParam LocalDate targetDate, @RequestParam boolean done, ModelMap modelMap) {
//
//        //handling 0 or -1 values
//        if (!id.isEmpty()) {
//            if (Integer.valueOf(id) <= 0) {
//                modelMap.addAttribute("id_err_msg1", "id must be 1 or positive");
//                modelMap.addAttribute("id_err_msg2", id);
//                return "insert2";
//            }
//        }
//        l1.info("--------------------.> now adding the forntend data ---> backend calling services toDo_services.insert_todo ");
//        toDo_services.insert_todo(id, username, description, creationDate, targetDate, done);
//        return // after insert is done, show all todos
//                "redirect:list"; // this redirect to listall.jsp VIEW using /list
//        //redierct: {Always put url endpoint name, NOT JSP}
//    }
//
//
/////////////////////////////     INSERT   Automatic     ///////////////////////////////////////////////////////////////////
//    @RequestMapping(value = "/insert", method = RequestMethod.GET)
//    public String get_insert_todo(ModelMap modelMap) {
//
//        // making data ready to pre-fill in todo_object
//        String u_retrived = (String) modelMap.get("uid_email");
//        Todo Todo_model_SprDataJPA_obj = new Todo(toDo_services.listAllToDo().size() + 1, u_retrived, "HelloWorld", LocalDate.now(), LocalDate.now().plusYears(1), false);
//
//        // this injects our pre-fill the data from object to model to front_end
//        modelMap.put("todooo", Todo_model_SprDataJPA_obj);
//
//        return "insert"; // return  VIEW OUTPUT = insert.jsp // no @ResponseBody
//    }
//
//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public String post_insert_data(ModelMap modelMap,
//                                   @Valid @ModelAttribute("todooo") Todo todooo,   //   @Valid can be used to validate beans "BEANS" ONLY
//                                   BindingResult bindingResult) {
//
//        // binding validation errors if found in form, user will be redirected back to the same page.
//        // Errors will be hidden bcos of this...
//        if (bindingResult.hasErrors()) {
//            int x = bindingResult.getErrorCount();
//            System.out.println(x);
//            l1.info(" error  count = " + x);
//            return "insert";    //  --> "redirect:insert" returns   /insert   endpoint
//            // return "insert";   --> return "insert"    returns    insert.jsp
//        }
//
//        // retrieving username(=u_retrived) and hard coding username(u_retrived) intentionally into object while inserting as below.
//        // so basically username is NOT TWO way binding.
//        // except user, everything else will be 2 way binding...
//        String u_retrived = (String) modelMap.get("uid_email");
//
//        // Validation done under if(), now insert this data
//        toDo_services.insert_todo(String.valueOf(todooo.getId()), u_retrived, todooo.getDescription(), todooo.getCreationDate(), todooo.getTargetDate(), todooo.getDone());
//        return "redirect:list"; //    For such redirects you put ENDPOINT which is "/list"
//    }





}



