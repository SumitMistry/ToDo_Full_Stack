package springboot.ToDo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import springboot.ToDo.Model.User;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;
import springboot.ToDo.SecurityConfig.PassEncoder_config;

@Service
@Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW )
public class User_Signup_Services {

    private final Repo_DAO_User_JPA repo_dao_user_jpa;

    @Autowired
    private PassEncoder_config passEncoder_config;

    public User_Signup_Services(Repo_DAO_User_JPA user_jpa){
        super();
        this.repo_dao_user_jpa = user_jpa;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    // INSERT + RAW
    public User insert_user_raw(String incoming_username, String incoming_raw_pass){

        String encode_bcryp_pass = passEncoder_config.passwordEncoder().encode(incoming_raw_pass);
        User final_user_to_Add = new User(incoming_username,incoming_raw_pass,encode_bcryp_pass);

        if(incoming_username.isEmpty() || incoming_raw_pass.isEmpty()){
            throw  new ResponseStatusException(HttpStatusCode.valueOf(404), "User name / Pass CAN NOT be empty ");
        }
        else {
            User retrived_user = repo_dao_user_jpa.save(final_user_to_Add);
            return retrived_user;
        }
    }




    // INSERT + ENCODE == BCryptPasswordEncoder
    public User insert_user_Bcrypted_encoded(String input_username, String input_pass){

        String encode_bcryp_pass = passEncoder_config.passwordEncoder().encode(input_pass);

        User user_encrypted = new User(input_username, input_pass, encode_bcryp_pass);
        return repo_dao_user_jpa.save(user_encrypted);
    }



    // NO use
    // GET + DECODED == BCryptPasswordEncoder ---> not possible // DECODING is not Allowed !
    // You cannot retrieve or decrypt a Bcrypt-hashed password because Bcrypt is a one-way hashing algorithm. This is by design for security reasons.
    public  User get_user_bcryp_decoded(User entered_user){
        return  null;
    }



    // Verify / match user entered password is Match or NOT ?
    public boolean validate_User_Login(String incoming_username, String incoming_raw_pass){
        String encoded_pass = repo_dao_user_jpa.findByUsername(incoming_username).getPassword_encoded();
        boolean check = passEncoder_config.passwordEncoder().matches( incoming_raw_pass, encoded_pass );
        return check;
    }

}
