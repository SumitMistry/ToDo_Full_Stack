<%@ taglib prefix="test1" uri="http://java.sun.com/jsp/jstl/core" %>

META-INF\resources\webjars\bootstrap\5.1.3\css\bootstrap.min.css
META-INF\resources\webjars\bootstrap\5.1.3\js\bootstrap.min.js
META-INF\resources\webjars\jquery\3.6.0\jquery.min.js
<html>
    <head>
        <link href="\webjars\bootstrap\5.1.3\css\bootstrap.min.css" rel="stylesheet">
        <title> List: Todo    </title>
    </head>
    <body>
        <div>
            <h3>
                <p> ToDo listing page... where ...
                <p>your USER: ${uid_email}            Pass: ${pass}
            </h3>
            <hr>
        </div>


        <div>
            "${listMapVar}"

        </div>   <hr>


        <div class="container">

                       <a href="list" class="btn btn-primary btn-block">       /list      </a>
                       <a href="/actuator" class="btn btn-primary btn-block">       /actuator      </a>
                       <a href="/actuator/health" class="btn btn-primary btn-block">       /actuator/health      </a>
                       <a href="health" class="btn btn-primary btn-block">      /HealthCheck-Bluey      </a> <p>
                       <hr>
                        <a href="login" class="btn btn-primary btn-block">       /login     </a>
                        <a href="login1" class="btn btn-primary btn-block">       /login1     </a>
                        <a href="login2" class="btn btn-primary btn-block">       /login2     </a>
                        <a href="/welcome1" class="btn btn-primary btn-block">       /welcome1     </a> <p>
                        <hr>
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

                        <th> Attach </th>
                        <th> Delete </th>
                        <th> Update  </th>
                        <th> Find </th>
                        <th> Insert(Validate) </th>

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

                            <td>  <a href="upload?i=${eentry.id}" class="btn btn-primary btn-block">  🔗 </a> i:${eentry.id} </td>
                            <td>  <a href="delete?i=${eentry.id}" class="btn btn-danger"> x </a> i:${eentry.id} </td>
                            <td>  <a href="update?i=${eentry.id}" class="btn btn-info"> # </a> i:${eentry.id} </td>
                            <td>  <a href="find?i=${eentry.id}" class="btn btn-primary btn-block"> ? </a> i:${eentry.id} </td>
                            <td>  <a href="insert3" class="btn btn-success"> + </a> i++ </td>
                        </tr>
                    </test1:forEach>
                </tbody>
            </table>

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



        </div>
        <script src="\webjars\bootstrap\5.1.3\js\bootstrap.min.js"> </script>
        <script src="\webjars\jquery\3.6.0\jquery.min.js"> </script>
    </body>
    <html>
