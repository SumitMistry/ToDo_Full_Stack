package springboot.ToDo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This runs before ToDo_Main(psvm) main method.
 * The main method run later....
 * This class/method runs first
 */
//@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Autorun_justReference implements CommandLineRunner {

    Autorun_justReference(){
        super();
    }

    @Override
    public void run(String[] strings) throws Exception{
        System.out.println("\n\n                              AutoRunner:   in CMD line Runner " +
                "                              AutoRunner:   No methdos is defined here...jjust fyi...." +
        "                              AutoRunner:   Any method defined here...will autorun ...fyi.... \n\n");

    }

}
