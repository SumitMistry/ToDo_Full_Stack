package springboot.ToDo.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import springboot.ToDo.Model.Todo_original;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ToDo_Services {

    Logger l1 = LoggerFactory.getLogger(Class.class);
    private static List<Todo_original> listToDo = new ArrayList<Todo_original>();
    private static int counter =0;


    static{  //this is data creation here
        listToDo.add(new Todo_original(++counter,"Sumit","1st Todo list description", LocalDate.now(),  LocalDate.of(2024,2,22),false));
        listToDo.add(new Todo_original(++counter,"Mistry","2nd Todo list description", LocalDate.now(), LocalDate.of(2024,8,30) ,false));
        listToDo.add(new Todo_original(++counter,"Smith","3rd Todo list description", LocalDate.now(),  LocalDate.of(2024,7,2),false));
    }

    public List<Todo_original> listAllToDo(){
        l1.info(listToDo.toString());
        return listToDo; // this will be going to be used as variable  in todo_welcome.jsp as ${lsitTodo}
    }

    public List<Todo_original> insert_todo(String id,
                                           String username,
                                           String description,
                                           LocalDate creationDate,
                                           LocalDate targetDate,
                                           boolean done) {

        //int i1 = id.isEmpty() ? listToDo.size()+1 : Integer.valueOf(id);
        boolean addedorNot = listToDo.add(new Todo_original(  Integer.parseInt(id) , username,description,creationDate,targetDate, done));
        l1.info("insertingg(insert_todo).... given data T/F ==" + addedorNot);
        return  listToDo;
    }




    public void deleteByID(int id ){
        // PREDICATE functional programming
        Predicate<? super Todo_original> predicate = todoOriginal2 -> todoOriginal2.getId() == id;

        System.out.println("-------------------------------------------------deleted:::" + id); l1.info("DELETEDD::::::::" + id );

        listToDo.removeIf(predicate);
        // this predicate runs on every list item, and if the condition(iterated getId==user input id) matches then it will apply(remove) list.remove to that record...
    }


    public List<Todo_original> findByID_from_List(int id){
        Predicate <? super Todo_original>  predicate = tod2 -> tod2.getId() == id;
        List<Todo_original> list =  listToDo.stream().filter(predicate).toList();
        // or List<Todo> list =  listToDo.stream().filter(predicate).toList();
        // or Todo t1=  listToDo.stream().filter(predicate).findFirst().get();
        return list;
    }






}
