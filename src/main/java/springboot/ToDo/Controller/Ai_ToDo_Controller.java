package springboot.ToDo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Services.ToDo_Services;

import java.util.List;
import java.util.Optional;

@Controller
@ConfigurationProperties
@RequestMapping(value = "/api/todo")
public class Ai_ToDo_Controller {

    @Value("${spring.ai.openai.api-key}")
    private String ai_sm_ky;

    @Autowired
    private ToDo_Services toDoServices;

    @RequestMapping(value = "/ai", method = RequestMethod.POST)
    public String createAI_todo(@RequestParam(value = "text_for_ai_submission") String data_goes_to_ai,
                                ModelMap modelMap){

        //check api key valid?
        if ( ai_sm_ky ==null || ai_sm_ky.isEmpty() || ai_sm_ky.isBlank() ){
            throw  new IllegalStateException("API KEY not config correctly...check!!");
        }

        //convert incoming sentence to ai - based json model



        // GET whole list of all Todos, this should show us newly added by our AI
        List<Todo> todoList = toDoServices.findbyALL();
        modelMap.addAttribute("listMapVar", todoList);

        return "index";

    }



}
