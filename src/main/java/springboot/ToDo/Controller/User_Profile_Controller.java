package springboot.ToDo.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.UserProfile_pg0;
import springboot.ToDo.Model.UserProfile_pg1;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Services.User_Profile_Services;

import java.time.LocalDate;
import java.util.Arrays;

@Controller
//@RequestMapping("/")
@SessionAttributes({"uid_email", "pass", "totally" })
@Validated //  especially while using  @RequestParam.... this @validated enables validation(like @past, @future, @max min) within method argument direct .... @valid will not work here
@Tag(name = "Controller# 3 = User_Profile Management", description = "SM: Operations related to User_Profile") // Now, APIs will be grouped under "Todo Management" in Swagger UI. ✅ // used to group related API endpoints in the Swagger UI. It helps organize APIs by functionality, making them easier to understand and navigate.
public class User_Profile_Controller {

//    @Autowired
//    private Repo_DAO_UserProfile0_JPA repo_dao_userProfile_jpa;

    @Autowired
    private User_Profile_Services user_profile_services;

    @Autowired
    private Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa;
    //////////////////////  Profile: GET  //////////////////////////////////////
    //@ResponseBody
    @RequestMapping(value = {"profile", "profile/"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String get_user_profile(@SessionAttribute(value = "uid_email") String username,
                                   ModelMap modelMap) throws JsonProcessingException {

        // Retrieving Profile0 data to frontend from table [UserProfile_pg0]
        UserProfile_pg0 up0_obj = user_profile_services.get_UserProfile0_byUsername(username);
        modelMap.addAttribute("userProfile0_obj_modelAttribute", up0_obj);

        // Retrieving Profile1 data to frontend from table [UserProfile_pg1]
        UserProfile_pg1 up1_obj = user_profile_services.get_UserProfile1_byUsername(username);
        modelMap.addAttribute("up1sex", up1_obj.getSex() != null    ?   up1_obj.getSex().name() : "");

        // Retrieving join table data to frontend from table = [userAuth] + [userProfile0]
        modelMap.addAttribute("profile_a", up0_obj.toString());
        // Retrieving join table data to frontend from table = [userAuth]only
        modelMap.addAttribute("profile_b",up0_obj.getUserAuth().toString());


        // posting all todo belong to current user
        modelMap.addAttribute("profile_all_todos", Arrays.toString(repo_dao_springData_todo_jpa.findByUsername(username).get().toArray()));

        return "userProfile";
    }

    //////////////////////  Profile: Updates-POST //////////////////////////////////////
    //@ResponseBody
    @RequestMapping(value = {"profile", "profile/"}, method = RequestMethod.POST)
    public String set_user_profile(@SessionAttribute(value = "uid_email") String username,
                                   @RequestParam(value = "f_name") String f_name,
                                   @RequestParam(value = "l_name") String l_name,
                                   @RequestParam(value = "city") String city,
                                   @RequestParam(value = "phone") String phone,
                                   @RequestParam(value = "birth_date" )  LocalDate birth_date, // While using @RequestParam, @valid will not work, you should manually validate the value, @valid only works for Modelattibute only.

                                   @RequestParam(value = "sex") UserProfile_pg1.Sex sex,
                                   //Use @Validated on the controller to enable @RequestParam validation.
                                   ModelMap modelMap,
                                   @ModelAttribute("userProfile0_obj_modelAttribute") @Valid UserProfile_pg0 new_userProfilePg0,
                                   @Valid UserProfile_pg1 new_userProfilePg1,
                                   BindingResult bindingResult1, Errors err1){


        //use binding result to find error while validating / entering data in form, it throws error at that tag
        if (err1.hasErrors() || bindingResult1.hasErrors()) {
//            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            return "redirect:insert3_SprDataJPA";
            return "userProfile";
        }

        // --Profile-1 adjustment
            new_userProfilePg0.setPhone(phone);
            new_userProfilePg0.setL_name(l_name);
            new_userProfilePg0.setF_name(f_name);
            new_userProfilePg0.setCity(city);
                    //            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Ensure correct format
                    //            LocalDate birthDateee = LocalDate.parse(birthDate, formatter);
            new_userProfilePg0.setBirth_date(birth_date);
            UserProfile_pg0 retrived_user_prof0 =  user_profile_services.set_UserProfile0(new_userProfilePg0);

        // --Profile-2 adjustment
            new_userProfilePg1.setSex(sex);
            UserProfile_pg1 retrived_user_prof1 =  user_profile_services.set_UserProfile1(new_userProfilePg1);
            // Sending retrieved SEX values from backend to radio button=frontend
            modelMap.addAttribute("up1sex", retrived_user_prof1.getSex().name());



        modelMap.addAttribute("profile3", " [Success] : UserProfile 0 + 1  table: Updated successfully");
        // "retrived_user_prof" will contain joined table data from tables [userAuth] + [userProfile0]
        modelMap.addAttribute("profile4", " Retrieved data : " + retrived_user_prof0.toString()) ;
        // "retrived_user_prof" will contain joined table data from tables [userAuth] + [userProfile1]
        modelMap.addAttribute("profile5", " Retrieved data : " + retrived_user_prof1) ;
        return "userProfile";

    }

}
