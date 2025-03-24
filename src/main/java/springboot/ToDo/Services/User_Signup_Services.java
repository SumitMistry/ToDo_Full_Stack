package springboot.ToDo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import springboot.ToDo.Model.UserAuth;
import springboot.ToDo.Model.UserProfile_pg0;
import springboot.ToDo.Model.UserProfile_pg1;
import springboot.ToDo.Repository.Repo_DAO_UserAuth_JPA;
import springboot.ToDo.Repository.Repo_DAO_UserProfile0_JPA;
import springboot.ToDo.Repository.Repo_DAO_UserProfile1_JPA;
import springboot.ToDo.SecurityConfig.SpringSecurityConfiguration;

@Service
@Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW )
public class User_Signup_Services {

    @Autowired
    private SpringSecurityConfiguration springSecurityConfiguration;



    private final Repo_DAO_UserProfile0_JPA repo_dao_userProfile0_jpa;
    private final Repo_DAO_UserProfile1_JPA repo_dao_userProfile1_jpa;

    private final Repo_DAO_UserAuth_JPA repo_dao_userAuth_jpa;
    public User_Signup_Services(Repo_DAO_UserAuth_JPA userAuth_jpa, Repo_DAO_UserProfile0_JPA repoDaoUserProfile0Jpa, Repo_DAO_UserProfile1_JPA repoDaoUserProfile1Jpa){
        super();
        this.repo_dao_userAuth_jpa = userAuth_jpa;
        this.repo_dao_userProfile0_jpa = repoDaoUserProfile0Jpa;
        this.repo_dao_userProfile1_jpa = repoDaoUserProfile1Jpa;
        //        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    // SIGNUP: INSERT + RAW password
    public UserAuth signup_insert_raw_pass(String incoming_username, String incoming_raw_pass){

        UserAuth final_user_to_AddAuth = new UserAuth(incoming_username,incoming_raw_pass);

        // check if username is empty
        if(incoming_username.isEmpty() || incoming_raw_pass.isEmpty() ||   final_user_to_AddAuth == null){
            throw  new ResponseStatusException(HttpStatusCode.valueOf(404), "UserAuth name / Pass CAN NOT be empty ");
        }
        else {
            // Now add it to database using JPA
            UserAuth retrieved_user_after_signupAuth = repo_dao_userAuth_jpa.save(final_user_to_AddAuth);
            return retrieved_user_after_signupAuth;
        }
    }


   // SIGNUP: INSERT + ENCODED password + ROLE
    public UserAuth signup_insert_encoded_pass_and_role(String incoming_username, String incoming_raw_pass, String[] roles){

        if(incoming_username.isEmpty() || incoming_raw_pass.isEmpty()){
            throw  new ResponseStatusException(HttpStatusCode.valueOf(404), "UserAuth name / Pass CAN NOT be empty ");
        }
        else {
            // ENCODE incoming raw password
            String encoded_bcryp_pass = springSecurityConfiguration.passwordEncoder_method().encode(incoming_raw_pass);
            // springSecurityConfig3.passwordEncoder_method().encode(incoming_raw_pass);

            // Creating ENCODED user object
            UserAuth user_Auth_retrieved_with_null_encode_value = repo_dao_userAuth_jpa.findByUsername(incoming_username).get();
            user_Auth_retrieved_with_null_encode_value.setPassword_encoded(encoded_bcryp_pass);
            user_Auth_retrieved_with_null_encode_value.setUser_role(roles);

            UserAuth sinup_user_now_added_encodingAuth = user_Auth_retrieved_with_null_encode_value;

            return sinup_user_now_added_encodingAuth;
        }
    }



    public UserProfile_pg0 add_ONLY_username_in_UserProfile0(String usr_name){
        if (usr_name.isEmpty()) {

        }
            UserProfile_pg0 user_object_for_UserProfilePg0 = new UserProfile_pg0(usr_name);
            repo_dao_userProfile0_jpa.save(user_object_for_UserProfilePg0);
            return user_object_for_UserProfilePg0;
    }


    public UserProfile_pg1 add_ONLY_username_in_UserProfile1(String usr_name){
        if (usr_name.isEmpty()) {

        }
        UserProfile_pg1 user_object_for_UserProfilePg1 = new UserProfile_pg1(usr_name);
        repo_dao_userProfile1_jpa.save(user_object_for_UserProfilePg1);
        return user_object_for_UserProfilePg1;
    }


}













//    // NO NEED ------------------------------------------
//    // INSERT + ENCODE == BCryptPasswordEncoder
//    public UserAuth insert_user_Bcrypted_encoded(String input_username, String input_pass){
//
//        String encode_bcryp_pass = passEncoder_config.passwordEncoder().encode(input_pass);
//
//        UserAuth user_encrypted = new UserAuth(input_username, input_pass, encode_bcryp_pass);
//        return repo_dao_user_Auth_jpa.save(user_encrypted);
//    }




//    /// NO NEED ------------------------------------------
//    // GET + DECODED == BCryptPasswordEncoder ---> not possible // DECODING is not Allowed !
//    // You cannot retrieve or decrypt a Bcrypt-hashed password because Bcrypt is a one-way hashing algorithm. This is by design for security reasons.
//    public  UserAuth get_user_bcryp_decoded(UserAuth entered_user){
//        return  null;
//    }



    /*
        public UserAuth insert_user_raw(UserAuth user){
        repo_dao_user_Auth_jpa.save(user);
        UserAuth retrived_user = repo_dao_user_Auth_jpa.findById(user.getUid()).orElseThrow(() -> new NoSuchElementException("UserAuth name ENTEREDD not found! weirdo"));

        System.out.println("77777777777777"+ retrived_user.toString());
        return retrived_user;
    }
     */
