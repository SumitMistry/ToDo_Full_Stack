package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.UserAuth;

import java.util.Optional;

@Repository
public interface Repo_DAO_UserAuth_JPA extends JpaRepository<UserAuth, Integer> {

    Optional<UserAuth> findByUsername(String username);

}
