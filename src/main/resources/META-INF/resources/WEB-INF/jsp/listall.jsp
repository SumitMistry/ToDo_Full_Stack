<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>



        <div class="container">
                        <a href="hardcode1" class="btn btn-primary btn-block"> Append 3 records: /hardcode1 </a> --> change value true-->false <p>
                              @Transactional(readOnly = true, propagation = Propagation.) -->  I kept this hard coded data as READ ONLY so will not get injected to DB
                        <hr>
            <table class="table">
                <thead>

                    <tr>

                        <th> id </th>
                        <th> username </th>
                        <th> description </th>
                        <th> creationDate </th>
                        <th> targetDate </th>
                        <th> done </th>

                        <th> Insert<br>(Validated) </th>
                        <th> Update  </th>
                        <th> Delete </th>
                        <th> Attach </th>
                        <th> findBy<br>Id(?) </th>
                        <th> findBy<br>User(?) </th>



                    </tr>
                </thead>
                <tbody>
                    <test1:forEach items="${listMapVar}" var="eentry">
                        <tr>

                            <td>  ${eentry.id}  </td>
                            <td>  ${eentry.username}   </td>
                            <td>  ${eentry.description}   </td>
                            <td>  ${eentry.creationDate}   </td>
                            <td>  ${eentry.targetDate}   </td>
                            <td>  ${eentry.done}   </td>
                             <%--  <td> <a href="upload?i=${eentry.id}" class="btn btn-warning"> not working </a> id:${eentry.id} </td>   --%>

                            <td>  <a href="insert3" class="btn btn-success"> + </a> i++ </td>
                            <td>  <a href="update?i=${eentry.id}" class="btn btn-info"> # </a> i:${eentry.id} </td>
                            <td>  <a href="delete?i=${eentry.id}" class="btn btn-danger"> x </a> i:${eentry.id} </td>
                            <td>  <a href="upload?i=${eentry.id}" class="btn btn-primary btn-block">  🔗 </a> i:${eentry.id} </td>
                            <td>  <a href="find?i=${eentry.id}" class="btn btn-warning"> ? </a> i:${eentry.id} </td>
                            <td>  <a href="user?i=${eentry.username}" class="btn btn-warning"> ? </a> i:${eentry.username} </td>


                        </tr>
                    </test1:forEach>
                </tbody>
            </table>
            </div>

            <div class="container">

                    1.SpringDataJPA - INSERT: <p>
                    <a href="insert3" class="btn btn-success"> Insert+Validation: /insert3 </a> <p>
            <hr>
                    2.SpringDataJPA - FIND: <p>
                    <a href="find?i=1" class="btn btn-success">find?i=1 </a> <p>
            <hr>
                    @Transactional(readOnly = true) // , propagation = Propagation.)
                    / I kept this hard coded data as READONLY so will not get injected to DB
                    public String sprData_jpa_hard_code_data(ModelMap modelMap){ <p>
                    1.1<a href="hardcode1" class="btn btn-success"> Append 3 records: /hardcode1 </a> <p>
            <hr>
                    3.Auto Validation: <a href="insert" class="btn btn-warning"> X---Insert1---X </a> <p>
            <hr>
                    4.Manual: <a href="insert2" class="btn btn-warning"> X---Insert2-deleted---X </a> <p>
            <hr>
                       <a href="/" class="btn btn-primary">     /     </a>
                       <a href="list" class="btn btn-primary">    /list     </a>
                       <a href="/actuator" class="btn btn-primary">    /actuator  </a>
                       <a href="/actuator/health" class="btn btn-primary">   /actuator/health </a>
                       <a href="health" class="btn btn-primary">      /HealthCheck-Bluey      </a>
                        <a href="login" class="btn btn-primary">       /login     </a>
                        <a href="login1" class="btn btn-primary">       /login1     </a>
                        <a href="login2" class="btn btn-primary">       /login2     </a>
                        <a href="welcome1" class="btn btn-primary">       /welcome1     </a> <p>

        </div>

        <hr>asList: "${listMapVar}"<hr>

<%@ include file="common/footer.jspf" %>