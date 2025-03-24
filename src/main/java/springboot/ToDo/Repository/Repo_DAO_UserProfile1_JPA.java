package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.UserProfile_pg1;

import java.util.Optional;

@Repository
public interface Repo_DAO_UserProfile1_JPA extends JpaRepository<UserProfile_pg1, Integer> {

    Optional<UserProfile_pg1> findByUsername(String username);
}
