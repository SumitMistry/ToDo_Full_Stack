package springboot.ToDo.Services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.ToDo.Model.UserProfile_pg0;
import springboot.ToDo.Model.UserProfile_pg1;
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

    public UserProfile_pg0 get_UserProfile0_byUsername(String username) {
        UserProfile_pg0 profile0 = repo_dao_userProfile_0_jpa.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" Usernae not founnd"));
        return profile0;
    }

    public UserProfile_pg1 get_UserProfile1_byUsername(String username) {
        UserProfile_pg1 profile1 = repo_dao_userProfile_1_jpa.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" Usernae not founnd"));
        return profile1;
    }
    public UserProfile_pg0 set_UserProfile0(UserProfile_pg0 userProfilePg0) {

        // This is an easiest way to store and save object directly, but here we did join this 11 line will not work...
        //        return repo_dao_userProfile_0_jpa.save(userProfilePg0);


        // Retrieve current user profile
        UserProfile_pg0 current_profile =  repo_dao_userProfile_0_jpa.findByUsername(userProfilePg0.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        // set data
        current_profile.setCity(userProfilePg0.getCity());
        current_profile.setF_name(userProfilePg0.getF_name());
        current_profile.setL_name(userProfilePg0.getL_name());
        current_profile.setPhone(userProfilePg0.getPhone());
        current_profile.setBirth_date(userProfilePg0.getBirth_date());


        // obtained saved_user_profile_0
        UserProfile_pg0 updated_profile =  repo_dao_userProfile_0_jpa.findByUsername(userProfilePg0.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        System.out.println("--------------------->>>>>>>>>>> " +"\n\n\n\n"+
                "--------------------->" + updated_profile.toString());

        // saved user_profile = fine, now save it to dB
        repo_dao_userProfile_0_jpa.save(updated_profile);

        // returning to show on front-end
        return updated_profile;
    }



    public UserProfile_pg1 set_UserProfile1(UserProfile_pg1 userProfilePg1) {


        // Retrieve current user profile
        UserProfile_pg1 current_profile1 =  repo_dao_userProfile_1_jpa.findByUsername(userProfilePg1.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

        // set data
        current_profile1.setSex(userProfilePg1.getSex());


        // obtained saved_user_profile_1
        UserProfile_pg1 updated_profile1 =  repo_dao_userProfile_1_jpa.findByUsername(userProfilePg1.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" Usernae not founnd"));

                        System.out.println("--------------------->>>>>>>>>>> " +"\n\n\n\n"+
                                "--------------------->" + updated_profile1.toString());

        // saved user_profile = fine, now save it to dB
        repo_dao_userProfile_1_jpa.save(updated_profile1);

        // returning to show on front-end
        return updated_profile1;
    }





}










