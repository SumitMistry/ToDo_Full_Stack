<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


        <div class="container">

        <div>
          <hr>
        </div>


             <form:form method="post" modelAttribute="userProfile_obj_modelAttribute">

                    <fieldset class="mb-3">
                    <form:label path="uid"> uid : </form:label>
                                                <!-- <form:input type="text" path="uid" required="required"   />   -->
                    <form:input type="text" path="uid" required="required"   />
                    <form:errors path="uid"  cssClass="text-warning"    />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="username"> username : </form:label>
                    <form:input type="text" path="username" required="required"   />
                    <form:errors path="username"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="f_name"> First Name : </form:label>
                    <form:input type="text" path="f_name" required="required"   />
                    <form:errors path="f_name"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="l_name"> Last Name : </form:label>
                    <form:input type="text" path="l_name" required="required"   />
                    <form:errors path="l_name"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="city"> City : </form:label>
                    <form:input type="text" path="city" required="required"   />
                    <form:errors path="city"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="phone"> Phone : </form:label>
                    <form:input type="text" path="phone" required="required"   />
                    <form:errors path="phone"  cssClass="text-warning"  />
                    </fieldset>


                <p><input type="submit" value="   Update   "  class="btn btn-success"> </input>

            </form:form>

              <pre> ${profile1} </pre>  <p>
              <pre> ${profile2} </pre>  <p>
<p><p>

        </div>


<%@ include file="common/footer.jspf" %>
