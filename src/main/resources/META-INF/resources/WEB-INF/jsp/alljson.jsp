5️⃣ POST/ INSERT Todo via API (Returns JSON)
POST http://localhost:8080/api/todo/insert4
                     * {
                     *     "id": 456,
                     *     "username": "test1@test2.com",
                     *     "description": "Added from PostMAN",
                     *     "creationDate": "2025-01-30",
                     *     "targetDate": "2026-01-30",
                     *     "done": false
                     * }


7️⃣ PUT-Update Todo By UID (Returns JSON)
 PUT http://localhost:8080/api/todo/uid/97
             * {
             *     "id": 123,
             *     "username": "nyc@njusa.com",
             *     "description": "Added from Postman edited2",
             *     "creationDate": "2025-01-30",
             *     "targetDate": "2056-10-24",
             *     "done": false
             * }




7️⃣ PUT-Update Todo By UID (Returns JSON)
PUT http://localhost:8080/api/todo/uid/97
             * {
             *     "id": 123,
             *     "username": "nyc@njusa.com",
             *     "description": "Added from Postman edited2",
             *     "creationDate": "2025-01-30",
             *     "targetDate": "2056-10-24",
             *     "done": false
             * }


6️⃣ Get Todo By (SINGLE) ID (Returns JSON)
DISABLED



6️⃣ Get Todo By (MULTIPLE) ID (Returns JSON)
GET http://localhost:8080/api/todo/id/990,1,2,3,51,4


8️⃣ Delete (SINGLE) Todo by UID (Returns JSON)
DELETE http://localhost:8080/api/todo/uid/1



GET all todos in JSON (returning ResponseEntity<List<Todo>>)
http://localhost:8080/api/todo/listjson

GET all todo in JSON(returning  List<Todo> )
http://localhost:8080/api/todo/listjson1