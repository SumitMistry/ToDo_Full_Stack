package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.UserProfile_pg0;

import java.util.Optional;

@Repository
public interface Repo_DAO_UserProfile0_JPA extends JpaRepository<UserProfile_pg0, Integer> {

    Optional<UserProfile_pg0> findByUsername(String username);

}
