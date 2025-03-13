package springboot.ToDo.A_ForPracticeONLY;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*
    ModelMap == map.put("listvarinJSP", a1fromJavaClass) -------------> This flows from Javs Class's a1 variable to ---> frontend
    (@RequestParam("username") String retrieved_username) -------------> frontend ---> Java class. This extracts submitted "username" tag's data into variable <retrieved_username>
        this value can be returning as array int[] or String[], example in update() POST method...below
 */
@Controller
@RequestMapping("api/todo/")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SessionAttributes({"uid_email", "pass", "totally"})
@Tag(name = "Controller# 2 = PersonJson Management", description = "SM: Operations related to pJSON") // Now, APIs will be grouped under "Todo Management" in Swagger UI. ✅ // used to group related API endpoints in the Swagger UI. It helps organize APIs by functionality, making them easier to understand and navigate.
public class PersonJSON_Controller {


    /*
        POST http://localhost:8080/api/todo/pJSON
                POST this:
                    {
                        "name" : "Sumikt" ,
                        "age" : 14
                    }

                RETRUN this:
                    ---> RETURNUNG JSON name=Sumikt  ---> RETURNUNG JSON age=14
    */
    @RequestMapping(value = "pJSON", method = RequestMethod.POST)
    @ResponseBody // this is mandatory
    public String createPersonJSON(@RequestBody PersonJSON_POJO_Model personJSONPOJOModel){
        System.out.println("I ....m ....here....");
        return "  ---> RETURNUNG JSON name=" + personJSONPOJOModel.getName() + "  ---> RETURNUNG JSON age="+ personJSONPOJOModel.getAge();
    }

}








/*



    @RequestMapping(value = "test1", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String test1(){
        System.out.println(" ....>>.....testing success ");
        return " .........testing success";   //goes to frontend
    }



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




