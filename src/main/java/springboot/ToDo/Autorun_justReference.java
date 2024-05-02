package springboot.ToDo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import springboot.ToDo.Controller.ToDo_Controller;

import java.util.function.BiFunction;


@Component
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
