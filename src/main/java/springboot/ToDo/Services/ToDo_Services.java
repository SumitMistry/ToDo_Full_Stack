package springboot.ToDo.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_JPA;

import java.util.List;

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


    public void insert_list_data_springDataJpa(List<Todo> list1){
        list1.forEach(x->repo_dao_springData_jpa.save(x));
    }
    @Transactional(propagation = Propagation.REQUIRED)  /// requires_new
    //    is telling Spring that this method needs to execute in its own transaction, independent of any other, already existing transaction
    //    Which basically means your code will open two (physical) connections/transactions to the database
    //    method needs a transaction, either open one for me or use an existing one → getConnection(). setAutocommit(false). commit().
    public void deleteByID_springDataJPA(int id){
        repo_dao_springData_jpa.deleteById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED) //this will not allow any WRITE transaction to be posted in db.
    public List<Todo> findByID(int id){  // The findById method retrieves an entity by its unique identifier (id). It returns an Optional wrapper, indicating that the entity may or may not exist in the data store. If the entity is found, it will be wrapped inside the Optional. Otherwise, the Optional will be empty
        return repo_dao_springData_jpa.findAll().stream().filter(x-> x.getId() == id).toList();
    }


    public List<Todo> findbyALL(){
        return repo_dao_springData_jpa.findAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateByID(int idTodelete, Todo todo){
        repo_dao_springData_jpa.deleteById(idTodelete);
        repo_dao_springData_jpa.save(todo);
    }








//    private static List<Todo> listToDo = new ArrayList<Todo>();
//    private static int counter =0;
//
////    static{  //this is data creation here
////        listToDo.add(new Todo(++counter,"Sumit","1st Todo list description", LocalDate.now(),  LocalDate.of(2024,2,22),false));
//        listToDo.add(new Todo(++counter,"Mistry","2nd Todo list description", LocalDate.now(), LocalDate.of(2024,8,30) ,false));
//        listToDo.add(new Todo(++counter,"Smith","3rd Todo list description", LocalDate.now(),  LocalDate.of(2024,7,2),false));
//    }

//    public List<Todo> listAllToDo(){
//        l1.info(listToDo.toString());
//        return listToDo; // this will be going to be used as variable  in todo_welcome.jsp as ${lsitTodo}
//    }

//    public List<Todo> insert_todo(String id,
//                                           String username,
//                                           String description,
//                                           LocalDate creationDate,
//                                           LocalDate targetDate,
//                                           boolean done) {
//
//        //int i1 = id.isEmpty() ? listToDo.size()+1 : Integer.valueOf(id);
//        boolean addedorNot = listToDo.add(new Todo(  Integer.parseInt(id) , username,description,creationDate,targetDate, done));
//        l1.info("insertingg(insert_todo).... given data T/F ==" + addedorNot);
//        return  listToDo;
//    }
//
//    public void deleteByID(int id ){
//        // PREDICATE functional programming
//        Predicate<? super Todo> predicate = todoOriginal2 -> todoOriginal2.getId() == id;
//        listToDo.removeIf(predicate);
//        // this predicate runs on every list item, and if the condition(iterated getId==user input id) matches then it will apply(remove) list.remove to that record...
//    }

//
//    public List<Todo> findByID_from_List(int id){
//        Predicate <? super Todo>  predicate = tod2 -> tod2.getId() == id;
//        List<Todo> list =  listToDo.stream().filter(predicate).toList();
//        // or List<Todo> list =  listToDo.stream().filter(predicate).toList();
//        // or Todo t1=  listToDo.stream().filter(predicate).findFirst().get();
//        return list;
//    }






}
