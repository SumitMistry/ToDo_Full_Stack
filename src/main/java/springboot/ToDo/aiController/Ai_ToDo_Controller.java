package springboot.ToDo.aiController;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @RequestMapping(value = "/ai", method = RequestMethod.POST)
    public String user_stringInput_for_ai_submission(@RequestParam("user_textInput_for_ai_submission") String userInput_STRING,
                                                      ModelMap modelMap,
                                                      // @ModelAttribute("totally") int totally, // not good, if totally not parsed,or open once, then it is null and error..so not good one.
                                                      @ModelAttribute("uid_email") String userEmail) {
        try {

/// User passed STRING
            // Routing correct action
            String json_generated_by_AI;
            // for ---> ACTION
            if (userInput_STRING.toLowerCase().matches(".*(get|give|show|list|find|delete|search|update).*")){
                json_generated_by_AI = construct_STRING_prompt_for_OTHERS(userInput_STRING);
            }
            // for ---> NON-ACTION
            else {
                json_generated_by_AI = construct_STRING_prompt_for_CREATE(userInput_STRING);
            }


/// String --> JSON Node
            // This Jackson library (-->ObjectMapper) in Java help us perform serialization (Java objects to JSON) and deserialization (JSON to Java objects or JsonNode trees).
            com.fasterxml.jackson.databind.
                    ObjectMapper    jackson_obj_MAPPER  = new com.fasterxml.jackson.databind
                                                                                    .ObjectMapper()
                                                                                    .findAndRegisterModules();
            // The Jackson library in Java help us to parse JSON data into a tree-like structure
            //package ----> com.fasterxml.jackson -----> this library in Java helps us to parse JSON data into a tree-like structure.
            com.fasterxml.jackson.databind.
                    JsonNode        jackson_obj_JsonNode_ROOT    = jackson_obj_MAPPER.readTree(json_generated_by_AI);
                    //---> Parses the raw JSON string (json_generated_by_AI) into a JsonNode
                    //readTree() method parses the provided JSON source and returns the root node of the resulting JSON tree model as a JsonNode object
                                        System.out.println("--------------1-->           " + json_generated_by_AI);
                                        System.out.println("--------------2-->>>         " + jackson_obj_JsonNode_ROOT.toString());
                                        System.out.println("--------------3-->>>>        " + jackson_obj_JsonNode_ROOT);

            modelMap.addAttribute("message", " ⏳ Data is being processed by AI...  " +
                    "\n   ↪ User Input = " + userInput_STRING +
                    "\n   ↪ jackson_obj_MAPPER = " + jackson_obj_MAPPER +
                    "\n   ↪ jackson_obj_JsonNode_ROOT = " + jackson_obj_JsonNode_ROOT  +
                    "\n   ↪ json_generated_by_AI = " + json_generated_by_AI
            );


//// Identify Json & Route-API   --->     1) CREATE      2) UPDATE DELETE SHOW LIST SEARCH UPDATE
            if // Action items:
                (jackson_obj_JsonNode_ROOT.has("action")) { // action == delete (give|show|list|find|delete|search|update)
                return routing_ai_JSON_to_correct_endpoint(jackson_obj_JsonNode_ROOT, modelMap);
            } else if // "create" ...
                (jackson_obj_JsonNode_ROOT.has("description") && jackson_obj_JsonNode_ROOT.has("creationDate") && jackson_obj_JsonNode_ROOT.has("targetDate")) {

                //AI processed json TODO is ready here..
                    Todo todo = jackson_obj_MAPPER.treeToValue(jackson_obj_JsonNode_ROOT, Todo.class);


                //System.out.println("\n\n\n -------------------->     " + todo.getId());
                //Thread.sleep(30000);
                //Manual setting-hard coding of fields within ai processed TODO data
                    if (todo.getId() == 0) {
                        todo.setId(new Random().nextInt(1, 999) + 1);
                    }
                    todo.setUid(0); // I have to provide some number because ENTITY MODEL has <<UID>> annotation set to <<@NOTNULL>> and so even db will take care, user need to provide fake input like 0  here--> set MODEL as "NOTNULL"
                    // todo.setAttach(null); // I can avoid this, as this is not mandatory field /null possible
                    todo.setUsername(userEmail);
                    todo.setCreationDate(java.time.LocalDate.now());
                    // other fields are being taken from AI response

                Todo todo_after_saved = todoRepo.save(todo);
                modelMap.put("message", "✅ TODO created via AI (within - elseif branch)!  " +
                                        "\n   ↪ User Input = " + userInput_STRING +
                                        "\n   ↪ todo_after_saved = " + todo_after_saved.toString() +
                                        "\n   ↪ jackson_obj_MAPPER = " + jackson_obj_MAPPER +
                                        "\n   ↪ jackson_obj_JsonNode_ROOT = " + jackson_obj_JsonNode_ROOT  +
                                        "\n   ↪ json_generated_by_AI = " + json_generated_by_AI
                );

            } else {
                modelMap.put("error", "❌ Unrecognized AI response ((FORMAT)) <-------" +
                        "\n   Error related to ---> if condition in (jackson_obj_JsonNode_ROOT.has(action/description/creationDate/targetDate))" +
                        "\n   ↪ User Input = " + userInput_STRING +
                        "\n   ↪ jackson_obj_MAPPER = " + jackson_obj_MAPPER +
                        "\n   ↪ jackson_obj_JsonNode_ROOT = " + jackson_obj_JsonNode_ROOT  +
                        "\n   ↪ json_generated_by_AI = " + json_generated_by_AI
                );
            }

        } catch (Exception e) {
            logger.error("Error handling AI response", e);
            modelMap.put("error", "❌ ❌ ❌ ❌ ❌ ❌ AI failed to process your input with error below:" +
                    "\n   ↪ User Input = " + userInput_STRING +
                    "\n   ↪ Error 1 = " + e.toString() +
                    "\n   ↪ Error 2 = " + e.getMessage()
            );

        }

        List<Todo> todoList = toDoServices.findbyALL();
        modelMap.addAttribute("listMapVar", todoList);

        return "index";
    }


    private String construct_STRING_prompt_for_OTHERS(String input) throws Exception {
        String today = LocalDate.now().toString(); // Ensures today's date is in YYYY-MM-DD

        String prompt_other = "You are a smart API router for a TODO app. Return JSON with the correct action and its parameters.\n\n" +
                        "Supported actions with hints:\n" +
                        "- listAll → list, show, display, all, give\n" +
                        "- jsonCentral  → json, central\n" +
                        "- findByUser (username) → user, username\n" +
                        "- findById (id) → find, fetch, get, id\n" +
                        "- findByUID (uid) → fetch, find, get, uid\n" +
                        "- deleteByUID (uid) → delete, remove, uid\n" +
                        "- deleteById (id) → delete, remove, id\n" +
                        "- updateByUID (uid) → update, change, edit, uid\n" + // not applicable for our design, UID cahnge not allowed
                        "- checkExistByUID (uid) → exists, check, uid\n" +
                        "- search (keyword) → search, keyword, match\n" +
                        "- multipleIds (ids) → many, multiple, ids\n" +
                        "- dateRange (from, to) → date, between, range, from, to\n\n" +
                        "Examples:\n" +
                        "Input: list all todos\nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: give all \nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: show all \nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: all tasks \nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: show my tasks\nOutput: { \"action\": \"listAll\", \"params\": {} }\n" +
                        "Input: delete or remove todo with uid <uid>\nOutput: { \"action\": \"deleteByUID\", \"params\": { \"uid\": <uid> } }\n" +
                        "Input: search or find or get todos for keyword <keyword>\nOutput: { \"action\": \"search\", \"params\": { \"keyword\": \"<keyword>\" } }\n\n" +
                        "Try to infer the best matching action from user input, even if phrased naturally or with synonyms.\n" +
                        "Importantly Consider today's date(YYYY-MM-DD)= " + today + " and accordingly format 'from' and 'to' with strict format of YYYY-MM-DD per user's natural day language input.\n" +
                        "- dateRange to: YYYY-MM-DD (MUST be in this exact format)\n" +
                        "use today's date as reference and whenever user input natural language days(e.g. 'tomorrow', 'next Monday' convert date format to dd/MM/yyyy."+
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
//        System.out.println( "\n\n\n\n\n ------------> " + today);
        String prompt_create = "You are a smart JSON generator for creating a TODO item.\n" +
                "Based on the input, return a valid JSON object with the following fields:\n" +
                "- id: Single Integer number required. Either entered by user, if not entered then between random integer between 1 to 999\n" +
                "- username: \"dummy@example.com\"\n" +
                "- description: string (required)\n" +
                "- creationDate: \"" + today + "\" (strictly count this as today's date)\n" +
                "- targetDate: YYYY-MM-DD (MUST be in this exact format and use relative reference of 'creationDate')\n" +
                "- done: true or false\n\n" +
//                "DO NOT include 'id'.\n" +
                "If the input contains relative dates (e.g., 'tomorrow', 'next Monday'), convert them to YYYY-MM-DD format.\n" +
                "Return ONLY the raw JSON. No explanation or extra text.\n\n" +
                "Examples:\n" +
                "Input: create a todo to buy groceries due next friday\n" +
                "Output: {\n" +
                "  \"id\": \"55\",\n" +
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


    private String routing_ai_JSON_to_correct_endpoint(JsonNode root, ModelMap modelMap) {
        String action = root.path("action").asText();
        JsonNode params = root.path("params");


        switch (action) {

            case "listAll":
                return "forward:/api/todo/listall";
            case "jsonCentral":
                return "forward:/api/todo/jsoncentral";
            case "findByUser":
                return "forward:/api/todo/findByUser?user=" + params.path("username").asText();
            case "findById":
                return "forward:/api/todo/findById?u=" + params.path("id").asInt();
            case "findByUID":
                return "forward:/api/todo/findByUID?u=" + params.path("uid").asInt();
            case "deleteByUID":
                return "forward:/api/todo/deleteByUID?u=" + params.path("uid").asInt();
            case "deleteById":
                return "forward:/api/todo/delByID?u=" + params.path("id").asInt();
            case "updateByUID":
                return "forward:/api/todo/uid/" + params.path("uid").asInt();  // GET NOT SUPPORTED, ignore this for now.... // this is POST
//            case "checkExistByUID":
//                return "forward:/api/todo/api/todo/existByUID?u=" + params.path("uid").asInt();
            case "search":
                return "forward:/api/todo/searchAPI?searchKey=" + params.path("keyword").asText();
            case "multipleIds":
                return "forward:/api/todo/id/" + params.path("ids").asText();
            case "dateRange":
                return "forward:/api/todo/dateRangePicker?fromDate=" + params.path("from").asText()
                        + "&toDate=" + params.path("to").asText();
            default:
                modelMap.put("error", "❌ Unknown action: " + action);

                List<Todo> todoList1 = toDoServices.findbyALL();
                modelMap.addAttribute("listMapVar", todoList1);

                return "index";
        }
    }
}


/*
            -list multiple todo ids 680,681
            -
            -
            -


 */