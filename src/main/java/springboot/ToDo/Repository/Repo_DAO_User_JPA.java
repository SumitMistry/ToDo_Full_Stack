package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.User;

import java.util.Optional;

@Repository
public interface Repo_DAO_User_JPA extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
