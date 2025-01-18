package springboot.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.ToDo.Model.Todo;

import java.util.List;
import java.util.Optional;

public interface Repo_DAO_SpringData_JPA extends JpaRepository<Todo  //However, if you encounter any specific issues or limitations, you might need to consider using separate entity classes or configurations for each database.
        , Integer> {

    void deleteByUid(int uid);
    void deleteById(int id);

    // This is called "DERIVED QUERY" method--------------JPA automatically generate query based on "findByUID" spelling
    // Derived query method to find a Todo by UID
    Optional<List<Todo>> findByUid(int uid);
    Optional<Todo> findById(int id);

    Optional<List<Todo>> findByUsername(String username);

    // Custom query to retrieve the file name by ID
    // This is called "Custom QUERY" method--------------we give custom query to JPA on our own this will return our custom logic;
    // This will return ANY record LIST multiple or Single where the "sumit" keyword is mentioned.
    // If the raw query I have entered, then keep  nativeQuery = true
    @Query(value = "SELECT * FROM todoh WHERE description LIKE '%sumit%' OR username LIKE '%sumit%';" , nativeQuery = true)
    Optional<List<Todo>> findSumit();

    @Query(value = "SELECT * FROM todoh WHERE description LIKE '%:keyword%' OR username LIKE '%:keyword%';" , nativeQuery = true)
    Optional<List<Todo>> findByKeyword(@Param("keyword") String keyword);

}

