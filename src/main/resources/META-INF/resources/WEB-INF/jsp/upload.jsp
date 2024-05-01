<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

        <div>
            <p> ${listMapVar}   "${listMapVar}"<---- this is not available because we have not put variable =listMapVar into SessionAttributes({x,y,z, listMapVar})
            <hr> <p>
        </div>



        <div class="container">

            <pre> ${id_err_msg2} ${id_err_msg1} </pre>


            <form:form method="get"  enctype="multipart/form-data"  modelAttribute="todo55">
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
            </form:form>


            <%-- commented ...<form:form method="post" action="upload"  enctype="multipart/form-data"  modelAttribute="fileUpload_holder">   --%>
            <%-- commented...<form:form method="post" action="upload/${todoId}" enctype="multipart/form-data"  modelAttribute="fileUpload_holder">   --%>
            <form:form method="post"                                enctype="multipart/form-data"  modelAttribute="fileUpload_holder">

                    <fieldset class="mb-3">
                      <form:label path="multipartFile"> Choose file:: </form:label>
                      <form:input type="file"  path="multipartFile" form:bind="*{multipartFile}" id="multipartFile" name="multipartFile"  required="required"  />
                    </fieldset>

                <p><input type="submit" value=" Attach "  class="btn btn-success"> </input>

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