
    <%@ include file="common/header.jspf" %>
    <%@ include file="common/navigation.jspf" %>


        <div class="container">
            <h2> Account Login </h2>
        <b style="color: blue;" >
            <p><p>
            <pre>${authmsg_login2}</pre>
            <p><p><p>
        </b>

            <form method = "post">
                <label>Email    : </label>       <input type="email" placeholder="email..." name="uid_email" value="${prefill_login_email_old2}" > <p>
                <label>Password : </label>       <input type="password" placeholder="password..." name="pass"  value="${prefill_login_pass_old2}"> <p>
                <button type="submit" class="cancelbtn" name="signup">Sign-Up!</button> <p>

                        <p>
                        <input type="checkbox" checked="checked"> Remember me
                        <button type="button" class="cancelbtn"> Cancel</button> <p>
                        Forgot <a href="#"> password? </a>
            </form>







        </div>
    </body>
</html>
