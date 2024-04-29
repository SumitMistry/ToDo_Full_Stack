package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.ToDo.Model.Todo;

public interface Repo_DAO_SpringData_JPA extends JpaRepository<Todo, Integer> {

}
