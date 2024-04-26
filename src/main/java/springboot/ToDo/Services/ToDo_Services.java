package springboot.ToDo.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.ToDo.Model.Todo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ToDo_Services {

    Logger l1 = LoggerFactory.getLogger(Class.class);
    private static List<Todo> listToDo = new ArrayList<Todo>();
    private static int counter =0;


    static{  //this is data creation here
        listToDo.add(new Todo(++counter,"Sumit","1st Todo list description", LocalDate.now(),  LocalDate.of(2024,2,22),false));
        listToDo.add(new Todo(++counter,"Mistry","2nd Todo list description", LocalDate.now(), LocalDate.of(2024,8,30) ,false));
        listToDo.add(new Todo(++counter,"Smith","3rd Todo list description", LocalDate.now(),  LocalDate.of(2024,7,2),false));
    }

    public List<Todo> listAllToDo(){
        l1.info(listToDo.toString());
        return listToDo; // this will be going to be used as variable  in todo_welcome.jsp as ${lsitTodo}
    }

    public List<Todo> insert_todo(String id,
                                  String username,
                                  String description,
                                  LocalDate creationDate,
                                  LocalDate targetDate,
                                  boolean done) {

        int i1 = id.isEmpty() ? listToDo.size()+1 : Integer.valueOf(id);
        boolean addedorNot = listToDo.add(new Todo(  i1 , username,description,creationDate,targetDate, done));
        l1.info("insertingg(insert_todo).... given data T/F ==" + addedorNot);
        return  listToDo;
    }

}
