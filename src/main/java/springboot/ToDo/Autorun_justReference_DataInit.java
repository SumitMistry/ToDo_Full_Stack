package springboot.ToDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springboot.ToDo.Services.Login_Services;

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
public class Autorun_justReference_DataInit implements CommandLineRunner{

    @Autowired
    private Login_Services login_Services;
    @Override
    public void run(String[] str) throws Exception {
        if (login_Services.toString().equals("admin")) {
            System.out.println("\n\n                              " +"No users found in the database. Initializing default users...");

            Login_Services user1 = new Login_Services();
            Login_Services user2 = new Login_Services();

            user1.validateLogin("test123", "123");
            user1.validateLogin("best321", "12345");

            System.out.println("Default users initialized... from......... Autorun_justReference_DataInit implements CommandLineRunner");
        } else {
            System.out.println("\n\n                              " +"Users already exist in the database. Skipping initialization......... Autorun_justReference_DataInit implements CommandLineRunner"+"\n\n                              " );
        }
    }


}
