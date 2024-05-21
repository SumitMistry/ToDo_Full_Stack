package springboot.ToDo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Autorun_justReference implements CommandLineRunner {

    Autorun_justReference(){
        super();
    }

    @Override
    public void run(String[] strings) throws Exception{
        System.out.println("\n\n                              in CMD line Runner " +
                "                              AutoRunner: No methdos is defined here...jjust fyi....\n\n");

    }

}
