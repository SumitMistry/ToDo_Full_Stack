package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.UserProfile0;

import java.util.Optional;

@Repository
public interface Repo_DAO_UserProfile0_JPA extends JpaRepository<UserProfile0, Integer> {

    Optional<UserProfile0> findByUsername(String username);

}
