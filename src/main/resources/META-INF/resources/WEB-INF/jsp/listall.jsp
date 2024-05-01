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
            <h1>
                <p> ToDo listing page... where ...
                <p>your USER: ${uid_email}            Pass: ${pass}
            </h1>
            <hr>
        </div>


        <div>
            "${listMapVar}"
        </div>        <p>        <p>


        <div class="container">

         <hr>
                            <a href="list" class="btn btn-success">  /list  </a> <p>
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
                        <th> Attachment </th>
                        <th> Attachment </th>
                        <th> Delete </th>
                        <th> Update (No Validation)</th>

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
                            <td> <a href="upload?id=${eentry.id}" class="btn btn-warning"> not working </a> id:${eentry.id} </td>
                            <td> <a href="upload/${eentry.id}" class="btn btn-warning"> 🔗 </a> id:${eentry.id} </td>
                            <td>  <a href="delete?id=${eentry.id}" class="btn btn-warning"> x </a> id:${eentry.id} </td>
                            <td>  <a href="update?id=${eentry.id}" class="btn btn-success"> # </a> id:${eentry.id} </td>
                        </tr>
                    </test1:forEach>
                </tbody>
            </table>

                    1.SpringDataJPA - INSERT: <p>
                    <a href="insert3" class="btn btn-success"> Insert+Validation: /insert3 </a> <p>
            <hr>
                    2.SpringDataJPA - FIND: <p>
                    <a href="find?id=1" class="btn btn-success">find?id=1 </a> <p>
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
