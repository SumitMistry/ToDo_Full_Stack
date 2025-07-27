package springboot.ToDo.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;




@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa;

    // This is especially to avoid white-label-page and take the error message to show to front end page.
    // this is handling -----> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo with UID " + uid + " not found");
    @ExceptionHandler(ResponseStatusException.class)
    public String handleError_to_avoid_white_label_page(ResponseStatusException ex, ModelMap model, HttpServletRequest request) {

        System.out.println("Exception handler triggered!");  // Check console

        // Add error message to model
        model.addAttribute("error", ex.getReason());

        // Add fresh data to model
        model.addAttribute("listMapVar", repo_dao_springData_todo_jpa.findAll());

        // Preserve other existing model attributes
        if (request.getSession() != null) {
            model.addAttribute("uid_email", request.getSession().getAttribute("uid_email"));
            model.addAttribute("pass", request.getSession().getAttribute("pass"));
            model.addAttribute("totally", request.getSession().getAttribute("totally"));
        }

        return "index";  // Direct return to index.jsp
    }

}
