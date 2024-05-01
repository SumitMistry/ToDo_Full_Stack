<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

        <div>
            <h1>
                <p> Enter NEW TODO details:
                <p> ToDo listing page... where ...
                <p> your USER: ${uid_email}            Pass: ${pass}
            </h1>
                <p> ${listMapVar}   <---- this is not available because we have not put variable =listMapVar into SessionAttributes({x,y,z, listMapVar})
            <hr> <p>
        </div>



        <div class="container">

            <pre> ${id_err_msg2} ${id_err_msg1} </pre>

        <%--  form:form method="post" action="/api" modelAttribute="todo_obj_spring_data_jpa2" --%>
            <form:form method="post" modelAttribute="todo_obj_spring_data_jpa2">

                    <fieldset class="mb-3">
                    <form:label path="id"> id : </form:label>
                    <form:input type="text" path="id" required="required"   />
                    <form:errors path="id"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="username"> username : </form:label>
                    <form:input type="text" path="username" required="required"   />
                    <form:errors path="username"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="description"> description : </form:label>
                    <form:input type="text" path="description" required="required"   />
                    <form:errors path="description"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="creationDate"> creationDate : </form:label>
                    <form:input type="text" path="creationDate" required="required"   />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="targetDate"> targetDate : </form:label>
                    <form:input type="text" path="targetDate" required="required"   />
                    <form:errors path="targetDate"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="done"> done : </form:label>
                    <form:input type="text" path="done" required="required"   />
                    <form:errors path="done"  cssClass="text-warning"  />
                    </fieldset>


                <p><input type="submit" value=" + "  class="btn btn-success"> </input>

            </form:form>

            <p>
                Hint1:
            <p>
                Hint2:

        </div>


        <script type="text/javascript">
           $('#targetDate').datepicker({
               format: 'dd-mm-yyyy'
           });

           $('#creationDate').datepicker({
                format: 'dd-mm-yyyy'
           });
        </script>

<%@ include file="common/footer.jspf" %>