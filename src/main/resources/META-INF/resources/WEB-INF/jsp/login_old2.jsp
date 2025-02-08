
    <%@ include file="common/header.jspf" %>
    <%@ include file="common/navigation.jspf" %>


        <div class="container">
            <h2> Account Login </h2>
        <b style="color: blue;" >
            <p><p>
            <pre>${authmsg_login2}</pre>
            <p><p><p>
        </b>




                <form method="POST" action="/login2">
                    <label>Email    : </label>
                    <input type="email" placeholder="email..." name="uid_email" value="${prefill_login_email_old2_a}" required> <p>

                    <label>Password : </label>
                    <input type="password" placeholder="password..." name="pass" value="${prefill_login_email_old2_b}" required> <p>
                    <button type="submit" class="cancelbtn">Login</button> <p>

                    <input type="checkbox" checked="checked"> Remember me
                    <button type="button" class="cancelbtn">Cancel</button> <p>

                    Forgot <a href="#">password?</a>

                    <!-- Show error message if login fails -->
                    <p style="color: red;">${param.error ? "Invalid username or password" : ""}</p>
                    <p style="color: green;">${param.logout ? "You have been logged out successfully" : ""}</p>
                </form>






        </div>
    </body>
</html>
