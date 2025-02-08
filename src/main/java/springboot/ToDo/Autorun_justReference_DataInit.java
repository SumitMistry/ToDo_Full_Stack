package springboot.ToDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;
import springboot.ToDo.Controller.User_Signup_Controller;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Model.User;
import springboot.ToDo.Repository.Repo_DAO_SpringData_JPA;
import springboot.ToDo.Repository.Repo_DAO_User_JPA;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.User_Signup_Services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Refer to...... \ToDo\src\main\java\springboot\ToAutorun_justReference CommandLineRunner in Spring Boot.pdf
 *
 *
 * This makes an excellent choice for various post-startup tasks to run within.
 *
 * In Java Spring Boot, the CommandLineRunner interface is used to execute a block of code .....after....the Spring Boot application has started.
 * It is typically used to run tasks like initializing resources, setting up default data, or executing any logic that needs to happen ......after....... the application context has been initialized but....... before........ the application starts serving requests
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@SessionAttributes({"uid_email", "pass", "totally"})
public class Autorun_justReference_DataInit implements CommandLineRunner{

    @Autowired
    private Login_Services login_Services;
    @Autowired
    private Repo_DAO_SpringData_JPA repo_dao_springData_jpa;

    @Autowired
    private Repo_DAO_User_JPA repo_dao_user_jpa;

    @Override
    public void run(String[] str) throws Exception {

        // AUTO-IMPORT 1 entry from JPA (this is not relying on data.sql) This is 1 record adding for sample based on user data fetched.
        String usr_retrived = (String) new ModelMap().get("uid_email"); // this is null and not possible because username is not enterd before login / autorun will have no userdata, as autorun runs first.
        //System.out.println("sumit here =--=-=-=-=-=-=-=-=-=-=" +usr_retrived + "    ***********  " +random);


        //Delete lot of records first....
        int rowsDeleted = repo_dao_springData_jpa.deleteRecords();

        // ADD 2 records::::: USED JPA for query building in below.....this will make query1
        // automatically ....by below 4 lines..
        Random rand = new Random();         int random1 = rand.nextInt(1,999)+1;  int random2 = rand.nextInt(1,999)+1;
        Todo todoList1 = new Todo( random1, "sumit@bofa.com","Autorun_justRef_dataInit.java", LocalDate.now(), LocalDate.now().plusYears(1), true,null);
        Todo todoList2 = new Todo( random2, "vraj@yyz.com","Autorun_justRef_dataInit.java", LocalDate.now(), LocalDate.now().plusYears(1), false,null);
        repo_dao_springData_jpa.save(todoList1);
        repo_dao_springData_jpa.save(todoList2);

        // ADD 2 users to db upon start // signup 2 users automatically upon start
//        User u1 = new User_Signup_Services().signup_insert_encoded_pass("sumit@bofa.com", "1" , new String[]{"USER,ADMIN"});
//        repo_dao_user_jpa.save(u1);
//        u1.setPassword_raw("1");



        // how to call controller or API here for the testing??
        // I dont want to invoke method from backend, but I want from api endpoint in springboot
        // signup_insert_raw_pass_and_encoded_pass



        if (login_Services.toString().equals("admin")) {
            System.out.println("\n\n                              " +"No users found in the database. Initializing default users...");
            System.out.println("Default users initialized... from......... Autorun_justReference_DataInit implements CommandLineRunner");
        } else {
            System.out.println("\n\n                              " +"Users already exist in the database. Skipping initialization......... Autorun_justReference_DataInit implements CommandLineRunner"+"\n\n                              " );
        }
    }


}
