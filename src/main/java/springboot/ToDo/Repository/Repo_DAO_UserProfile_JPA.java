package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.UserProfile;

import java.util.Optional;

@Repository
public interface Repo_DAO_UserProfile_JPA extends JpaRepository<UserProfile , Integer> {


    Optional<UserProfile> findByUsername(String username);



}
