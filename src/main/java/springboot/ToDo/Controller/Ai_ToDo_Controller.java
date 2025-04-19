package springboot.ToDo.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Services.ToDo_Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@ConfigurationProperties
@RequestMapping(value = "/api/todo")
@SessionAttributes({"uid_email", "pass", "totally", "listMapVar"})
public class Ai_ToDo_Controller {

    @Value("${spring.ai.openai.api-key}")
    private String ai_sm_ky;

    @Autowired
    private ToDo_Services toDoServices;

    @Autowired
    private Repo_DAO_SpringData_todo_JPA repo_dao_springData_todo_jpa;

    @RequestMapping(value = "/ai", method = RequestMethod.POST)
    public String map_ai_result_to_TodoModel(@RequestParam(value = "text_for_ai_submission") String data_goes_to_ai,
                                             ModelMap modelMap,
                                             RedirectAttributes redirectAttributes,
                                             @ModelAttribute(value = "totally") int totally,
                                             @ModelAttribute(value = "uid_email") String user_email ) {
        try {
            //check api key valid?
            if (ai_sm_ky == null || ai_sm_ky.isEmpty() || ai_sm_ky.isBlank()) {
                throw new IllegalStateException("API KEY not config correctly...check!!");
            }

            //convert incoming sentence to ai - based json model
            //get ai result ---> map ai-result ----> JSON object ----> parse into ToDo model obj --->  save to db
            String ai_json_result = callOpenAI_api(data_goes_to_ai);
            ObjectMapper objectMapper = new ObjectMapper();
            Todo out_todo = objectMapper.readValue(ai_json_result,Todo.class);

            //hardcoded - necessary mapping
            out_todo.setUid(0);
            out_todo.setAttach(null);
            out_todo.setId(totally+1);
            out_todo.setUsername(user_email);

            repo_dao_springData_todo_jpa.save(out_todo); //toDoServices.insert_list_data_springDataJpa(Arrays.asList(out_todo));

            //Notification
            modelMap.put("message", "✅ AI TODO created!");


            // Last step = FETCH whole list of all Todos, this should show us newly added by our AI
            List<Todo> todoList = toDoServices.findbyALL();
            modelMap.addAttribute("listMapVar", todoList);


        } catch (Exception x) {
            x.printStackTrace();
            modelMap.put("error", "❌ AI failed to create TODO with error = " + x.getMessage()+ x.toString());
        }

        return "index";
    }



    private String callOpenAI_api(String prompt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are a smart assistant that returns Java TODO data in JSON. Only return JSON."),
                Map.of("role", "user", "content", prompt + "\nRespond ONLY with a valid JSON with: id (int), username (email), description, creationDate (yyyy-MM-dd), targetDate (yyyy-MM-dd), done (boolean).")
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ai_sm_ky); // set in application.properties

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        Map<String, Object> message = (Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0);
        Map<String, String> messageContent = (Map<String, String>) message.get("message");

        return messageContent.get("content");
    }





}
