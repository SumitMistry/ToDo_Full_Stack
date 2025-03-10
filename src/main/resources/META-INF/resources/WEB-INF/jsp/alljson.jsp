<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


        <div class="container">
            <pre>



                GET all todos in JSON (returning ResponseEntity<List<Todo>>)
                    http://localhost:8080/api/todo/listjson




                GET all todo in JSON(returning  List<Todo> )
                    http://localhost:8080/api/todo/listjson1




                GET Todo By (SINGLE) ID (Returns JSON)
                DISABLED



                GET Todo By (MULTIPLE) ID (Returns JSON)
                    GET
                    http://localhost:8080/api/todo/id/990,1,2,3,51,4


                POST/ INSERT Todo via API (Returns JSON)
                    POST
                    http://localhost:8080/api/todo/insert4
                                     * {
                                     *     "id": 456,
                                     *     "username": "test1@test2.com",
                                     *     "description": "Added from PostMAN",
                                     *     "creationDate": "2025-01-30",
                                     *     "targetDate": "2026-01-30",
                                     *     "done": false
                                     * }


                PUT-Update Todo By UID (Returns JSON)
                    PUT
                    http://localhost:8080/api/todo/uid/97
                             * {
                             *     "id": 123,
                             *     "username": "nyc@njusa.com",
                             *     "description": "Added from Postman edited2",
                             *     "creationDate": "2025-01-30",
                             *     "targetDate": "2056-10-24",
                             *     "done": false
                             * }




                PUT-Update Todo By UID (Returns JSON)
                    PUT
                    http://localhost:8080/api/todo/uid/97
                             * {
                             *     "id": 123,
                             *     "username": "nyc@njusa.com",
                             *     "description": "Added from Postman edited2",
                             *     "creationDate": "2025-01-30",
                             *     "targetDate": "2056-10-24",
                             *     "done": false
                             * }



                Delete (SINGLE) Todo by ID (Returns JSON)
                    DELETE
                    http://localhost:8080/api/todo/id/1



            </pre
        </div>







<%@ include file="common/footer.jspf" %>
