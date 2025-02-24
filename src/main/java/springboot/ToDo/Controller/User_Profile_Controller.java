package springboot.ToDo.Controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Model.UserProfile;
import springboot.ToDo.Repository.Repo_DAO_UserProfile_JPA;
import springboot.ToDo.Services.User_Profile_Services;

@Controller
//@RequestMapping("/")
@SessionAttributes({"uid_email", "pass", "totally" })
public class User_Profile_Controller {

    @Autowired
    private Repo_DAO_UserProfile_JPA repo_dao_userProfile_jpa;
    @Autowired
    private User_Profile_Services user_profile_services;

    //////////////////////  Profile: GET  //////////////////////////////////////
    //@ResponseBody
    @RequestMapping(value = {"profile", "profile/"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String get_user_profile(@SessionAttribute(value = "uid_email") String username,
                                   ModelMap modelMap){
        UserProfile up1_obj = repo_dao_userProfile_jpa.findByUsername(username).get();
        modelMap.addAttribute("userProfile_obj_modelAttribute", up1_obj);
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
                                   @ModelAttribute("userProfile_obj_modelAttribute") UserProfile new_userProfile,
                                   ModelMap modelMap){

        new_userProfile.setPhone(phone);
        new_userProfile.setL_name(l_name);
        new_userProfile.setF_name(f_name);
        new_userProfile.setCity(city);

        UserProfile retrived_user_prof =  user_profile_services.set_UserProfile(new_userProfile);
        modelMap.addAttribute("profile1", " [Success] : UserProfile table: Updated successfully");
        modelMap.addAttribute("profile2", " Retrieved data : " + retrived_user_prof.toString()) ;
        return "userProfile";

    }

}
