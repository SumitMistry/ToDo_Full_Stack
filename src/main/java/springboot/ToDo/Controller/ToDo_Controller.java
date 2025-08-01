package springboot.ToDo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springboot.ToDo.Model.MultipartFile_holder;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.ToDo_Services;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
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
@RequestMapping(value = "/api/todo")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SessionAttributes({"uid_email", "pass", "totally", "listMapVar"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will not work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..
@Tag(name = "Controller# 1 = Todo Management", description = "SM: Operations related to Todo items") // Now, APIs will be grouped under "Todo Management" in Swagger UI. ✅ // used to group related API endpoints in the Swagger UI. It helps organize APIs by functionality, making them easier to understand and navigate.
public class ToDo_Controller<T> {

    private static final Logger l1 = LoggerFactory.getLogger(Class.class);

    ///////// doing this so I dont need to use @Autowire annotation, this is constructor based injection, we dont need autowire here
    private final ToDo_Services toDo_Services;
    private final Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa;
    private final Login_Services loginServices;



    public ToDo_Controller(ToDo_Services toDo_Services, Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa, Login_Services loginServices) {
        super();
        this.toDo_Services = toDo_Services;
        this.repo_dao_springData_todo_jpa = repo_dao_springData_todo_jpa;
        this.loginServices = loginServices;
    }

    static {
        // this to set initial static block, will initialize once only...
    }



    /**
     * 1️⃣ Fetch todos within a date range (Returns JSP page)
     */
    @RequestMapping(value = {"/dateRangePicker"}, method = {RequestMethod.GET , RequestMethod.POST})
    @Operation(summary = "GET Todo by dateRange", description = "Range of date from and to picker for searching todo, FORMAT = YYYY-MM-DD"  )
    public String dateRangeFinder(@RequestParam(name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                  @RequestParam(name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                  ModelMap modelMap){

        List<Todo> list_todos = repo_dao_springData_todo_jpa.findCreationDateRange(fromDate,toDate);

        if (! list_todos.isEmpty()) {
            modelMap.addAttribute("listMapVar", list_todos);
            modelMap.addAttribute("totally", list_todos.size());
        } else {
            modelMap.addAttribute("listMapVar", new ArrayList<>());  // Empty list if no results
            modelMap.addAttribute("totally", 0);
        }
        return "index";  // Returning JSP page
    }



    /**
     * 2️⃣ Search todos by keyword (Returns JSP page)
     */
    @RequestMapping(value = {"/searchAPI"}, method = {RequestMethod.GET , RequestMethod.POST})
    @Operation(summary = "GET searchAPI", description = "Returns Keyword Search result.")
    public String findByKeyword(@RequestParam(name = "searchKey") String keyword,
                                ModelMap modelMap){

        List<Todo> todoList = toDo_Services.findByKeyword(keyword);

        System.out.println(todoList.size());

        if (! todoList.isEmpty()) {
            modelMap.addAttribute("listMapVar", todoList);
            modelMap.addAttribute("totally", todoList.size());
        } else {
            modelMap.addAttribute("listMapVar", new ArrayList<>());  // Empty list if no results
            modelMap.addAttribute("totally",0);
        }
        return "index";   // Returning JSP page
    }


    ///////////////////////////     USER based LISTING     ///////////////////////////
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    @Operation(
            summary = "GET user based listing" ,
            description = "Returns JSP page with current user's all todo" ,
            parameters = {
                    @Parameter(
                            name = "username",
                            description = "Username of the currently logged-in user (automatically fetched from Spring Security)",
                            required = true,
                            hidden = false // Hides this parameter from Swagger UI
                    )},
            responses = {
                    @ApiResponse(responseCode = "200", description = "200=No Todo lin list to show you."),
                    @ApiResponse(responseCode = "404", description = "404=Todo listing is here.")            }
    )
    public String list_per_user_todos(ModelMap modelMap) {

        // List with all todos + parsing it to modelmap
        Optional<List<Todo>> user_listing = repo_dao_springData_todo_jpa.findByUsername(loginServices.get_username_from_login_from_spring_Security());
        modelMap.addAttribute("listMapVar", user_listing.get());

        // counting total todos and parsing on the navigation.jspf via {totally} variable, this is for HEADER count variable
        int count_todos = user_listing.get().size();
        modelMap.addAttribute("totally", count_todos );

//        // This code helps in - Improve Exception Handling
//        if (user_listing.isEmpty() || user_listing.get().isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found for the uxser");
//        }

        // now final result listing as view
        return "index"; // JSP page
    }

    ///////////////////////////     ALL USER LISTING     ///////////////////////////
    @RequestMapping(value = {"/listall"}, method = {RequestMethod.GET, RequestMethod.POST})
    @Operation(summary = "GET Listing of all User's Todo ", description = "select * = List of all todo in db"
//                parameters = { @Parameter
//                                ( name = "LIST ALL TODO" ,
//                                description = "GET Listing of all User's Todo",
//                                        required = false,
//                                        hidden = false)        }
    )
    public String listAll_todos(ModelMap modelMap) {
        //List<Todo> outputList = toDo_Services.listAllToDo();

        // List with all todos + parsing it to modelmap
        List<Todo> list1 = toDo_Services.findbyALL();
        modelMap.addAttribute("listMapVar", list1);

        // counting total todos and parsing on the navigation.jspf via {totally} variable, this is for HEADER count variable
        int count_todos = list1.size();
        modelMap.addAttribute("totally", count_todos );

        // now final result listing as view
        return "index";
    }

    /**
     * 3️⃣ GET - Show Insert Todo Page (JSP Page)
     */
    //////////////////// INSERT - SpringDataJPA SQL == insert3 (GET/POST)
    @RequestMapping(value = { "/insert3" }, method = RequestMethod.GET)
    public String getInsertPage(ModelMap modelMap) {
        // 🔹THIS GET Method - Returns a pre-filled Todo object in frontend page using ModelMapping...

        // this is used just to get count=size of list
        int list_size = toDo_Services.findbyALL().size();
        //pre-filling add tags in forms automatically populated for users as easy to use.
        Todo t1 = new Todo(list_size + 1, (String) modelMap.get("uid_email"), "Hardcoded / Default Task", LocalDate.now(), LocalDate.now().plusDays(7), false, null);

        modelMap.addAttribute("todo_obj_spring_data_jpa2", t1);

        return "insert3_SprDataJPA"; // this returns (jsp file)=view without @RESPONSEBODY
    }


    /**
     * 4️⃣ POST - Insert a new Todo (Process Form Submission)
     */
    @RequestMapping(value = { "/insert3" }, method = RequestMethod.POST)
    @Operation(summary = "POST insert3 (includes Validation)", description = "Insert validated todo by Json.")
    public String post_SprData_JPA_insert(ModelMap modelMap,
                                          @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo,
                                          BindingResult bindingResult, Errors err) {

        //use binding result to find error while validating / entering data
        if (err.hasErrors() || bindingResult.hasErrors()) {
            //            l1.info(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
            //            System.out.println(" -------> YOu have BindingResult err: count = " + bindingResult.getErrorCount());
            //            return "redirect:insert3_SprDataJPA";
            return "insert3_SprDataJPA"; // Return form page with validation errors
        }

        // post data to backend SQL
        repo_dao_springData_todo_jpa.save(todo);

        // get data view
        // Redirect to todo list page
        // return "redirect:/api/todo/list";    // validation will not be displayed because we have 2 different mpodels, both displaying on same page=List
        return "redirect:/api/todo/listall";
    }






    ///////////////////////////     FindBYUID     ///////////////////////////
    @RequestMapping(value = "/findByUID", method = {RequestMethod.GET , RequestMethod.POST})
    @Operation(summary = "GET todo by UID", description = "Use UID to retrieve Todo")
    public String findByUID_from_List(@RequestParam(value = "u") int uid, ModelMap modelMap) {
        //      System.out.println(toDo_Services.findByID(uid));

        if (repo_dao_springData_todo_jpa.existsByUid(uid)){
            modelMap.addAttribute("message"," ✅ Todo uid -> " + uid + "foundd!");
            List<Todo> todo_uid = toDo_Services.findByUid(uid);
            modelMap.addAttribute("listMapVar", todo_uid );
            modelMap.addAttribute("totally", todo_uid.size());
        }
        else{
            modelMap.addAttribute("error"," ❌ Todo uid -> " + uid + " not foundd!");
        }

        return "index";
    }



    ///////////////////////////     FindBY ID     ///////////////////////////
    @RequestMapping(value = "/findById", method = {RequestMethod.GET , RequestMethod.POST})
    @Operation(summary = "GET todo by ID", description = "Use ID to retrieve Todo")
    public String findById(@RequestParam(value = "u") int id,
                           ModelMap modelMap) {

        if (repo_dao_springData_todo_jpa.existsByCustomId(id)){
            modelMap.addAttribute("message"," ✅ Todo ID -> " + id + "foundd!");
            List<Todo> todo_id = toDo_Services.findByCustomId(id).get();
            modelMap.addAttribute("listMapVar", todo_id );
            modelMap.addAttribute("totally", todo_id.size());
        } else{
            modelMap.addAttribute("error"," ❌ Todo ID -> " + id + " not foundd!");
        }

        //
        //        // Optional<List<Todo>> todo_list_optional = repo_dao_springData_todo_jpa.findById(id);
        //        Optional<List<Todo>> todo_list = repo_dao_springData_todo_jpa.findById_custom(id);
        //
        //        if (todo_list.isEmpty() || todo_list.get().isEmpty()) {
        //
        //            // OPTION # 1 (White Label page with exception specific message there
        //            // This will return WHITE-LABEL PAGE with below Exception reason
        //            // app.properties ---> ++ ---> server.error.include-message=always
        //            // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo id based item not found <- written in ResponseStatusException...Controller");
        //
        //
        //            // OPTION # 2 (return error.jsp page with specific variable string message passed)
        //            // return jsp error page with specific to "ID not found" error message
        //            modelMap.addAttribute("error","Todo id # " + id + " based item not found <- written in ResponseStatusException...Controller");
        //            // return "error_nf";
        //
        //
        //            // OPTION # 3 (just disabled ----> return "error_nf")
        //            // We will not use <error_nf.php> in above line.
        //            // This will using <index.php> original webpage which already has "error" tag and we will associate error message only.
        //
        //        } else{
        //            modelMap.addAttribute("message",id + " <-- Todo Found!");
        //
        //        }


        return "index"; // JSP page name (without prefix/suffix)

    }


    ///////////////////////////     FindBY User     ///////////////////////////
    // New Derived Query bases JPA function //////////////
    // findByUsername() is ++ Faster than  findBYusername1() no steram ALL in here.
    @RequestMapping(value = "/findByUser", method = {RequestMethod.GET, RequestMethod.POST})
    @Operation(summary = "GET todo by Username/email", description = "Use Username/email to retrieve Todo",
            parameters = {@Parameter(name = "user", description = "email"  )})
    public String findByUsername(@RequestParam(value = "user")String enter_username,
                                 ModelMap modelMap ){

        Optional<List<Todo>> t1 =  repo_dao_springData_todo_jpa.findByUsername(enter_username);

        // this technique Improve Exception Handling
        if (t1.isEmpty() || t1.get().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong username, retry...");
        }

        modelMap.addAttribute("listMapVar", t1.get());
        modelMap.addAttribute("totally", t1.get().size());
        return "index";
    }

    //  OLD -STEAMING ALL List = POOR Performance ////////////////////////////
    // Not in use, taken off from JSP - front end (listall.jsp) side
    // /////////////////////////     findByUser     ///////////////////////////
    // ---BELOW  NOT in USE---------------
    @RequestMapping(value = "/findByUser1", method = RequestMethod.GET)
    public String findBYusername1(@RequestParam(value = "u")String enter_username, ModelMap modelMap ){

        // Predicate function practice
        Predicate<? super Todo> p1 = x-> x.getUsername().equalsIgnoreCase(enter_username);
        List<Todo> t1 =  repo_dao_springData_todo_jpa.findAll().stream().filter(p1).toList();

        // or // List<Todo> t1 =  repo_dao_springData_todo_jpa.findAll().stream().filter(x->x.getUsername().equals(enter_username)).toList();
        modelMap.addAttribute("listMapVar", t1);
        modelMap.addAttribute("totally", t1.size());
        return "index";
    }



    ///////////////////////////     DELETE BY UID    ///////////////////////////

//    @RequestMapping(value = "/deleteByUID", method = {RequestMethod.GET , RequestMethod.POST})
//    @Operation(summary = "DELETE Todo by UID - JSP" , description = "DELETE by Uid. User passes UID.. that want to delete." )
//    public ResponseEntity<String> deleteByUID(@RequestParam("u") int uid, ModelMap map) {
//
//        boolean x =  repo_dao_springData_todo_jpa.existsByUid(uid); // if exist == true
//
//        if (x == true) {
//            toDo_Services.deleteByUID_springDataJPA(uid);
//            l1.info("Deleted UID: " + uid);
//            map.addAttribute("message","✅ Deleted UID: " + uid);
//            return ResponseEntity.ok("Todo deleted successfully");
//        } else {
//            l1.info("Todo uid not foundd: " + uid);
//            map.addAttribute("error"," ❌ Todo uid -> " + uid + " not foundd!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo with UID " + uid + " not found");
//        }
//
//    }

    @RequestMapping(value = "/deleteByUID", method = {RequestMethod.GET , RequestMethod.POST})
    @Operation(summary = "DELETE Todo by UID - JSP" , description = "DELETE by Uid. User passes UID.. that want to delete." )
    public String deleteByUID(@RequestParam(value = "u") int uid, ModelMap modelMap) {


            boolean x =  repo_dao_springData_todo_jpa.existsByUid(uid); // if exist == true

            // before deleting, checking if the ID exist or not!!, this is good practice..
            if (x == true ){ // if exist == true
                toDo_Services.deleteByUID_springDataJPA(uid);
                l1.info("Deleted UID: " + uid);
                modelMap.addAttribute("message","✅ Deleted UID: " + uid);

            } else {
                l1.info("Todo uid not foundd: " + uid);
                modelMap.addAttribute("error"," ❌ Todo uid -> " + uid + " not foundd!");

                // stopping white-label page. Thanks to this exception handling method ---> handleError_to_avoid_white_label_page()
                // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo with UID " + uid + " not found");  //-> gives wWhitelabel Error Page
            }

               //return "redirect:/api/todo/list";  // --->this return logged in User's specific todos
        List<Todo> list1 = toDo_Services.findbyALL();
        modelMap.addAttribute("listMapVar", list1);
        return "index";
    }





    ///////////////////////////     DELETE BY ID    ///////////////////////////
    @RequestMapping(value = "/delByID", method = RequestMethod.GET)
    @Operation(summary = "DELETE Todo by ID - JSP" , description = "DELETE by Id. User passes ID.. that want to delete." )
    public String deleteById(@RequestParam(value = "u") int id, ModelMap modelMap) {

        if (repo_dao_springData_todo_jpa.existsByCustomId(id)){
            repo_dao_springData_todo_jpa.deleteByCustomId(id);
            l1.info("DELETEDD::::::::" + id);
            modelMap.addAttribute("message","✅ Deleted ID: " + id);
        }
        else{
            l1.info("Todo ID not foundd: " + id);
            modelMap.addAttribute("error"," ❌ Todo id -> " + id + " not foundd!");
        }
        List<Todo> list1 = toDo_Services.findbyALL();
        modelMap.addAttribute("listMapVar", list1);
        return "index";
    }


    /////////////       CUSTOM QUERY supported by JpaRepository  //////////////////////
    @RequestMapping(value = "/sam", method = RequestMethod.GET)
    public String getSum(ModelMap modelMap){
        List<Todo> todo =  toDo_Services.getAllSumit().get();
        modelMap.addAttribute("listMapVar", todo);
        modelMap.addAttribute("totally", todo.size());
        return "index";
    }


    ///////////////////////////     hardcode1     ///////////////////////////
    //////////////////// This end point ONLY used to ADD HARDCODED values into SQL
    /////////// CHange values to NOT readOnly..here...to make it work @Transactional(readOnly = true)
    @RequestMapping(value = {"/hardcode1"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(readOnly = true) // , propagation = Propagation.)
    @Operation(summary = "Hardcoded 4 data entries into dB.", description = "4 data entries-hardcoded data" )
    // I kept this hard coded data as READONLY so will not get injected to DB
    public String hard_code_data_sprData_jpa(
            ModelMap modelMap,  @SessionAttribute(value ="uid_email") String uid_email) {

        // ADD: locally add to LIST
        List<Todo> list1 = new ArrayList<>();
        list1.add(new Todo(001,  "VJ@Karma.com", "added by Todo_Controller.java/hardcoded1", LocalDate.now(), LocalDate.now().plusYears(1), false,  null));

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


        // ADD: locally add to LIST
        List<Todo> list4 = new ArrayList<>();
        list4.add(new Todo(004, uid_email , "Adding 2025", LocalDate.of(2025, 01, 22), LocalDate.of(2026, 11, 25), false,  null));
        // INSERTING to SQL
        toDo_Services.insert_list_data_springDataJpa(list4);


        // FETCH all from SQL
        List<Todo> existing = toDo_Services.findbyALL();
        // Map above list to display on frontend UI
        modelMap.addAttribute("listMapVar", existing);


        return "index";
    }






    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @Operation(summary = "GET update Todo by UID - in JSP)" , description = "Update by UId" )
    public String get_UpdateByUID_page(ModelMap modelMap,
                                       @RequestParam(value = "u") int uid_TakenFromURL) {

        // Retrieved current record/data
        Todo retrieve_current_rec = toDo_Services.findByUid(uid_TakenFromURL).get(0); //toDo_Services.findByID_from_List(id);

        // To inject our pre-fill the data from object to model to front_end
        // This model is dupming data into insert3_SprDataJPA
        modelMap.addAttribute("todo_obj_spring_data_jpa2", retrieve_current_rec);

        // insertion done in above step now , reloading Listing page
        return "insert3_SprDataJPA";
    }

    ///////////////////////////     UPDATE GET + POST     ///////////////////////////
    //This updated method will NOT create new UID upon modifying/updating existing record.
    @RequestMapping(value = "/update", method = {RequestMethod.PUT, RequestMethod.POST}) // PUT = modifying existing resource // POST = new resource creation
    public String post_UpdateByUID_page(ModelMap modelMap,
                                        @RequestParam("u") int uid_TakenFrom_HtmlForm_URL, // ---> this is unique uid value, taken from mapping HTML user's input
                                        @ModelAttribute("todo_obj_spring_data_jpa2") @Valid Todo todo_obj_spring_data_jpa2,  // user inputed form's data this brings data from HTML VIEW FORM ---> into this method here
                                        BindingResult bindingResult,
                                        Errors err    ) {

        // this is fetching existing UID of the record, first we need UID
//        todo_obj_spring_data_jpa2.setUid(uid_TakenFrom_HtmlForm_URL); // this is to prevent user from changing any uid, if user enters diff uid, still it will not impact, because we are hardcoding before onw uid to after one.

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

        // FETCH all from SQL
        List<Todo> all_current = toDo_Services.findbyALL();
        // Map above list to display on frontend UI
        modelMap.addAttribute("listMapVar", all_current);
        modelMap.addAttribute("message", " ✅ Todo Updated successfully for uid -> " + uid_TakenFrom_HtmlForm_URL + " ✅ "+todo_obj_spring_data_jpa2);

        return  "index";
    }




    // backup UPLOAD GET WORKING...
    ///////////////////////////     UPLOAD     ///////////////////////////
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String get_attach_function(
            ModelMap modelMap,
            @RequestParam(value = "u") int uidTakenFromHtmlTag) {

        // Retrieved current record/data
        Todo existingTodo = toDo_Services.findByUid(uidTakenFromHtmlTag).get(0);

        // insertion of fetched above data's mapping is done in below step now,

        modelMap.addAttribute("todo55", existingTodo); // REF: upload.jsp // half page of this form is loaded with existing <todo's data>
        modelMap.addAttribute("fileUpload_holder", new MultipartFile_holder()); // REF: upload.jsp // half page of this form is loaded with <fileUpload_holder>

        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String post_now_uploading_here(
            ModelMap modelMap,
            @ModelAttribute("multipartFile") MultipartFile_holder multipartFile,
            @RequestParam(value = "u") int uidTakenFromHtmlTag, // this result in [ old val, new val], we need new val by user so
            @ModelAttribute("todo55") @Valid Todo todo55,  // this brings data from HTML VIEW FORM to here--->
            BindingResult bindingResult,
            Errors err
    ) {

        //finding existing Todo
        List<Todo> existingTodo = toDo_Services.findByUid(uidTakenFromHtmlTag);

        try {
            MultipartFile file = multipartFile.getMultipartFile();
            if (file != null && !file.isEmpty()) {
                byte[] fileData = file.getBytes();
                Blob blob = new SerialBlob(fileData);
                existingTodo.get(0).setAttach(blob);
                repo_dao_springData_todo_jpa.save(existingTodo.get(0));
            }
        } catch (IOException | SQLException e) {
            l1.error("File upload failed", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");
        }
                                    /*
                                    ##  This works great for small size files like byte[] datatype. We change model to byte[] or BLOB depending on the requirement
                                        existingTodo.setattach(multipartFile.getMultipartFile().getBytes());
                                        repo_dao_springData_todo_jpa.save(existingTodo);
                                    */

        //return "redirect:/api/todo/list";  // --->this return logged in User's specific todos
        return "redirect:/api/todo/listall";
    }






















// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ---------------------------------- JSON --------------------------------------
// ------------------------------------------------------------------------------
// ----------------------------------POSTMAN-------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// -----------------------------PURE REST API JSON-------------------------------
// ------------------------------------------------------------------------------
//   If you decide to make your application a pure ""REST API"" (returning JSON instead of rendering a JSP page), ...
//   .....then you'd need to change it to return JSON, like this:

    @Operation(summary = "Json Central", description = "JSON Central Guide to all api point")
    @RequestMapping(value = "/jsoncentral", method = {RequestMethod.GET , RequestMethod.POST})
    public String show_allJson_jsp() {
        return "alljson";  // This will look for WEB-INF/jsp/alljson.jsp
    }




    //    http://localhost:8080/api/todo/listjson
    @Operation(summary = "Get all Todos (JSON+ResponseEntity)", description = "GET all todos in JSON (returning ResponseEntity<List<Todo>>) Returns a list of all todo items in JSON format")
    @RequestMapping(value = "/listjson", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<Todo>> listAll_todos_json(ModelMap modelMap) {
        return new ResponseEntity<List<Todo>>(toDo_Services.findbyALL(), HttpStatus.OK);
    }

    //    http://localhost:8080/api/todo/listjson1
    ///////////////////////////     JSON     ///////////////////////////
    // JSON: below is without the use of ----> [responseEntity<> wrapper ]
    @Operation(summary = "Get all Todos (JSON)", description = "GET all todo in JSON(returning  List<Todo>) (without ResponseEntity). Returns a list of all todo items in JSON format")
    @RequestMapping(value = "/listjson1", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Todo> listAll_todos_json1() {
        List<Todo> ans =  toDo_Services.findbyALL();
        return ans;
    }




    /**
     * 6️⃣ Get Todo By (SINGLE) ID (Returns JSON)
     */
    //    @Operation(summary = "GET Single Todo by id" , description = "GET Todo By (SINGLE) ID (Returns JSON)")
    //    @RequestMapping(value = "/id/{singeleiid}",  method = RequestMethod.GET)
    //    @ResponseBody
    //    public ResponseEntity<?> getTodoById(@PathVariable(value = "singeleiid") int id){
    //        // Debug method
    //        // System.out.println("-----------Fetching Todo with ID: " + iid); // Log the ID
    //
    //        // Step 1: Fetch the Todo item from the database using its ID
    //        Optional<List<Todo>> todo_list = repo_dao_springData_todo_jpa.findById(id);
    //
    //        // Step 2: Check if the Todo item exists
    //        if (todo_list.isPresent() && !todo_list.get().isEmpty()) {
    //            // Step 3: If found, return the Todo item with HTTP status 200 OK
    //            return new ResponseEntity<>(todo_list.get().get(0), HttpStatus.OK);
    //
    //        } else {
    //
    //            // Step 4: If not found, return a 404 Not Found response with a message
    //            String errorMessage = "Todo with ID " + id + " not found";
    //            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    //        }
    //    }


    /**
     * 6️⃣ Get Todo By (MULTIPLE) ID (Returns JSON) multipleIds
     *    GET http://localhost:8080/api/todo/id/990,1,2,3,51,4
     */
    @Operation(summary = "Get Todo By (MULTIPLE) ID (Returns JSON) using comma" , description = "GET Todo By (Mutiple) ID (Returns JSON) GET http://localhost:8080/api/todo/id/990,1,2,3,51,4")
    @RequestMapping(value = "/id/{multipleiid}",  method = {RequestMethod.GET , RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> getTodoByIdss_JSON(@PathVariable(value = "multipleiid") String multiple_ids){

        // step:1 - get seperate is in LIST<Integer>
        List<Integer> ids_in_list = Arrays.stream(multiple_ids.split(","))
                .map(x-> Integer.parseInt(x))
                .toList();


        // step-2: Fetch the Todos from the database using the list of IDs
        List<Todo> todos_multiple = repo_dao_springData_todo_jpa.findByCustomId(ids_in_list);


        // Step 2: Check if the Todo item exists
        if ( todos_multiple.isEmpty()) {
            // Step 3: If not found, return 404 - NOT FOUND
            return new ResponseEntity<>("NO todos found ", HttpStatus.NOT_FOUND);

        } else {
            // Step 4: If found, return 200 - OK
            return new ResponseEntity<>(todos_multiple, HttpStatus.OK);
        }

    }


    /////----------------- INSERT - SpringDataJPA SQL == insert4 (GET/POST) --------JSON
    /**
     * 5️⃣ POST/ INSERT Todo via API (Returns JSON)
     *    POST http://localhost:8080/api/todo/insert4
     * {
     *     "id": 456,
     *     "username": "test1@test2.com",
     *     "description": "Added from PostMAN",
     *     "creationDate": "2025-01-30",
     *     "targetDate": "2026-01-30",
     *     "done": false
     * }
     */
    @Operation(summary = "POST/ INSERT Todo via API (Returns JSON)", description = "insert todo record. User to send data in json.")
    @RequestMapping(value = { "/insert4" }, method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> insert4TodoAPI_JSON(
            @RequestBody @Valid Todo todoh,
            BindingResult bindingResult) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors()); // 400 Bad Request
        }

        // Saved TODO to database
        Todo savedTodo = repo_dao_springData_todo_jpa.save(todoh);

        // GET URI location path link
        URI  get_current_request_uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{multipleiid}").buildAndExpand(savedTodo.getId()).toUri();

        // Create Map
        // Map(K:V) Map(URIstring:TodoObj)
        // Map = (K)URI link + (V)saved TODO
        Map<URI,Todo> map = new HashMap<URI,Todo>();
        map.put(get_current_request_uri,savedTodo);

        return ResponseEntity.status(HttpStatus.CREATED).body(map);  // returning only status (201 Created)  ---> return only path where URI created

    }



    /**
     * 7️⃣ PUT-Update Todo By UID (Returns JSON)
     *    PUT http://localhost:8080/api/todo/uid/97
     * {
     *     "id": 123,
     *     "username": "nyc@njusa.com",
     *     "description": "Added from Postman edited2",
     *     "creationDate": "2025-01-30",
     *     "targetDate": "2056-10-24",
     *     "done": false
     * }
     */
    @Operation(summary = "PUT-Update Todo By UID (Returns JSON)", description = "Update todo record by UID. PUT http://localhost:8080/api/todo/uid/97")
    @RequestMapping(value = "/uid/{uiid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateTodoByUID_JSON(
            @PathVariable(value = "uiid") int uid,
            @RequestBody @Valid Todo updatedTodo,
            BindingResult bindingResult) {

        // Edge case-1 - Binding error catch
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors()); // 400 Bad Request
        }

        // Edge case-2 - Todo not Found
        if (!repo_dao_springData_todo_jpa.existsById(uid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found"); // 404 Not Found
        }

        //user input data(user-entered-todo) will not have UID, so binding/merging uid to user-entered-todo
        updatedTodo.setUid(uid);


        toDo_Services.updateByUid(updatedTodo);
        return new ResponseEntity<> ("updated successfullly " + toDo_Services.findByUid(uid) , HttpStatus.OK); // 200 OK
        // or
        // return  ResponseEntity.ok(savedTodo); // 200 OK
    }




    /**
     * 8️⃣ DELETE Todo by ID (Returns 204: noContent() created = void)
     *    DELETE http://localhost:8080/api/todo/id/1
     */
    @Operation(summary = "DELETE Todo by ID (Returns 204: noContent() created = void)" , description = "DELETE by id. User pass Id that want to delete. http://localhost:8080/api/todo/id/1" )
    @DeleteMapping("/id/{idd}")
    @ResponseBody
    public ResponseEntity<?> deleteTodo_by_id_JSON(@PathVariable(value = "idd") int id) {
        // edge case-1: if ID Todo not found...
        if ( repo_dao_springData_todo_jpa.findById_custom(id).get().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found"); // 404 Not Found
        }

        repo_dao_springData_todo_jpa.deleteByCustomId(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


    ///////////////////////////     existByUid (GET)    ///////////////////////////
    // to check if the UID exit or not, just simply returns TRUE orFALSE BOOLEAN value only
    // Output directly into BODY of HTML using @ResponseBody
    @ResponseBody
    @RequestMapping(value = "/api/todo/existByUID", method = {RequestMethod.GET , RequestMethod.POST})
    @Operation(
            summary = "Check if a Todo exists by UID",
            description = "Checks if a Todo item exists by UID. Returns a boolean value (true/false).",
            parameters = {
                    @Parameter(
                            name = "u",
                            description = "The UID of the Todo item to check",
                            required = true,
                            in = ParameterIn.QUERY,
                            example = "123",
                            hidden = false // Hides this parameter from Swagger UI

                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "200=Todo item exists (true)"),
                    @ApiResponse(responseCode = "404", description = "404=Todo item does not exist (false)")
            })
    public ResponseEntity<Boolean> existByUid_JSON(@RequestParam(value = "u") int uid,
                                              ModelMap modelMap){
        boolean x =  repo_dao_springData_todo_jpa.existsByUid(uid);

        if (x){   // if (x == true)
            return new ResponseEntity<>(x, HttpStatus.FOUND); // 302 code
        }
        else{
            return new ResponseEntity<>(x, HttpStatus.NOT_FOUND); //404 error code
        }
    }


}








/*
 if (todo55.getattach() != null && !todo.getAttach().isEmpty()) {
                // attach attachment
                existingTodo.setattach(todo55.getMultipartFile().getBytes());
                // save/commit object to dB via SpringJPA
                repo_dao_springData_todo_jpa.save(existingTodo);
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

                return repo_dao_springData_todo_jpa.save(tg1);


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
///////////////////////////////     INSERT   Automatic     ///////////////////////////////////////////////////////////////////
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