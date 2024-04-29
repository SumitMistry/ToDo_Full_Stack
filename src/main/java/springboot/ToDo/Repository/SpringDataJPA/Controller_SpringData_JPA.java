package springboot.ToDo.Repository.SpringDataJPA;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo_model_SprDataJPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@NoArgsConstructor
@RequestMapping("api/todo/")
@SessionAttributes({"uid_email" , "pass" })
public class Controller_SpringData_JPA {

    @Autowired
    private Service_SpringData_JPA service_springData_jpa;

    Controller_SpringData_JPA(Service_SpringData_JPA service_springData_jpa){
        this.service_springData_jpa = service_springData_jpa;
    }

    Logger l1 = LoggerFactory.getLogger(Class.class);




    List<Todo_model_SprDataJPA> list1 = new ArrayList<>();

    //////////////////// This end point ONLY used to ADD HARDCODED values into SQL
    @RequestMapping(value = {"hardcode1"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String sprData_jpa_hard_code_data(ModelMap modelMap){


        // ADD: locally add to LIST
        list1.add(new Todo_model_SprDataJPA(1,"Sumit@gmail.com", "TableDesc-1", LocalDate.of(2021,02,8), LocalDate.of(2048,10,7),true));
        list1.add(new Todo_model_SprDataJPA(2,"MIstrSS@microso.com", "Vrajwilback", LocalDate.of(1987,12,22), LocalDate.of(2031,01,15),true));
        list1.add(new Todo_model_SprDataJPA(3,"RuthBVraj@nakamo.com", "Faint2024", LocalDate.of(2004,11,22), LocalDate.of(2025,11,25),false));

        l1.info("\n ----> CONTROLLER: ADDING FAKE hard-coded DATA...");

        // Map above list to frontend to display on UI
        modelMap.addAttribute("listMapVar",list1);

        // ADD: sent list to SQL
        service_springData_jpa.springDataJpa_insert_data(list1);

        return "listall";
    }

//////////////////// To insert values into SQL == insert3 (GET/POST)
    @RequestMapping(value = "/insert3", method = RequestMethod.GET)
    public String get_SprData_JPA_insert(ModelMap modelMap){

        Todo_model_SprDataJPA t1 = new Todo_model_SprDataJPA(list1.size() + 1, (String) modelMap.get("uid_email"), "HelloWorld", LocalDate.now(), LocalDate.now().plusYears(1), false);
        modelMap.put("todo_obj_spring_data_jpa2", t1);
        return "insert3_SprDataJPA"; // this returns (jsp file)=view without @RESPONSEBODY
    }
    @RequestMapping(value = "/insert3", method = RequestMethod.POST)
    public String post_SprData_JPA_insert( ModelMap modelMap,
                                           @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo_model_SprDataJPA todo_obj_spring_data_jpa2 ,
                                           BindingResult bindingResult, Errors err) {

        //use binding result to find error while validating / entering data
        if (err.hasErrors()  || bindingResult.hasErrors()){
//            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            return "redirect:insert3_SprDataJPA";
            return "insert3_SprDataJPA";
        }


        // add data from front end viewing only
        list1.add(todo_obj_spring_data_jpa2);
        modelMap.addAttribute("listMapVar",list1);

        // post data to backend SQL
         service_springData_jpa.springDataJpa_insert_data(list1);

        // get data view
        return "listall"; // validation will not be displayed because we have 2 different mpodels, both displaying on same page=List
    }



}
