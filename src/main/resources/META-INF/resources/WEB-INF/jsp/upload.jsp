<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

        <div>
            <p> ${listMapVar}   "${listMapVar}"<---- this is not available because we have not put variable =listMapVar into SessionAttributes({x,y,z, listMapVar})
            <hr> <p>
        </div>



        <div class="container">

            <pre> ${id_err_msg2} ${id_err_msg1} </pre>
<p>
PART-A : (Form existing UID record data / GET)
<p>

            <form:form method="get"  enctype="multipart/form-data"  modelAttribute="todo55">
                    <fieldset class="mb-3">
                    <form:label path="uid"> uid : </form:label>
                    <form:input type="text" path="uid" required="required" disabled="true" />
                    <form:hidden path="uid" />
                    <form:errors path="uid"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="id"> id : </form:label>
                    <form:input type="text" path="id" required="required" disabled="true" />
                    <form:hidden path="id" />
                    <form:errors path="id"  cssClass="text-warning"/>
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="username"> username : </form:label>
                    <form:input type="text" path="username" required="required"  disabled="true" />
                    <form:hidden path="username" />
                    <form:errors path="username"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="description"> description : </form:label>
                    <form:input type="text" path="description" required="required" disabled="true"  />
                    <form:hidden path="description" />
                    <form:errors path="description"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="creationDate"> creationDate : </form:label>
                    <form:input type="text" path="creationDate" required="required" disabled="true"  />
                    <form:hidden path="creationDate" />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="targetDate"> targetDate : </form:label>
                    <form:input type="text" path="targetDate" required="required" disabled="true" />
                    <form:errors path="targetDate"  cssClass="text-warning"  />
                    <form:hidden path="targetDate" />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="done"> done : </form:label>
                    <form:input type="text" path="done" required="required" disabled="true" />
                    <form:hidden path="done" />
                    <form:errors path="done"  cssClass="text-warning"  />
                    </fieldset>
            </form:form>


            <%-- commented ...<form:form method="post" action="api/todo/upload"  enctype="multipart/form-data"  modelAttribute="fileUpload_holder">   --%>
            <%-- commented...<form:form method="post" action="api/todo/upload/${todoId}" enctype="multipart/form-data"  modelAttribute="fileUpload_holder">   --%>
<p>
PART-B : (Upload section / POST)
<p>

            <form:form method="post"          enctype="multipart/form-data"  modelAttribute="fileUpload_holder">

                    <fieldset class="mb-3">
                      <form:label path="multipartFile"> Choose file:: </form:label>
                      <form:input type="file"  path="multipartFile" form:bind="*{multipartFile}" id="multipartFile" name="multipartFile"  required="required"  />
                    </fieldset>

                <p><input type="submit" value=" Attach "  class="btn btn-success"> </input>

            </form:form>
        </div>




<%@ include file="common/footer.jspf" %>