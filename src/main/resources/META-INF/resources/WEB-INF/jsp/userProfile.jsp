<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


        <div class="container">

        <div>
          <hr>
        </div>

              <pre> ${profile_a} </pre>  <p>
              <pre> ${profile_b} </pre>  <p>


             <form:form method="post" modelAttribute="userProfile0_obj_modelAttribute">

                    <fieldset class="mb-3">
                    <form:label path="uid"> uid : </form:label>
                    <form:input type="text" path="uid" required="required" disabled="true"  />
                    <form:hidden path="uid" />
                    <form:errors path="uid"  cssClass="text-warning"    />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="username"> username : </form:label>
                    <form:input type="text" path="username"  disabled="true"    />
                    <form:hidden path="username" />
                    <form:errors path="username"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="f_name"> First Name : </form:label>
                    <form:input type="text" path="f_name" required="required"     />
                    <form:errors path="f_name"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="l_name"> Last Name : </form:label>
                    <form:input type="text" path="l_name" required="required"    />
                    <form:errors path="l_name"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="birth_date"> Birthdate : </form:label>
                    <form:input type="text" path="birth_date" required="required"   />
                    <form:errors path="birth_date"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="city"> City : </form:label>
                    <form:input type="text" path="city"      />
                    <form:errors path="city"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="phone"> Phone : </form:label>
                    <form:input type="text" path="phone"      />
                    <form:errors path="phone"  cssClass="text-warning"  />
                    </fieldset>




                    <label> Sex : </label>

                        <input type="radio" id="sexMale" name="sex"  value="MALE"          ${up1sex == 'MALE' ? 'checked' : ''}  required  >     <label for="sexMale">MALE   </label>
                        <input type="radio" id="sexFemale" name="sex" value="FEMALE"       ${up1sex == 'FEMALE' ? 'checked' : ''}  required>           <label for="sexFemale">FEMALE</label>
                        <br> <br>



                    <fieldset class="mb-3">
                    <form:label path="userAuth.user_role"> Roles : </form:label>
                    <form:input type="text" path="userAuth.user_role" value="${userProfile0_obj_modelAttribute.userAuth.userRoleAsString}"  disabled="true"   />
                    <form:hidden path="userAuth.user_role"  value="${userProfile0_obj_modelAttribute.userAuth.userRoleAsString}" />
                    <form:errors path="userAuth.user_role"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="userAuth.user_role"> Roles : </form:label>
                    <form:input type="text" path="userAuth.user_role" value="${userProfile0_obj_modelAttribute.userAuth.user_role}"  disabled="true"   />
                    <form:hidden path="userAuth.user_role"  value="${userProfile0_obj_modelAttribute.userAuth.user_role}" />
                    <form:errors path="userAuth.user_role"  cssClass="text-warning"  />
                    </fieldset>

                    <fieldset class="mb-3">
                    <form:label path="userAuth.password_raw"> Raw Password: </form:label>
                    <form:input type="text" path="userAuth.created_date" value="${userProfile0_obj_modelAttribute.userAuth.password_raw}" disabled="true"      />
                    <form:hidden path="userAuth.password_raw"  value="${userProfile0_obj_modelAttribute.userAuth.password_raw}" />
                    <form:errors path="userAuth.password_raw"  cssClass="text-warning"  />
                    </fieldset>


                    <fieldset class="mb-3">
                    <form:label path="userAuth.password_encoded"> Encoded Password: </form:label>
                    <form:input type="text" path="userAuth.password_encoded" value="${userProfile0_obj_modelAttribute.userAuth.password_encoded}"  disabled="true"   size="70"   />
                    <form:hidden path="userAuth.password_encoded"  value="${userProfile0_obj_modelAttribute.userAuth.password_encoded}" />
                    <form:errors path="userAuth.password_encoded"  cssClass="text-warning"  />
                    </fieldset>



                    <fieldset class="mb-3">
                    <form:label path="userAuth.created_date"> Created Date: </form:label>
                    <form:input type="text" path="userAuth.created_date" value="${userProfile0_obj_modelAttribute.userAuth.created_date}" required="required" disabled="true"    size="30"   />
                    <form:hidden path="userAuth.created_date"  value="${userProfile0_obj_modelAttribute.userAuth.created_date}" />
                    <form:errors path="userAuth.created_date"  cssClass="text-warning"  />
                    </fieldset>



     My Todos :     ${profile_all_todos}  <p>


                <p><input type="submit" value="   Update   "  class="btn btn-success"> </input>

            </form:form>
            

                    
                    
              <pre> ${profile3} </pre>  <p>
              <pre> ${profile4} </pre>  <p>
              <pre> ${profile5} </pre>  <p>
<p><p>

        </div>


<%@ include file="common/footer.jspf" %>
