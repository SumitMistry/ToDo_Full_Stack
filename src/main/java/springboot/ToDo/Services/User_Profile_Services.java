package springboot.ToDo.Services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.ToDo.Model.UserProfile;
import springboot.ToDo.Repository.Repo_DAO_UserAuth_JPA;
import springboot.ToDo.Repository.Repo_DAO_UserProfile_JPA;

import java.util.NoSuchElementException;

@Service
public class User_Profile_Services {


    private final Repo_DAO_UserProfile_JPA repo_dao_userProfile_jpa;
    public User_Profile_Services(Repo_DAO_UserProfile_JPA repoDaoUserProfileJpa){
        repo_dao_userProfile_jpa = repoDaoUserProfileJpa;
    }

    public UserProfile get_UserProfile_byUsername(String username){
        UserProfile profile =  repo_dao_userProfile_jpa.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));
        return profile;
    }

    public UserProfile set_UserProfile(UserProfile userProfile){
        return repo_dao_userProfile_jpa.save(userProfile);
    }


    //////// After joining table in Entity, this iis step-2



}







//        // Retrieve current user profile
//        UserProfile current_profile =  repo_dao_userProfile_jpa.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));
//
//        // set data
//        current_profile.setCity(city);
//        current_profile.setF_name(f_name);
//        current_profile.setL_name(l_name);
//        current_profile.setPhone(phone);
//
//        // return c
//        UserProfile updated_profile =  repo_dao_userProfile_jpa.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));
