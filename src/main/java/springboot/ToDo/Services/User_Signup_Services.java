package springboot.ToDo.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import springboot.ToDo.Model.User;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW )
public class User_Signup_Services {

    private final Repo_DAO_User_JPA repo_dao_user_jpa;

    public User_Signup_Services(Repo_DAO_User_JPA user_jpa){
        super();
        this.repo_dao_user_jpa = user_jpa;
    }

    // CREATE- INSERT  / SAVE NEW user
    public User insert_user(User user){
        repo_dao_user_jpa.save(user);
        User retrived_user = repo_dao_user_jpa.findById(user.getUid()).orElseThrow(() -> new NoSuchElementException("User name ENTEREDD not found! weirdo"));
        return retrived_user;
    }


}
