package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.MultipartFile_holder;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_JPA;
import springboot.ToDo.Services.ToDo_Services;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/*
    ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
    (@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>
        this value can be returning as array int[] or String[], example in update() POST method...below
 */

@Controller
@RequestMapping("api/todo/")
@SessionAttributes({"uid_email", "pass", "totally"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will nto work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..
public class ToDo_Controller {

    Logger l1 = LoggerFactory.getLogger(Class.class);

    ///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
    private final ToDo_Services toDo_Services;

    private final Repo_DAO_SpringData_JPA repo_dao_springData_jpa;

    public ToDo_Controller(ToDo_Services toDo_Services, Repo_DAO_SpringData_JPA repo_dao_springData_jpa) {
        super();
        this.toDo_Services = toDo_Services;
        this.repo_dao_springData_jpa =repo_dao_springData_jpa;
    }

    static {
        // this to set initial static block, will initialize once only...
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listAll_todos(ModelMap modelMap) {
        //List<Todo> outputList = toDo_Services.listAllToDo();

        // List with all todos + parsing it to modelmap
        List<Todo> list1 = toDo_Services.findbyALL();
        modelMap.addAttribute("listMapVar", list1);

        // counting total todos and parsing on the navigation.jspf vvia {totally} variable
        modelMap.addAttribute("totally", list1.size());

        // now final result listing as view
        return "listall";
    }


    @RequestMapping(value = "listjson", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<List<Todo>> listAll_todos_json(ModelMap modelMap) {
        return new ResponseEntity<List<Todo>>(toDo_Services.findbyALL(),HttpStatus.OK);
    }


///////////////////////////     FindBYID     ///////////////////////////
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public String findByID_from_List(@RequestParam(value = "i") int id, ModelMap modelMap) {
        //        System.out.println(toDo_Services.findByID(id));
        //      List<Todo> list1 =  toDo_Services.findByID_from_List(id);
        modelMap.addAttribute("listMapVar", toDo_Services.findByID(id));
        return "listall";
    }

///////////////////////////     FindBYuser     ///////////////////////////
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String findBYusername(@RequestParam(value = "i")String enter_username, ModelMap modelMap ){

        // Predicate function practice
        Predicate<? super Todo> p1 = x-> x.getUsername().equalsIgnoreCase(enter_username);
        List<Todo> t1 =  repo_dao_springData_jpa.findAll().stream().filter(p1).toList();

        // or // List<Todo> t1 =  repo_dao_springData_jpa.findAll().stream().filter(x->x.getUsername().equals(enter_username)).toList();
        modelMap.addAttribute("listMapVar", t1);
        return "listall";
    }



    //////////////////// This end point ONLY used to ADD HARDCODED values into SQL
    /////////// CHange values to NOT readOnly..here...to make it work @Transactional(readOnly = true)
    @RequestMapping(value = {"hardcode1"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(readOnly = true) // , propagation = Propagation.)
    // I kept this hard coded data as READONLY so will not get injected to DB
    public String hard_code_data_sprData_jpa(ModelMap modelMap) {

        // ADD: locally add to LIST
        List<Todo> list1 = new ArrayList<>();
        list1.add(new Todo(1, "Sumit@gmail.com", "TableDesc-1", LocalDate.of(2021, 02, 8), LocalDate.of(2048, 10, 7), true, null));
        list1.add(new Todo(2, "MIstrSS@microso.com", "Vrajwilback", LocalDate.of(1987, 12, 22), LocalDate.of(2031, 01, 15), true,   null));
        list1.add(new Todo(3, "RuthBVraj@nakamo.com", "Faint2024", LocalDate.of(2004, 11, 22), LocalDate.of(2025, 11, 25), false,  null));

        l1.info("\n ----> CONTROLLER: ADDING FAKE hard-coded DATA...");

        // INSERTING to SQL
        toDo_Services.insert_list_data_springDataJpa(list1);

        // FETCH all from SQL
        List<Todo> existing = toDo_Services.findbyALL();

        // Map above list to display on frontend UI
        modelMap.addAttribute("listMapVar", existing);

        return "listall";
    }

    //////////////////// INSERT - SpringDataJPA SQL == insert3 (GET/POST)
    @RequestMapping(value = "/insert3", method = RequestMethod.GET)
    public String get_SprData_JPA_insert(ModelMap modelMap) {
        List<Todo> list1 = toDo_Services.findbyALL();

        Todo t1 = new Todo(list1.size() + 1, (String) modelMap.get("uid_email"), "Hardcoded-world", LocalDate.now(), LocalDate.now().plusYears(1), false, null);
        modelMap.put("todo_obj_spring_data_jpa2", t1);
        return "insert3_SprDataJPA"; // this returns (jsp file)=view without @RESPONSEBODY
    }

    @RequestMapping(value = "/insert3", method = RequestMethod.POST)
    public String post_SprData_JPA_insert(ModelMap modelMap,
                                          @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,
                                          BindingResult bindingResult, Errors err) {

        //use binding result to find error while validating / entering data
        if (err.hasErrors() || bindingResult.hasErrors()) {
//            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
//            return "redirect:insert3_SprDataJPA";
            return "insert3_SprDataJPA";
        }

        // post data to backend SQL
        repo_dao_springData_jpa.save(todo_obj_spring_data_jpa2);

        // get data view
        return "redirect:list"; // validation will not be displayed because we have 2 different mpodels, both displaying on same page=List
    }


    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String show_UpdateByID_page(ModelMap modelMap, @RequestParam(value = "i") int id) {

        // Retrieved current record=data
        List<Todo> td0 = toDo_Services.findByID(id); //toDo_Services.findByID_from_List(id);
        System.out.println("--->" + td0);
        //Todo td0 = current_data.get(0);


        // To inject our pre-fill the data from object to model to front_end
        // This model is dupming data into insert3_SprDataJPA
        modelMap.put("todo_obj_spring_data_jpa2", td0.get(0));

        // insertion done in above step now , reloading Listing page
        return "insert3_SprDataJPA";
    }

    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String post_UpdateByID_page(ModelMap modelMap,
                                       @RequestParam("i") int oldvalue_of_id_NO_use, // ---> this is old id values, BEFORE user's update
                                       @RequestParam("id") int iDtakenFromHtmlTag, // ---> this is new id values, AFTER user's update, taken from mapping HTML user's input
                                       @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,
                                       BindingResult bindingResult,
                                       Errors err
    ) {

        // this id logic is to fetch user entered "NEW value of" ID for our this Update function
        //System.out.println(oldvalue_of_id_NO_use); // ---> this is old  values
        System.out.println(iDtakenFromHtmlTag ); // this will be the user's new value

        // if validation error handle here, handle here:
        if (bindingResult.hasErrors() || err.hasErrors()) {
            System.out.println(" YOU HAVE ERRRRRRORS....in INPUT VALIDATIONs");
            l1.info(" YOU HAVE ERRRRRRORS....in INPUT VALIDATIONs");
            System.out.println(bindingResult.getAllErrors());
            System.out.println(err.getAllErrors());
            return "insert3_SprDataJPA";
        }

        // New record object
        Todo new_obj_to_Be_Added = new Todo(todo_obj_spring_data_jpa2.getId(),
                //iDtakenFromHtmlTag, //this is new id at 1st index
                todo_obj_spring_data_jpa2.getUsername(),
                todo_obj_spring_data_jpa2.getDescription(),
                todo_obj_spring_data_jpa2.getCreationDate(),
                todo_obj_spring_data_jpa2.getTargetDate(),
                todo_obj_spring_data_jpa2.getDone()
                , null);

        // if no validation error: update data here
        // delete current old record from index 0 of REQ PARAM value, so we can replace with new data
        toDo_Services.updateByID(oldvalue_of_id_NO_use, new_obj_to_Be_Added);

        return "redirect:list";
    }


    ///////////////////////////     DELETE     ///////////////////////////
    @RequestMapping(value = "delete")
    public String deleteByID(@RequestParam(value = "i") int id) {
        toDo_Services.deleteByID_springDataJPA(id);
        //toDo_Services.deleteByID(id); // this was old implementation for  deleting from local list.
        l1.info("DELETEDD::::::::" + id);
        //return "listall";
        return "redirect:list";
    }



    ///////////////////////////     UPLOAD     ///////////////////////////
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String get_attach_function(
            ModelMap modelMap,
            @RequestParam(value = "i") int todoId) {

        // Retrieved current record/data
        Todo existingTodo = repo_dao_springData_jpa.findById(todoId).orElseThrow(() -> new IllegalArgumentException("Invalid Todo ID"));

        // insertion of fetched above data's mapping is done in below step now,
        modelMap.addAttribute("todoId", todoId); // REF: upload.jsp //used for --> form's post action="upload/${todoId}"
        modelMap.addAttribute("todo55", existingTodo); // REF: upload.jsp // half page of this form is loaded with existing <todo's data>
        modelMap.addAttribute("fileUpload_holder", new MultipartFile_holder()); // REF: upload.jsp // half page of this form is loaded with <fileUpload_holder>

        return "upload";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String post_now_uploading_here(
            @ModelAttribute("multipartFile") MultipartFile_holder multipartFile,
            @RequestParam(value = "i") int todoId, // this result in [ old val, new val], we need new val by user so
            ModelMap modelmap
            ) {

        //finding existing Todo
        Todo existingTodo = repo_dao_springData_jpa.findById(todoId).orElseThrow(() -> new IllegalArgumentException("Invalid Todo ID"));
        try{
            if (  !  (multipartFile.getMultipartFile() == null && multipartFile.getMultipartFile().isEmpty())) {

                //  This works great for BLOB datatype. Large data object attach to db. We change model to byte[] or BLOB depending on the requirement
                byte[] b = multipartFile.getMultipartFile().getBytes();
                existingTodo.setattach(new javax.sql.rowset.serial.SerialBlob(b));
                repo_dao_springData_jpa.save(existingTodo);

                                    /*
                                    ##  This works great for small size files like byte[] datatype. We change model to byte[] or BLOB depending on the requirement
                                        existingTodo.setattach(multipartFile.getMultipartFile().getBytes());
                                        repo_dao_springData_jpa.save(existingTodo);
                                    */
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "redirect:list";
    }

}








/*
 if (todo55.getattach() != null && !todo.getAttach().isEmpty()) {
                // attach attachment
                existingTodo.setattach(todo55.getMultipartFile().getBytes());
                // save/commit object to dB via SpringJPA
                repo_dao_springData_jpa.save(existingTodo);
            }
 */

/*
try {
            if (todo.getAttach() != null && !todo.getAttach().isEmpty()) {
                byte[] attachedFileBytes = StreamUtils.copyToByteArray(todo.getAttach().getInputStream());
                existingTodo.setAttachedFile(attachedFileBytes);
                existingTodo.setAttachedFileName(todo.getAttach().getOriginalFilename());
                todoRepository.save(existingTodo);
            }
 */

/*

            // uploaded file ---> byte[]
            byte[] uploaded_file = f1.getBytes();

            // byte[] ---> attach into <t11> todo obj();
            existingTodo.setattach(uploaded_file);

            // <t11> todo obj() ---> to save/commit into dB
 */


    /*
     ModelMap modelMap,
                                         @RequestParam("id") int[] id2, // this result in [ old val, new val], we need new val by user so
                                         @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,
                                         BindingResult bindingResult,
                                         Errors err
     */

    /* backup UPLOAD GET WORKING...


    @RequestMapping(value = {"upload"}, method = RequestMethod.GET)
    public String get_attach_function(ModelMap modelMap, @RequestParam(value = "id") int id) {

        // Retrieved current record=data
        List<Todo> td0 = toDo_Services.findByID(id); //toDo_Services.findByID_from_List(id);


        // insertion done in above step now , reloading Listing page
        modelMap.addAttribute("todo55", td0.get(0));
        return "upload";
    }


    @RequestMapping(value = {"upload"}, method = RequestMethod.POST)
    @ResponseBody
    public Todo post_attach_function(
            @RequestParam("id") int[] id2, // this result in [ old val, new val], we need new val by user so
            @ModelAttribute("todo55") Todo todo55,
            @RequestParam("attach") MultipartFile f1
    ) throws IOException {
        return null;

    }
     */

    /*


            // Normalize file name
            String fileName = StringUtils.cleanPath(f1.getOriginalFilename());

                Todo tg1 =   new Todo(todo55.getId(), todo55.getUsername(), todo55.getDescription(), todo55.getCreationDate(), todo55.getTargetDate()
                        , todo55.getDone(), f1.getBytes());

                return repo_dao_springData_jpa.save(tg1);


            ////
            String fileDoenloadURi = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(dbFile.getId())
                    .toUriString();

        }
     */




/////////////////////////////     INSERT 2  MANUAL     ///////////////////////////////////////////////////////////////////
//    @RequestMapping(value = "/insert2", method = RequestMethod.GET)
//    public String get_insert_todo2() {
//        return "insert2";
//    }
//
//    @RequestMapping(value = "/insert2", method = RequestMethod.POST)
//    public String post_insert_todo(@RequestParam String id, @NonNull @RequestParam String username, @NonNull @RequestParam String description, @RequestParam LocalDate creationDate, @RequestParam LocalDate targetDate, @RequestParam boolean done, ModelMap modelMap) {
//
//        //handling 0 or -1 values
//        if (!id.isEmpty()) {
//            if (Integer.valueOf(id) <= 0) {
//                modelMap.addAttribute("id_err_msg1", "id must be 1 or positive");
//                modelMap.addAttribute("id_err_msg2", id);
//                return "insert2";
//            }
//        }
//        l1.info("--------------------.> now adding the forntend data ---> backend calling services toDo_Services.insert_todo ");
//        toDo_Services.insert_todo(id, username, description, creationDate, targetDate, done);
//        return // after insert is done, show all todos
//                "redirect:list"; // this redirect to listall.jsp VIEW using /list
//        //redierct: {Always put url endpoint name, NOT JSP}
//    }
//
//
/////////////////////////////     INSERT   Automatic     ///////////////////////////////////////////////////////////////////
//    @RequestMapping(value = "/insert", method = RequestMethod.GET)
//    public String get_insert_todo(ModelMap modelMap) {
//
//        // making data ready to pre-fill in todo_object
//        String u_retrived = (String) modelMap.get("uid_email");
//        Todo Todo_model_SprDataJPA_obj = new Todo(toDo_Services.listAllToDo().size() + 1, u_retrived, "HelloWorld", LocalDate.now(), LocalDate.now().plusYears(1), false);
//
//        // this injects our pre-fill the data from object to model to front_end
//        modelMap.put("todooo", Todo_model_SprDataJPA_obj);
//
//        return "insert"; // return  VIEW OUTPUT = insert.jsp // no @ResponseBody
//    }
//
//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public String post_insert_data(ModelMap modelMap,
//                                   @Valid @ModelAttribute("todooo") Todo todooo,   //   @Valid can be used to validate beans "BEANS" ONLY
//                                   BindingResult bindingResult) {
//
//        // binding validation errors if found in form, user will be redirected back to the same page.
//        // Errors will be hidden bcos of this...
//        if (bindingResult.hasErrors()) {
//            int x = bindingResult.getErrorCount();
//            System.out.println(x);
//            l1.info(" error  count = " + x);
//            return "insert";    //  --> "redirect:insert" returns   /insert   endpoint
//            // return "insert";   --> return "insert"    returns    insert.jsp
//        }
//
//        // retrieving username(=u_retrived) and hard coding username(u_retrived) intentionally into object while inserting as below.
//        // so basically username is NOT TWO way binding.
//        // except user, everything else will be 2 way binding...
//        String u_retrived = (String) modelMap.get("uid_email");
//
//        // Validation done under if(), now insert this data
//        toDo_Services.insert_todo(String.valueOf(todooo.getId()), u_retrived, todooo.getDescription(), todooo.getCreationDate(), todooo.getTargetDate(), todooo.getDone());
//        return "redirect:list"; //    For such redirects you put ENDPOINT which is "/list"
//    }




