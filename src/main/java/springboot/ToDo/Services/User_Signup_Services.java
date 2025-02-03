package springboot.ToDo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import springboot.ToDo.Model.User;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;
import springboot.ToDo.SecurityConfig.SpringSecurityConfiguration;

@Service
@Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW )
public class User_Signup_Services {

    private final Repo_DAO_User_JPA repo_dao_user_jpa;

    @Autowired
    private SpringSecurityConfiguration springSecurityConfiguration;

    public User_Signup_Services(Repo_DAO_User_JPA user_jpa){
        super();
        this.repo_dao_user_jpa = user_jpa;
        //        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    // SIGNUP: INSERT + RAW password
    public User signup_insert_raw_pass(String incoming_username, String incoming_raw_pass){

        User final_user_to_Add = new User(incoming_username,incoming_raw_pass);

        // check if username is empty
        if(incoming_username.isEmpty() || incoming_raw_pass.isEmpty() ||   final_user_to_Add == null){
            throw  new ResponseStatusException(HttpStatusCode.valueOf(404), "User name / Pass CAN NOT be empty ");
        }
        else {
            // Now add it to database using JPA
            User retrieved_user_after_signup = repo_dao_user_jpa.save(final_user_to_Add);
            return retrieved_user_after_signup;
        }
    }


   // SIGNUP: INSERT + ENCODED password
    public User signup_insert_encoded_pass(String incoming_username, String incoming_raw_pass){

        if(incoming_username.isEmpty() || incoming_raw_pass.isEmpty()){
            throw  new ResponseStatusException(HttpStatusCode.valueOf(404), "User name / Pass CAN NOT be empty ");
        }
        else {
            // ENCODE incoming raw password
            String encoded_bcryp_pass = springSecurityConfiguration.passwordEncoder_method().encode(incoming_raw_pass);

            // Creating ENCODED user object
            User user_retrieved_with_null_encode_value   = repo_dao_user_jpa.findByUsername(incoming_username).get();
            user_retrieved_with_null_encode_value.setPassword_encoded(encoded_bcryp_pass);

            User sinup_user_now_added_encoding = user_retrieved_with_null_encode_value;

            return sinup_user_now_added_encoding;
        }
    }

}













//    // NO NEED ------------------------------------------
//    // INSERT + ENCODE == BCryptPasswordEncoder
//    public User insert_user_Bcrypted_encoded(String input_username, String input_pass){
//
//        String encode_bcryp_pass = passEncoder_config.passwordEncoder().encode(input_pass);
//
//        User user_encrypted = new User(input_username, input_pass, encode_bcryp_pass);
//        return repo_dao_user_jpa.save(user_encrypted);
//    }




//    /// NO NEED ------------------------------------------
//    // GET + DECODED == BCryptPasswordEncoder ---> not possible // DECODING is not Allowed !
//    // You cannot retrieve or decrypt a Bcrypt-hashed password because Bcrypt is a one-way hashing algorithm. This is by design for security reasons.
//    public  User get_user_bcryp_decoded(User entered_user){
//        return  null;
//    }



    /*
        public User insert_user_raw(User user){
        repo_dao_user_jpa.save(user);
        User retrived_user = repo_dao_user_jpa.findById(user.getUid()).orElseThrow(() -> new NoSuchElementException("User name ENTEREDD not found! weirdo"));

        System.out.println("77777777777777"+ retrived_user.toString());
        return retrived_user;
    }
     */
