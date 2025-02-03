
    <%@ include file="common/header.jspf" %>
    <%@ include file="common/navigation.jspf" %>

        <div class="container">
            <h2> Account SIGN-UP Setup </h2>
            <p>
            <pre>${authmsg_signup}<pre>
            <p>
            <pre>${authmsg_signup1}<pre>

            <form method = "post">
                <label>Email : </label>          <input type="email" placeholder="email..." name="uid_email" value="${prefill_signup_email}" >
                <label>Password : </label>       <input type="password" placeholder="password..." name="pass"  value="${prefill_signup_pass}">
                <button type="submit" class="cancelbtn" name="signup">Sign-Up!</button>

                        <p>
                        <input type="checkbox" checked="checked"> Remember me
                        <button type="button" class="cancelbtn"> Cancel</button>
                        Forgot <a href="#"> password? </a>
            </form>







        </div>
    </body>
</html>
