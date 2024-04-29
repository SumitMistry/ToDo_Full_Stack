package springboot.ToDo.Repository.SpringDataJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;


@Component
public class Autorun_SpringData_JPA implements CommandLineRunner {

    private final Controller_SpringData_JPA controller_springData_jpa;

    Autorun_SpringData_JPA(Controller_SpringData_JPA controller_springData_jpa){
        super();
        this. controller_springData_jpa = controller_springData_jpa;
    }

    @Override
    public void run(String[] strings) throws Exception{
        System.out.println("in CMD line Runner \n\n" +
                "No methdos is defined here...jjust fyi....");

    }

}
