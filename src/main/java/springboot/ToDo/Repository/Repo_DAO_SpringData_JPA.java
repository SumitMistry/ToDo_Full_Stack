package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.Todo;

public interface Repo_DAO_SpringData_JPA extends JpaRepository<Todo  //However, if you encounter any specific issues or limitations, you might need to consider using separate entity classes or configurations for each database.
        , Integer> {


}

