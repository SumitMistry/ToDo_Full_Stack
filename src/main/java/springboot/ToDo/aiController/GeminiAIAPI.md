
GEMINI API call check link:
https://console.cloud.google.com/apis/api/generativelanguage.googleapis.com/credentials?project=gen-lang-client-0399073710&inv=1&invt=Ab0JAw



# 🛑 Your current problem:

    Gemini API does NOT accept { "prompt": "..." } structure.

    That’s why it's saying:

        ❌ Invalid JSON payload received. Unknown name "prompt": Cannot find field.

------------------------------------------------------------------------
# In Gemini API, the expected payload is always in a specific structure like:
  ````
  {
    "contents": [
      {
        "parts": [
          {
            "text": "your text prompt here"
          }
        ]
      }
    ]
  }
  ````

------------------------------------------------------------------------
# ✅ Only contents -> parts -> text is valid.
# 📢 Conclusion:

    You CANNOT send just { "prompt": "..." }.
    You are forced to send the nested structure:
        contents > parts > text


------------------------------------------------------------------------

# 💬 Why you cannot avoid contents and parts:

Because Gemini API (Google Generative API) requires payload schema based on their public OpenAPI spec.

They expect this hierarchy. You cannot bypass it.

    "contents" is a list (array) of messages.

    "parts" is a list (array) inside each message.

    "text" is inside "parts".

No prompt field supported directly.

------------------------------------------------------------------------

# 🛠 So your corrected request must send:
  
  ````
  {
    "contents": [
      {
        "parts": [
          { "text": "Show me all my todos" }
        ]
      }
    ]
  }
  ````

You must send { "contents": [ { "parts": [ { "text": "..." } ] } ] }	Correct structure

------------------------------------------------------------------------

# 🛠 Final result

  ````
  Map<String, Object> body = Map.of(
      "contents", List.of(
          Map.of(
              "parts", List.of(
                  Map.of("text", "Show me all my todos")
              )
          )
      )
  );

  ````

------------------------------------------------------------------------

# Must adjust your Java code to build this format	as below:
✅ Java Map<String, Object> version of above JSON:

    Map<String, Object> textPart = new HashMap<>();
    textPart.put("text", "Show me all my todos");

    List<Map<String, Object>> partsList = new ArrayList<>();
    partsList.add(textPart);

    Map<String, Object> contentItem = new HashMap<>();
    contentItem.put("parts", partsList);

    List<Map<String, Object>> contentsList = new ArrayList<>();
    contentsList.add(contentItem);

    Map<String, Object> body = new HashMap<>();
    body.put("contents", contentsList);



