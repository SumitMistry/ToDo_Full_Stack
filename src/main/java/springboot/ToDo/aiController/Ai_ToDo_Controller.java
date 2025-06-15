package springboot.ToDo.aiController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.ToDo.Model.Todo;
import springboot.ToDo.Repository.Repo_DAO_SpringData_todo_JPA;
import springboot.ToDo.Services.ToDo_Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api/todo")
@SessionAttributes({"uid_email", "pass", "totally", "listMapVar"})
public class Ai_ToDo_Controller {

    private static final Logger logger = LoggerFactory.getLogger(Ai_ToDo_Controller.class);

    @Value("${gemini.api.key}")
    private String aiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Autowired
    private ToDo_Services toDoServices;

    @Autowired
    private Repo_DAO_SpringData_todo_JPA todoRepo;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    @RequestMapping(value = "/ai", method = RequestMethod.POST)
    public String handleAiRequest(@RequestParam("text_for_ai_submission") String userInput,
                                  ModelMap modelMap,
                                  // @ModelAttribute("totally") int totally, // not good, if totally not parsed,or open once, then it is null and error..so not good one.
                                  @ModelAttribute("uid_email") String userEmail) {
        try {

            String aiJson;

            if (userInput.toLowerCase().matches(".*(list|find|delete|search|update).*")){
                aiJson = construct_STRING_prompt_for_OTHERS(userInput);
            }
            else {
                aiJson = construct_STRING_prompt_for_CREATE(userInput);
            }

            ObjectMapper mapper = objectMapper();
            JsonNode root = mapper.readTree(aiJson);

            if (root.has("action")) {
                return routing_ai_JSON_decision_to_correct_endpoint(root, modelMap);
            } else if (root.has("description") && root.has("creationDate") && root.has("targetDate")) {
                Todo todo = mapper.treeToValue(root, Todo.class);

                Random rand = new Random();         int random1 = rand.nextInt(1,999)+1;
                todo.setId(random1);
                todo.setUid(0);
                todo.setAttach(null);
                todo.setUsername(userEmail);
                todo.setCreationDate(java.time.LocalDate.now());

                Todo created = todoRepo.save(todo);
                modelMap.put("message", "✅ TODO created via AI!" +created.toString()+  root  + root.asText() + mapper + aiJson + userInput);
            } else {
                modelMap.put("error", "❌ Unrecognized AI response format." + root + root.asText() + mapper + aiJson + userInput);
            }

        } catch (Exception e) {
            logger.error("Error handling AI response", e);
            modelMap.put("error", "❌ AI failed to process your input." + e.toString() + e.getMessage() + userInput);
        }

        List<Todo> todoList = toDoServices.findbyALL();
        modelMap.addAttribute("listMapVar", todoList);

        return "index";
    }


    private String construct_STRING_prompt_for_OTHERS(String input) throws Exception {

        String prompt_other = "You are a smart API router for a TODO app. Return JSON with the correct action and its parameters.\n\n" +
                        "Supported actions with hints:\n" +
                        "- listAll → list, show, display, all\n" +
                        "- jsonCentral\n" +
                        "- findByUser (username) → user, username\n" +
                        "- findById (id) → find, get, id\n" +
                        "- findByUID (uid) → fetch, find, uid\n" +
                        "- deleteByUID (uid) → delete, remove, uid\n" +
                        "- updateByUID (uid) → update, change, edit, uid\n" +
                        "- checkExistByUID (uid) → exists, check, uid\n" +
                        "- search (keyword) → search, keyword, match\n" +
                        "- multipleIds (ids) → many, multiple, ids\n" +
                        "- dateRange (from, to) → date, range, from, to\n\n" +
                        "Examples:\n" +
                        "Input: list all todos\nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: give all \nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: show all \nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: all tasks \nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: show my tasks\nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: delete a todo with uid <uid>\nOutput: { \"action\": \"deleteByUID\", \"params\": { \"uid\": <uid> } }\n" +
                        "Input: search todos for keyword <keyword>\nOutput: { \"action\": \"search\", \"params\": { \"keyword\": \"<keyword>\" } }\n\n" +
                        "Try to infer the best matching action from user input, even if phrased naturally or with synonyms.\n" +
                        "Respond ONLY with valid JSON like this:\n" +
                        "{ \"action\": \"action_name\", \"params\": { ... } }\n\n" +
                        "Input: " + input ;

        Map<String, Object> textPart = Map.of("text", prompt_other);
        Map<String, Object> contentItem = Map.of("parts", List.of(textPart));
        Map<String, Object> body = Map.of("contents", List.of(contentItem));

        return callGeminiAPI(body);
    }

    private String construct_STRING_prompt_for_CREATE(String input) throws Exception {
        String today = LocalDate.now().toString(); // Ensures today's date is in YYYY-MM-DD

        String prompt_create = "You are a smart JSON generator for creating a TODO item.\n" +
                "Based on the input, return a valid JSON object with the following fields:\n" +
                "- username: \"dummy@example.com\"\n" +
                "- description: string (required)\n" +
                "- creationDate: \"" + today + "\" (today's date)\n" +
                "- targetDate: yyyy-MM-dd (MUST be in this exact format, no relative dates like 'tomorrow')\n" +
                "- done: true or false\n\n" +
                "DO NOT include 'id'.\n" +
                "If the input contains relative dates (e.g., 'tomorrow', 'next Monday'), convert them to YYYY-MM-DD format.\n" +
                "Return ONLY the raw JSON. No explanation or extra text.\n\n" +
                "Examples:\n" +
                "Input: create a todo to buy groceries tomorrow\n" +
                "Output: {\n" +
                "  \"username\": \"dummy@example.com\",\n" +
                "  \"description\": \"buy groceries\",\n" +
                "  \"creationDate\": \"" + today + "\",\n" +
                "  \"targetDate\": \"2025-04-22\",\n" +
                "  \"done\": false\n" +
                "}\n\n" +
                "Input: " + input;

        Map<String, Object> textPart = Map.of("text", prompt_create);
        Map<String, Object> contentItem = Map.of("parts", List.of(textPart));
        Map<String, Object> body = Map.of("contents", List.of(contentItem));


        return callGeminiAPI(body);
    }


    private String callGeminiAPI(Map<String, Object> body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", aiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(
                                                        geminiApiUrl,
                                                        HttpMethod.POST,
                                                        request,
                                                        Map.class
                                                        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Gemini API failed with status: " + response.getStatusCode());
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("No response candidates from Gemini");
        }

        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

        if (parts == null || parts.isEmpty()) {
            throw new RuntimeException("Gemini returned empty parts");
        }

        String text = (String) parts.get(0).get("text");

        // extract Json from String logic
        if (text.contains("```")) {
            return text.replaceAll("(?s).*?```(?:json)?\\s*(\\{.*?\\})\\s*```.*", "$1").trim();
        }
        return text.trim();

    }


    private String routing_ai_JSON_decision_to_correct_endpoint(JsonNode root, ModelMap modelMap) {
        String action = root.path("action").asText();
        JsonNode params = root.path("params");

        switch (action) {
            case "listAll":
                return "redirect:/api/todo/listall";
            case "jsonCentral":
                return "redirect:/api/todo/jsoncentral";
            case "findByUser":
                return "redirect:/api/todo/findByUser?user=" + params.path("username").asText();
            case "findById":
                return "redirect:/api/todo/findById?u=" + params.path("id").asInt();
            case "findByUID":
                return "redirect:/api/todo/findByUID?u=" + params.path("uid").asInt();
            case "deleteByUID":
                return "redirect:/api/todo/deleteByUID?u=" + params.path("uid").asInt();
            case "updateByUID":
                return "redirect:/api/todo/uid/" + params.path("uid").asInt();  // GET NOT SUPPORTED, ignore this for now.... // this is POST
            case "checkExistByUID":
                return "redirect:/api/todo/api/todo/existByUID?u=" + params.path("uid").asInt();
            case "search":
                return "redirect:/api/todo/searchAPI?searchKey=" + params.path("keyword").asText();
            case "multipleIds":
                return "redirect:/api/todo/id/" + params.path("ids").asText();
            case "dateRange":
                return "redirect:/api/todo/dateRangePicker?fromDate=" + params.path("from").asText()
                        + "&toDate=" + params.path("to").asText();
            default:
                modelMap.put("error", "❌ Unknown action: " + action);
                return "redirect:/api/todo/listall";
        }
    }
}


/*
            -list multiple todo ids 680,681
            -
            -
            -


 */