<%@ taglib prefix="test1" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                <p> Enter NEW TODO details:
                <p> ToDo listing page... where ...
                <p> your USER: ${uid}            Pass: ${pass}
            </h1>
                <p> ${listMapVar}   <---- this is not available because we have not put variable =listMapVar into SessionAttributes({x,y,z, listMapVar})
            <hr> <p>
        </div>



        <div class="container">
            <%-- Add todo login here --%>

            <pre> ${id_err_msg2} ${id_err_msg1} </pre>

            <form:form method="post" modelAttribute="todooo">
                <p><label>id : </label>
                    <form:input type="text" path="id" required="required"   />
                <p><label>username : </label>
                    <form:input type="text" path="username" required="required"    />
                <p><label>description : </label>
                    <form:input type="text" path="description" required="required"   />
                <p><label>creationDate : </label>
                    <form:input type="text" path="creationDate" required="required"    />
                <p><label>targetDate : </label>
                    <form:input type="text" path="targetDate" required="required"    />
                <p><label>done : </label>
                    <form:input type="text" path="done" required="required"    />

                <p><input type="submit" value=" + "  class="btn btn-success"> </input>
            </form:form>

            <p>
                Hint1:
            <p>
                Hint2:
<p><p>

        </div>
        <script src="\webjars\bootstrap\5.1.3\js\bootstrap.min.js"> </script>
        <script src="\webjars\jquery\3.6.0\jquery.min.js"> </script>
    </body>
    <html>
