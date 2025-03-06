package springboot.ToDo.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Services.Login_Services;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//@Controller
//@RequestMapping(value = "/")
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@SessionAttributes({"uid_email", "pass", "totally"})  // when you want to store a value in whole session, use this.
// you have to pass this values from frontend variable standpoint, so it is <uid_email> not <usernr>
// <usernr> is backend variable, this will not work
// "uid_email" is the frontend variable, passing this will be able to save as session. it will work..
public class Header_Controller<T> {



}





