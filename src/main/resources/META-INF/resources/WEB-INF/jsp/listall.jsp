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
                <p>your USER: ${uid}            Pass: ${pass}
            </h1>
            <hr>
        </div>


        <div>
            "${listMapVar}"
        </div>        <p>        <p>


        <div class="container">
            <table class="table">
                <thead>
                    <tr>
                        <th>  id  </th>
                        <th>  username  </th>
                        <th>  description  </th>
                        <th>  creationDate  </th>
                        <th>  targetDate  </th>
                        <th>  done  </th>
                    </tr>
                </thead>
                <tbody>
                    <test1:forEach items="${listMapVar}" var="eentry">
                        <tr>
                            <th>  ${eentry.id}  </th>
                            <th>  ${eentry.username}   </th>
                            <th>  ${eentry.description}   </th>
                            <th>  ${eentry.creationDate}   </th>
                            <th>  ${eentry.targetDate}   </th>
                            <th>  ${eentry.done}   </th>
                        </tr>
                    </test1:forEach>
                </tbody>
            </table>
            <a href="insert" class="btn btn-success"> Insert </a>
            <a href="insert2" class="btn btn-success"> Insert2 </a>
        </div>
        <script src="\webjars\bootstrap\5.1.3\js\bootstrap.min.js"> </script>
        <script src="\webjars\jquery\3.6.0\jquery.min.js"> </script>
    </body>
    <html>
