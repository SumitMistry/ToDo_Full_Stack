
    <%@ include file="common/header.jspf" %>
    <%@ include file="common/navigation.jspf" %>


        <div class="container">
            <h2> Account Login </h2>
        <b style="color: blue;" >
            <p><p>
            <pre>${authmsg_login2}</pre>
            <p><p><p>
        </b>


                    <!--  <form method="post" action="/login_perform2">   ...this "login_perform2" need to be adjusted at SpringSecurityConfiguration:: securityFilterChain:: .loginProcessingUrl("/login_perform2") -->
                    <!--  <form method="post" action="/login2"> -->
                <form method="post" action="/login_perform2">   <!-- this ACTION URL goes to POSTMAN login url -->

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />


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



                       <p>

                    <button type="button" class="cancelbtn" onclick="window.location.href='/signup'">Sign-Up</button>


                </form>

        </div>
    </body>
</html>
