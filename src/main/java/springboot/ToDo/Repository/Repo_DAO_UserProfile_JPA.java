package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.UserProfile;

@Repository
public interface Repo_DAO_UserProfile_JPA extends JpaRepository<UserProfile , Integer> {

}
