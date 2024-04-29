package springboot.ToDo.Repository.SpringDataJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.Todo_model_SprDataJPA;

@Repository
public interface Repo_DAO_SpringData_JPA extends JpaRepository<Todo_model_SprDataJPA, Integer> {

}
