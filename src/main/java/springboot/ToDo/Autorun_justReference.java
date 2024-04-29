package springboot.ToDo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Autorun_justReference implements CommandLineRunner {

    Autorun_justReference(){
        super();
    }

    @Override
    public void run(String[] strings) throws Exception{
        System.out.println("in CMD line Runner \n\n" +
                "No methdos is defined here...jjust fyi....");

    }

}
