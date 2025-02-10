

<html>
<head>
</head>
<body>
        <div class="container">
            <h2> Account SIGN-UP Setup </h2>
            <p><p>
        <b style="color: blue;" >
            <pre>${authmsg_signup}</pre>
            <pre>${authmsg_signup1}</pre>
            <p><p><p>
        </b>

            <form method="post">
                <label>Email    : </label>          <input type="email" placeholder="email..." name="uid_email" value="${prefill_signup_email}" > <p>

                <label>Password : </label>       <input type="password" placeholder="password..." name="pass"  value="${prefill_signup_pass}"> <p>

                <label>Role : </label>
                    <input type="checkbox" id="roleUser" name="role" value="USER">     <label for="roleUser">User</label><br>
                    <input type="checkbox" id="roleAdmin" name="role" value="ADMIN">     <label for="roleAdmin">Admin</label><br>

                        <p>

                <button type="submit" class="cancelbtn" name="signup">Sign-Up!</button> <p>

                        <p>

                        <input type="checkbox" checked="checked"> Remember me
                        <button type="button" class="cancelbtn"> Cancel</button> <p>
                        Forgot <a href="#"> password? </a>
            </form>







        </div>
    </body>
</html>
