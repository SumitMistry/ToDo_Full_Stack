
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>



        <div class="container">
                        <a href="hardcode1" class="btn btn-primary btn-block"> Append 3 records: /hardcode1 </a> --> change value true-->false <p>
                              @Transactional(readOnly = true, propagation = Propagation.) -->  I kept this hard coded data as READ ONLY so will not get injected to DB
                        <hr>
            <table class="table">
                <thead>

                    <tr>
                        <th> uid </th>
                        <th> id </th>
                        <th> username </th>
                        <th> description </th>
                        <th> creationDate </th>
                        <th> targetDate </th>
                        <th> done </th>

                        <th> Insert<br>(Validated) </th>
                        <th> Update </th>
                        <th> DeleteBy<br>ID(?) </th>
                        <th> DeleteBy<br>UID(?) </th>
                        <th> Attach </th>
                        <th> findBy<br>ID(?) </th>
                        <th> findBy<br>UID(?) </th>
                        <th> findBy<br>User(?) </th>

                    </tr>
                </thead>
                <tbody>
                    <test1:forEach items="${listMapVar}" var="eentry">
                        <tr>
                            <td>  ${eentry.uid}  </td>
                            <td>  ${eentry.id}  </td>
                            <td>  ${eentry.username}   </td>
                            <td>  ${eentry.description}   </td>
                            <td>  ${eentry.creationDate}   </td>
                            <td>  ${eentry.targetDate}   </td>
                            <td>  ${eentry.done}   </td>
                             <%--  <td> <a href="upload?u=${eentry.uid}" class="btn btn-warning"> not working </a> id:${eentry.uid} </td>   --%>

                            <td>  <a href="/api/todo/insert3" class="btn btn-success"> &#x271A; </a> i++ </td>
                            <td>  <a href="/api/todo/update?u=${eentry.uid}" class="btn btn-info"> # </a> u=${eentry.uid} </td>
                            <td>  <a href="/api/todo/delByID?u=${eentry.id}" class="btn btn-danger"> &#x2718 </a> u=${eentry.id} </td>
                            <td>  <a href="/api/todo/deleteByUid?u=${eentry.uid}" class="btn btn-danger"> &#x2718;</a> u=${eentry.uid} </td>
                            <td>  <a href="/api/todo/upload?u=${eentry.uid}" class="btn btn-primary btn-success">  &#x1F517 </a> u=${eentry.uid} </td>
                            <td>  <a href="/api/todo/findById?u=${eentry.id}" class="btn btn-warning"> &#x2754; </a> u=${eentry.id} </td>
                            <td>  <a href="/api/todo/findByUID?u=${eentry.uid}" class="btn btn-warning"> &#x2754; </a> u=${eentry.uid} </td>
                            <td>  <a href="/api/todo/findByUser?user=${eentry.username}" class="btn btn-warning"> &#x2754; </a> user=${eentry.username} </td>


                        </tr>
                    </test1:forEach>
                </tbody>
            </table>
            </div>

            <div class="container">

                    1.SpringDataJPA - INSERT: <p>
                    <a href="/api/todo/insert3" class="btn btn-success"> Insert+Validation: /insert3 </a> <p>

            <hr>
                    2.SpringDataJPA - FindByUID: <p>
                    <a href="/api/todo/findByUID?u=1" class="btn btn-success">find?u=1 </a> <p>
            <hr>
                    @Transactional(readOnly = true) // , propagation = Propagation.)
                    / I kept this hard coded data as READONLY so will not get injected to DB
                    public String sprData_jpa_hard_code_data(ModelMap modelMap){ <p>
                    1.1<a href="/api/todo/hardcode1" class="btn btn-success"> Append 4 records: /hardcode1 </a> <p>
            <hr>
                    3.Auto Validation: <a href="api/todo/insert" class="btn btn-warning"> X---Insert1---X </a> <p>
            <hr>
                    4.Manual: <a href="/api/todo/insert2" class="btn btn-warning"> X---Insert2-deleted---X </a> <p>
            <hr>
                       <a href="/" class="btn btn-primary">     /     </a>
                       <a href="list" class="btn btn-primary">    /list     </a>
                       <a href="/actuator" class="btn btn-primary">    /actuator  </a>
                       <a href="/actuator/health" class="btn btn-primary">   /actuator/health </a>
                       <a href="health" class="btn btn-primary">      /HealthCheck-Bluey      </a>
                       <a href="login" class="btn btn-primary">       /login     </a>
                       <a href="login1" class="btn btn-primary">       /login1     </a>
                       <a href="login2" class="btn btn-primary">       /login2     </a>
                       <a href="/welcome1" class="btn btn-primary">       /welcome1     </a> <p>

        </div>

        <hr>asList: "${listMapVar}"<hr>

<%@ include file="common/footer.jspf" %>