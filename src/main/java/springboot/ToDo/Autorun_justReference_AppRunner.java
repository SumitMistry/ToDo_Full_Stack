package springboot.ToDo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
public class Autorun_justReference_AppRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args1) throws Exception {
        System.out.println("Application started with arguments: " + args1.getOptionNames()  + " .....Autorun_justReference_AppRunner implements ApplicationRunner");
    }
}
