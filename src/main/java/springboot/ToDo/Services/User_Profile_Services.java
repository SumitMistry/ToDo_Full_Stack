package springboot.ToDo.Services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.ToDo.Model.UserProfile0;
import springboot.ToDo.Model.UserProfile1;
import springboot.ToDo.Repository.Repo_DAO_UserAuth_JPA;
import springboot.ToDo.Repository.Repo_DAO_UserProfile0_JPA;
import springboot.ToDo.Repository.Repo_DAO_UserProfile1_JPA;

@Service
public class User_Profile_Services {

    private final Repo_DAO_UserAuth_JPA repo_dao_userAuth_jpa;

    private final Repo_DAO_UserProfile0_JPA repo_dao_userProfile_0_jpa;

    private final Repo_DAO_UserProfile1_JPA repo_dao_userProfile_1_jpa;

    public User_Profile_Services(Repo_DAO_UserProfile0_JPA repoDaoUserProfileJpa, Repo_DAO_UserAuth_JPA repo_dao_userAuth_jpa, Repo_DAO_UserProfile1_JPA repoDaoUserProfile1Jpa1) {
        super();
        this.repo_dao_userProfile_0_jpa = repoDaoUserProfileJpa;
        this.repo_dao_userAuth_jpa = repo_dao_userAuth_jpa;
        this.repo_dao_userProfile_1_jpa = repoDaoUserProfile1Jpa1;
    }

    public UserProfile0 get_UserProfile0_byUsername(String username) {
        UserProfile0 profile0 = repo_dao_userProfile_0_jpa.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" Usernae not founnd"));
        return profile0;
    }

    public UserProfile1 get_UserProfile1_byUsername(String username) {
        UserProfile1 profile1 = repo_dao_userProfile_1_jpa.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" Usernae not founnd"));
        return profile1;
    }
    public UserProfile0 set_UserProfile0(UserProfile0 userProfile0) {

        // This is an easiest way to store and save object directly, but here we did join this 11 line will not work...
        //        return repo_dao_userProfile_0_jpa.save(userProfile0);


        // Retrieve current user profile
        UserProfile0 current_profile =  repo_dao_userProfile_0_jpa.findByUsername(userProfile0.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        // set data
        current_profile.setCity(userProfile0.getCity());
        current_profile.setF_name(userProfile0.getF_name());
        current_profile.setL_name(userProfile0.getL_name());
        current_profile.setPhone(userProfile0.getPhone());
        current_profile.setBirth_date(userProfile0.getBirth_date());


        // obtained saved_user_profile_0
        UserProfile0 updated_profile =  repo_dao_userProfile_0_jpa.findByUsername(userProfile0.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        System.out.println("--------------------->>>>>>>>>>> " +"\n\n\n\n"+
                "--------------------->" + updated_profile.toString());

        // saved user_profile = fine, now save it to dB
        repo_dao_userProfile_0_jpa.save(updated_profile);

        // returning to show on front-end
        return updated_profile;
    }



    public UserProfile1 set_UserProfile1(UserProfile1 userProfile1) {


        // Retrieve current user profile
        UserProfile1 current_profile1 =  repo_dao_userProfile_1_jpa.findByUsername(userProfile1.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        // set data
        current_profile1.setSex(userProfile1.getSex());


        // obtained saved_user_profile_1
        UserProfile1 updated_profile1 =  repo_dao_userProfile_1_jpa.findByUsername(userProfile1.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

                        System.out.println("--------------------->>>>>>>>>>> " +"\n\n\n\n"+
                                "--------------------->" + updated_profile1.toString());

        // saved user_profile = fine, now save it to dB
        repo_dao_userProfile_1_jpa.save(updated_profile1);

        // returning to show on front-end
        return updated_profile1;
    }





}










