
    <%@ include file="common/header.jspf" %>
    <%@ include file="common/navigation.jspf" %>

        <div class="container">
            <h1> Welcome to Login Page </h1>
            <p>
            <pre>${authmsg}<pre>

            <form method = "post">
                <label>Email : </label>
                <input type="text" name="uid_email" value="${prefill_login_old_get1}" >
                <label>Password : </label>
                <input type="text" name="pass">
                <input type="submit">
                        <p>
                        <p>
                        <input type="checkbox" checked="checked"> Remember me
                        <button type="button" class="cancelbtn"> Cancel</button>
                        Forgot <a href="#"> password? </a>
            </form>


        </div>
    </body>
</html>
