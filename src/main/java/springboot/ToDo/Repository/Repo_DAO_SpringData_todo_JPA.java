package springboot.ToDo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springboot.ToDo.Model.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Repo_DAO_SpringData_todo_JPA extends JpaRepository<Todo  //However, if you encounter any specific issues or limitations, you might need to consider using separate entity classes or configurations for each database.
        , Integer> {


    // "DERIVED QUERY" --------------JPA automatically generate query based on "findByUID" spelling
    // Derived query method to find a Todo by UID
    Optional<List<Todo>> findByUid(int uid);

    // "CUSTOM QUERY"
    // Method to find record by ID
    //override the query to explicitly specify the id field using the @Query annotation
    @Query(value = "SELECT * FROM todoh WHERE id = :id ;", nativeQuery = true) // The @Param("id") annotation ensures that the id method parameter is correctly mapped to the :id placeholder in the query
    Optional<List<Todo>> findById(@Param("id") int id);


    void deleteByUid(int uid);



    // HEre if i remove @modifying and @Transactional , then error : org.springframework.orm.jpa.JpaSystemException: JDBC exception executing SQL [DELETE FROM todoh WHERE id = ? ;] [Statement.executeQuery() cannot issue statements that do not produce result sets.]
    // This error comes becasue JPA need modification permission, we creating custoom query so we need to plug this into it
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM todoh WHERE id = :id ;", nativeQuery = true) // The @Param("id") annotation ensures that the id method parameter is correctly mapped to the :id placeholder in the query
    void deleteById(Integer id);

    Optional<List<Todo>> findByUsername(String username);



    // "CUSTOM QUERY" to retrieve the file name by ID
    // This is called "Custom QUERY" method--------------we give custom query to JPA on our own this will return our custom logic;
    // This will return ANY record LIST multiple or Single where the "sumit" keyword is mentioned.
    // If the raw query I have entered, then keep  nativeQuery = true
    @Query(value = "SELECT * FROM todoh WHERE description LIKE '%sumit%' OR username LIKE '%sumit%';" , nativeQuery = true)
    Optional<List<Todo>> findSumit();

    // "CUSTOM QUERY"
//    @Query(value = "SELECT * FROM todoh WHERE description LIKE '%:keyword%' OR username LIKE '%:keyword%' ;" , nativeQuery = true)
    @Query(value = "SELECT * FROM todoh WHERE description LIKE %:keyword% OR username LIKE %:keyword%", nativeQuery = true)
    Optional<List<Todo>> findByKeyword(@Param("keyword") String keyword);



    // Custom Delete Query
    @Modifying
    @Transactional
    @Query(value = "delete from sumit.todoh where id BETWEEN 200 AND 888;", nativeQuery = true)
    int deleteRecords();

    @Modifying
    @Transactional // in below "Todo" is entity name , not table name. in Non-native query, it is based on Entity= TODO model name
    @Query(value = "SELECT t FROM Todo t WHERE t.creationDate BETWEEN :startDate AND :endDate")
    List<Todo> findCreationDateRange(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    boolean existsByUid(Integer uid);

    @Query("SELECT t FROM Todo t WHERE t.id IN :ids")
    List<Todo> findByCustomId(@Param("ids") List<Integer> ids);

//    @Query(value = "SELECT t FROM todo t WHERE t.id IN :idsHere")
//    List<Todo> findAllByIdssss(@Param(value = "idsHere") List<Integer> listOfIds);

}

