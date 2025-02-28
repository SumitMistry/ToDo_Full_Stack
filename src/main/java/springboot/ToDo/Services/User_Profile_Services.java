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

    private final Repo_DAO_UserAuth_JPA repo_dao_userAuth_jpa;

    private final Repo_DAO_UserProfile_JPA repo_dao_userProfile_jpa;

    public User_Profile_Services(Repo_DAO_UserProfile_JPA repoDaoUserProfileJpa, Repo_DAO_UserAuth_JPA repo_dao_userAuth_jpa) {
        super();
        this.repo_dao_userProfile_jpa = repoDaoUserProfileJpa;
        this.repo_dao_userAuth_jpa = repo_dao_userAuth_jpa;
    }

    public UserProfile get_UserProfile_byUsername(String username) {
        UserProfile profile = repo_dao_userProfile_jpa.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" Usernae not founnd"));
        return profile;
    }

    public UserProfile set_UserProfile(UserProfile userProfile) {

        // This is an easiest way to store and save object directly, but here we did join this 11 line will not work...
        //        return repo_dao_userProfile_jpa.save(userProfile);



        // Retrieve current user profile
        UserProfile current_profile =  repo_dao_userProfile_jpa.findByUsername(userProfile.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        // set data
        current_profile.setCity(userProfile.getCity());
        current_profile.setF_name(userProfile.getF_name());
        current_profile.setL_name(userProfile.getL_name());
        current_profile.setPhone(userProfile.getPhone());
        current_profile.setBirth_date(userProfile.getBirth_date());

        // obtained saved_user_profile
        UserProfile updated_profile =  repo_dao_userProfile_jpa.findByUsername(userProfile.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

                System.out.println("--------------------->>>>>>>>>>> " +"\n\n\n\n"+
                "--------------------->" + updated_profile.toString());

        // saved user_profile = fine, now save it to dB
        repo_dao_userProfile_jpa.save(updated_profile);

        // returning to show on front-end
        return updated_profile;
    }
}










