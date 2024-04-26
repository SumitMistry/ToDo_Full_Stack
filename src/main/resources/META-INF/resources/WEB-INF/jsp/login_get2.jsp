<html>
    <head>
        <title> login page </title>
    </head>
    <body>
        <div class="container">
            <h1> Welcome to Login Page </h1>
            <p>
            <pre>    ${authmsg}         <pre>

            <form method = "post">
                <label>UID : </label>
                <input type="text" name="uid">
                <label>Password : </label>
                <input type="text" name="pass">
                <input type="submit">
            </form>

            <p>
                Hint1: uid must have @
            <p>
                Hint2: pass must be 4 length
        </div>
    </body>
</html>
