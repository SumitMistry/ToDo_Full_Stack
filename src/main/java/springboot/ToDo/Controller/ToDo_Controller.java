package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;



/*
    BOTH -----> Adds the key-value pair to the ModelMap:

    modelMap.put(key, value)
        >1 Signature ONLY ===>
        >Requires a key to be explicitly specified.

    modelMap.addAttribute(key, value)  ******** BEST***************
        >2 Signature ====> modelMap.addAttribute(user) OR  modelMap.addAttribute("user", user);
        >Used for Null values handling.
        >Can derive the key automatically if omitted

 */
/*
    ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
    (@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>
        this value can be returning as array int[] or String[], example in update() POST method...below
 */

@Controller
@RequestMapping("api/todo/")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SessionAttributes({"uid_email", "pass", "totally"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will not work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..
public class ToDo_Controller<T> {

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

    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String listAll_todos(ModelMap modelMap) {
        //List<Todo> outputList = toDo_Services.listAllToDo();

        // List with all todos + parsing it to modelmap

        List<Todo> list1 = toDo_Services.findbyALL();
        modelMap.addAttribute("listMapVar", list1);

        // counting total todos and parsing on the navigation.jspf vvia {totally} variable, this is for HEADER count variable
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


    ///////////////////////////     FindBYUID     ///////////////////////////
    @RequestMapping(value = "findByUID", method = RequestMethod.GET)
    public String findByUID_from_List(@RequestParam(value = "u") int uid, ModelMap modelMap) {
        //      System.out.println(toDo_Services.findByID(uid));
        //      List<Todo> list1 =  toDo_Services.findByID_from_List(uid);

        List<Todo> todo_uid = toDo_Services.findByUid(uid);
        modelMap.addAttribute("listMapVar", todo_uid );
        return "listall";
    }

    ///////////////////////////     FindBY ID     ///////////////////////////
    @RequestMapping(value = "findById", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "u") int id,
                           ModelMap modelMap) {

        System.out.println("-->>" + repo_dao_springData_jpa.findById(58).get().toString());
        System.out.println("----->>>>>>----------" +  id);

        Optional<List<Todo>> todo_list_optional = repo_dao_springData_jpa.findById(id);
        List<Todo> todo_list= repo_dao_springData_jpa.findById(id).get();

        System.out.println( "->>>>>>>"+ todo_list.toString());
        modelMap.addAttribute("listMapVar",todo_list);

        return "listall"; // JSP page name (without prefix/suffix)

    }



    // New Derived Query bases JPA fucntion //////////////
    // findByUsername() is ++ Faster than  findBYusername1() no steram ALL in here.
    @RequestMapping(value = "findByUser", method = RequestMethod.GET)
    public String findByUsername(@RequestParam(value = "user")String enter_username,
                                 ModelMap modelMap ){


        List<Todo> t1 =  repo_dao_springData_jpa.findByUsername(enter_username).orElseThrow(() -> new IllegalArgumentException("Wrong username, retry..."));

        modelMap.addAttribute("listMapVar", t1);
        return "listall";
    }

    //  OLD -STEAMING ALL List = POOR Performance ////////////////////////////
    // Not in use, taken off from JSP - front end (listall.jsp) side
    // /////////////////////////     findByUser     ///////////////////////////
    @RequestMapping(value = "findByUser1", method = RequestMethod.GET)
    public String findBYusername1(@RequestParam(value = "u")String enter_username, ModelMap modelMap ){

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
        list1.add(new Todo(001,  "VJ@Karma.com", "From Cntrl-hardc end", LocalDate.now(), LocalDate.now().plusYears(1), false,  null));

        l1.info("\n ----> CONTROLLER: ADDING FAKE hard-coded DATA...");

        // INSERTING to SQL
        toDo_Services.insert_list_data_springDataJpa(list1);


        // ADD: locally add to LIST
        List<Todo> list2 = new ArrayList<>();
        list2.add(new Todo(002, "MIstrSS@SuMIT.com", "Vraj will be back", LocalDate.of(1987, 12, 22), LocalDate.of(2031, 01, 15), true,   null));
        // INSERTING to SQL
        toDo_Services.insert_list_data_springDataJpa(list2);

        // ADD: locally add to LIST
        List<Todo> list3 = new ArrayList<>();
        list3.add(new Todo(003, "RuthBVraj@nakamo.com", "Faint of 2021-2024", LocalDate.of(2004, 11, 22), LocalDate.of(2025, 11, 25), false,  null));
        // INSERTING to SQL
        toDo_Services.insert_list_data_springDataJpa(list3);


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
    public String show_UpdateByID_page(ModelMap modelMap, @RequestParam(value = "u") int uid) {


        // Retrieved current record=data
        Todo retrieve_current_rec = toDo_Services.findByUid(uid).get(0); //toDo_Services.findByID_from_List(id);
        System.out.println("--->" + retrieve_current_rec);
        //Todo retrieve_current_rec = current_data.get(0);


        // To inject our pre-fill the data from object to model to front_end
        // This model is dupming data into insert3_SprDataJPA
        modelMap.addAttribute("todo_obj_spring_data_jpa2", retrieve_current_rec);

        // insertion done in above step now , reloading Listing page
        return "insert3_SprDataJPA";
    }

    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    //This updated method will NOT create new UID upon modifying/updating existing record.
    @RequestMapping(value = "update", method = {RequestMethod.PUT, RequestMethod.POST})
    public String post_UpdateByUID_page(ModelMap modelMap,
                                       @RequestParam("u") int uidTakenFromHtmlTag, // ---> this is uid values, taken from mapping HTML user's input
                                       @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,  // this brings data from HTML VIEW FORM --->
                                       BindingResult bindingResult,
                                       Errors err
    ) {
        // this is fetching existing UID of the record, first we need UID
        todo_obj_spring_data_jpa2.setUid(uidTakenFromHtmlTag);

        System.out.println(todo_obj_spring_data_jpa2.toString());


        if (err.hasErrors() || bindingResult.hasErrors()) {
//            int x = bindingResult.getErrorCount();
//            System.out.println(x);
//            l1.info(" error  count = " + x);
//            return "insert";    //  --> "redirect:insert" returns   /insert   endpoint
//            // return "insert";   --> return "insert"    returns    insert.jsp
            return "insert3_SprDataJPA";
             }

        // this "todo_obj_spring_data_jpa2" data is coming from FRONT-END and we simply pass it to save.
        toDo_Services.updateByUid(todo_obj_spring_data_jpa2);   // this has existing UID inside.

        return "redirect:list";
    }



    ///////////////////////////     DELETE BY UID    ///////////////////////////
    @RequestMapping(value = "deleteByUid", method = RequestMethod.GET)
    public String deleteByUID(@RequestParam(value = "u") int uid) {
        toDo_Services.deleteByUID_springDataJPA(uid);
        //toDo_Services.deleteByID(id); // this was old implementation for  deleting from local list.
        l1.info("DELETEDD::::::::" + uid);
        //return "listall";
        return "redirect:list";
    }

    ///////////////////////////     DELETE BY ID    ///////////////////////////
    @RequestMapping(value = "delByID", method = RequestMethod.GET)
    public String del_By_ID(@RequestParam(value = "u") int id) {
        repo_dao_springData_jpa.deleteById(id);
        l1.info("DELETEDD::::::::" + id);
        //return "listall";
        return "redirect:list";
    }

    /////////////       CUSTOM QUERY supported by JpaRepository  //////////////////////
    @RequestMapping(value = "sam", method = RequestMethod.GET)
    public String getSum(ModelMap modelMap){
        modelMap.addAttribute("listMapVar", toDo_Services.getAllSumit());
        return "listall";
    }


    @RequestMapping(value = "searchAPI", method = RequestMethod.GET)
    public String findByKeyword(@RequestParam(name = "searchKey") String keyword,
                                ModelMap modelMap){
        List<Todo> todoList = toDo_Services.findByKeyword(keyword);
        modelMap.addAttribute("listMapVar",todoList);
        return "listall";
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




