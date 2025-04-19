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

    @Value("${gemini.api.key}")
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
            String ai_json_result = call_ai_api(data_goes_to_ai);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules(); // for LocalDate

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




    // This is GOOGLE GEMINI AI specific method-------------------------
    // API usage data is here=  https://console.cloud.google.com/apis/api/generativelanguage.googleapis.com/metrics?project=gen-lang-client-0399073710&pli=1

    //WORKING =  Create a TODO with description carwash, creationDate (2025-04-19), targetDate (2026-04-19), done (true). Return only raw JSON. No explanations. No markdown
    private String call_ai_api(String prompt) throws Exception {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

        Map<String, Object> body = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", prompt + "\n\nRespond ONLY with raw JSON. No explanation. No markdown."))
                ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", ai_sm_ky);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        String textResponse = (String) parts.get(0).get("text");

        // Optional: log original Gemini response
        System.out.println("\n\n\n\n\n --------------->> Gemini Raw Response:\n" + textResponse);

        // Extract JSON block (remove markdown if present)
        String cleanedJson = extractJson(textResponse);

        return cleanedJson;
    }




    private String extractJson(String text) {
        // If Gemini responds with markdown, extract content between triple backticks
        if (text.contains("```")) {
            return text.replaceAll("(?s).*?```(?:json)?\\s*(\\{.*?\\})\\s*```.*", "$1");
        }
        // Otherwise return plain response (assumes it's JSON already)
        return text.trim();
    }








}



/*

    private String call_ai_api(String prompt) throws Exception {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

        Map<String, Object> body = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", prompt + "\n\nRespond ONLY with a JSON having: id (int), username (email), description, creationDate (yyyy-MM-dd), targetDate (yyyy-MM-dd), done (boolean). No explanations."))
                ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", ai_sm_ky);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        // Extract JSON content
        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        String textResponse = (String) parts.get(0).get("text");

        return textResponse;
 */