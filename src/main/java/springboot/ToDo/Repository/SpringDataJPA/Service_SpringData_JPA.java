package springboot.ToDo.Repository.SpringDataJPA;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.ToDo.Model.Todo_model_SprDataJPA;

import java.util.List;

@Service
@NoArgsConstructor
public class Service_SpringData_JPA {

    Logger l1 = LoggerFactory.getLogger(Class.class);

    @Autowired
    private Repo_DAO_SpringData_JPA repo_dao_springData_jpa;


    public void springDataJpa_insert_data(List<Todo_model_SprDataJPA> list1){
        list1.forEach(x->repo_dao_springData_jpa.save(x));
        l1.info("\n ----> SERVICES: ADDING FAKE hard-coded DATA...Logging: " + list1);
    }




}
