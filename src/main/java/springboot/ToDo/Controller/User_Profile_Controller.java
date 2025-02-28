package springboot.ToDo.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Model.UserProfile;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Repository.Repo_DAO_UserProfile_JPA;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.User_Profile_Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/")
@SessionAttributes({"uid_email", "pass", "totally" })
@Validated //  especially while using  @RequestParam.... this @validated enables validation(like @past, @future, @max min) within method argument direct .... @valid will not work here
public class User_Profile_Controller {

//    @Autowired
//    private Repo_DAO_UserProfile_JPA repo_dao_userProfile_jpa;

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
        UserProfile up1_obj = user_profile_services.get_UserProfile_byUsername(username);
        modelMap.addAttribute("userProfile_obj_modelAttribute", up1_obj);

        // Retrieving and posting User's join table data [userAuth] + [userProfile]
        modelMap.addAttribute("profile1", up1_obj.toString());

        // Retrieving and sending data to frontend. Data = [userAuth] table data only
        modelMap.addAttribute("profile2",up1_obj.getUserAuth().toString());


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
                                   //Use @Validated on the controller to enable @RequestParam validation.
                                   ModelMap modelMap,
                                   @ModelAttribute("userProfile_obj_modelAttribute") @Valid UserProfile new_userProfile,
                                   BindingResult bindingResult1, Errors err1){


        //use binding result to find error while validating / entering data in form, it throws error at that tag
        if (err1.hasErrors() || bindingResult1.hasErrors()) {
//            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            return "redirect:insert3_SprDataJPA";
            return "userProfile";
        }

        new_userProfile.setPhone(phone);
        new_userProfile.setL_name(l_name);
        new_userProfile.setF_name(f_name);
        new_userProfile.setCity(city);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Ensure correct format
//            LocalDate birthDateee = LocalDate.parse(birthDate, formatter);
        new_userProfile.setBirth_date(birth_date);


        UserProfile retrived_user_prof =  user_profile_services.set_UserProfile(new_userProfile);



        modelMap.addAttribute("profile3", " [Success] : UserProfile table: Updated successfully");
        // "retrived_user_prof" will contain joined table data from tables [userAuth] + [userProfile]
        modelMap.addAttribute("profile4", " Retrieved data : " + retrived_user_prof.toString()) ;
        return "userProfile";

    }

}
