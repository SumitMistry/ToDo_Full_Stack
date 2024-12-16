package springboot.ToDo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
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
public class Autorun_justReference implements CommandLineRunner {

    Autorun_justReference(){
        super();
    }

    @Override
    public void run(String[] strings) throws Exception{
        System.out.println("\n\n                              " +
                "Autorun_justReference implements CommandLineRunner" +
                "AutoRunner:   in CMD line Runner " +
                "                              AutoRunner:   No methdos is defined here...jjust fyi...." +
        "                              AutoRunner:   Any method defined here...will autorun ...fyi.... \n\n");

    }

}
