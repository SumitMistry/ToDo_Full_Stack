package springboot.ToDo.Services;

public class Deleted_from_ToDo_Services {
    /*


/*

//        List<Todo> get_curr_record = repo_dao_springData_jpa.findAll().stream().filter(x-> x.getUid() == uid).findFirst();
//
//        .setId(new_updated_todo.getId());
//        new_updated_todo.setId(get_curr_record.get().setId(););
//        get_curr_record.get().setUsername(new_updated_todo.getUsername());
//        get_curr_record.get().setDone(new_updated_todo.getDone());
//        get_curr_record.get().setCreationDate(new_updated_todo.getCreationDate());
//        get_curr_record.get().setDescription(new_updated_todo.getDescription());
//        get_curr_record.get().setTargetDate(new_updated_todo.getTargetDate());
//        get_curr_record.get().setattach(new_updated_todo.getattach());
//
//        repo_dao_springData_jpa.save(new_updated_todo);





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



    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Todo updateByUid(int uid, int id, String username, String description, LocalDate creationDate, LocalDate targetDate, boolean done, Blob attach) {
        Todo existing_rec_retrieved = repo_dao_springData_jpa.findAll().stream().filter(x -> x.getUid() == uid).toList().get(0);

        existing_rec_retrieved.setId(id);
        existing_rec_retrieved.setattach(attach);
        existing_rec_retrieved.setTargetDate(targetDate);
        existing_rec_retrieved.setDescription(description);
        existing_rec_retrieved.setCreationDate(creationDate);
        existing_rec_retrieved.setUsername(username);
        existing_rec_retrieved.setDone(done);

        return existing_rec_retrieved;
    }


 */



}
