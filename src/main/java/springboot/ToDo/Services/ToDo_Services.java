package springboot.ToDo.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_JPA;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
        // @Transactional(readOnly = false) --> this allows all method inside class to do writable transactions.
        // @Transactional(readOnly = true)  keeping this "true"", will not allow to add any transaction into dB
        // even though all method post successfully, this will lock it...
        // manage at each method separately or class level anything is fine.
public class ToDo_Services {

    Logger l1 = LoggerFactory.getLogger(Class.class);

    @Autowired
    private Repo_DAO_SpringData_JPA repo_dao_springData_jpa;


    public void insert_list_data_springDataJpa(List<Todo> list1) {
        list1.forEach(x -> repo_dao_springData_jpa.save(x));
    }


    @Transactional(propagation = Propagation.REQUIRED)  /// requires_new
    //    is telling Spring that this method needs to execute in its own transaction, independent of any other, already existing transaction
    //    Which basically means your code will open two (physical) connections/transactions to the database
    //    method needs a transaction, either open one for me or use an existing one → getConnection(). setAutocommit(false). commit().
    public void deleteByUID_springDataJPA(int uid) {
        repo_dao_springData_jpa.deleteByUid(uid);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Todo updateByUid(Todo todo3) {
        Todo existing_rec_retrieved = repo_dao_springData_jpa.findAll().stream().filter(x -> x.getUid() == todo3.getUid()).toList().get(0);

        existing_rec_retrieved.setId(todo3.getId());
        existing_rec_retrieved.setAttach(todo3.getAttach());
        existing_rec_retrieved.setTargetDate(todo3.getTargetDate());
        existing_rec_retrieved.setDescription(todo3.getDescription());
        existing_rec_retrieved.setCreationDate(todo3.getCreationDate());
        existing_rec_retrieved.setUsername(todo3.getUsername());
        existing_rec_retrieved.setDone(todo3.getDone());

        return existing_rec_retrieved;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<List<Todo>> getAllSumit() {
        return repo_dao_springData_jpa.findSumit();
    }

    public List<Todo> findbyALL() {
        return repo_dao_springData_jpa.findAll();
    }



    // WARNING : This is not ideal method, because when data is too heavy then you can not run all records and then select onw out of one, you need to target your records for faster and performance reason.
    //This method is NOT USED and NOT IDEAL and so Deprecated because performance is poor, this iterates all data instead of 1 UID target.
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    //this will not allow any WRITE transaction to be posted in db.
    public List<Todo> findByUid_nouse(int uid) {  // The findById method retrieves an entity by its unique identifier (id). It returns an Optional wrapper, indicating that the entity may or may not exist in the data store. If the entity is found, it will be wrapped inside the Optional. Otherwise, the Optional will be empty
        return repo_dao_springData_jpa.findAll().stream().filter(x -> x.getUid() == uid).toList();
    }


    //@Transactional(readOnly = true, propagation = Propagation.REQUIRED) //this will not allow any WRITE transaction to be posted in db.
//    public List<Todo> get_todo_LIST_by_id(int id) {
////      return repo_dao_springData_jpa.findById(id).orElseThrow(() -> new IllegalArgumentException("UId entered is NOT-VALID, check!D"));
//        List<Todo> todoList = repo_dao_springData_jpa.findAllById(id);
//        return todoList;
//    }


    // This method is based on Derived Query on JPA, comparing findByUid() vs findByUid1(), this method works best as it generates query automatically and no need to write query and JPA auto construct it.
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    //this will not allow any WRITE transaction to be posted in db.
    public List<Todo> findByUid(int uid) {
        return repo_dao_springData_jpa.findByUid(uid).orElseThrow(() -> new IllegalArgumentException("UId entered is NOT-VALID, check!D"));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<List<Todo>> findById(int id) {
        return repo_dao_springData_jpa.findById(id);
    }

}
