<%@ taglib prefix="test1" uri="http://java.sun.com/jsp/jstl/core" %>

META-INF\resources\webjars\bootstrap\5.1.3\css\bootstrap.min.css
META-INF\resources\webjars\bootstrap\5.1.3\js\bootstrap.min.js
META-INF\resources\webjars\jquery\3.6.0\jquery.min.js


<html>
    <head>
        <link href="\webjars\bootstrap\5.1.3\css\bootstrap.min.css" rel="stylesheet">
        <link href="\webjars\bootstrap-datepicker\1.9.0\css\bootstrap-datepicker3.standalone.min.css" rel="stylesheet"> </script>
        <title> List: Todo    </title>
    </head>
    <body>
        <div>
            <h1>
                <p> Enter NEW TODO details:
                <p> ToDo listing page... where ...
                <p> your USER: ${uid_email}            Pass: ${pass}
            </h1>
                <p> ${listMapVar}   <---- this is not available because we have not put variable =listMapVar into SessionAttributes({x,y,z, listMapVar})
            <hr> <p>
        </div>



        <div class="container">
            <%-- Add todo login here --%>

            <pre> ${id_err_msg2} ${id_err_msg1} </pre>

            <form method = "post">
                <label>id : </label>
                <input type="text" name="id" >
                <label>username : </label>
                <input type="text" name="username" required>
                <label>description : </label>
                <input type="text" name="description" required>
                <label>creationDate : </label>
                <input type="text" name="creationDate" required>
                <label>targetDate : </label>
                <input type="text" name="targetDate" required>
                <label>done : </label>
                <input type="text" name="done" required>


               <input type="submit" value=" + "  class="btn btn-success"> </input>
            </form>

            <p>
                Hint1:
            <p>
                Hint2:
<p><p>

        </div>
        <!--  Order matters....Include jQuery before Bootstrap otherwise Bootstrap wont work-->
                <script src="\webjars\jquery\3.6.0\jquery.min.js"> </script>
                <script src="\webjars\bootstrap-datepicker\1.9.0\js\bootstrap-datepicker.min.js"> </script>
                <script src="\webjars\bootstrap\5.1.3\js\bootstrap.min.js"> </script>


        <script type="text/javascript">
           $('#targetDate').datepicker({
               format: 'dd-mm-yyyy'
           });
           </script>

    </body>
    <html>
