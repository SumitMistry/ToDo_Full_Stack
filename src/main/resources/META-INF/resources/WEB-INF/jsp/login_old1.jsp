<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title> To-Dooh Login Page </title>
        <style>
        Body {
          color: #FFFFFF;
          font-family: Calibri, Helvetica, sans-serif;
          background-color: #000000;
        }
        button {
                background-color: #4CAF50;
                width: 100%;
                color: white;
                padding: 15px;
                margin: 10px 0px;
                border: none;
                cursor: pointer;
                 }
         form {
                border: 3px solid #f1f1f1;
            }
         input[type=text], input[type=password] {
                width: 100%;
                margin: 8px 0;
                padding: 12px 20px;
                display: inline-block;
                border: 2px solid green;
                box-sizing: border-box;
            }
         button:hover {
                opacity: 0.9;
            }
          .cancelbtn {
                width: auto;
                padding: 10px 18px;
                margin: 10px 5px;
            }


         .container {
                padding: 25px;
                background-color: #000000;
            }
        </style>
    </head>


<body>


    <div class="container">
        <center> <h1> To-Dooh Login </h1> </center>


                <b style="color: blue;" >
                    <p><p>
                    <pre>${authmsg_login1}</pre>
                    <p><p><p>
                </b>




            <form method="post" action="/login_perform">   <!--This brings to this listall page once click submitted. -->

                <div class="container">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <label>Email : </label>
                    <input type="text" placeholder="Enter Email" name="uid_email" value="${prefill_login_email_old1_a}" >

                    <label>Password : </label>
                    <input type="password" placeholder="Enter Password" name="pass" value="${prefill_login_email_old1_b}" >

                          <button type="submit" class="cancelbtn">Login</button> <p>

                                        <input type="checkbox" checked="checked"> Remember me
                                        <button type="button" class="cancelbtn">Cancel</button> <p>

                           Forgot <a href="#">password?</a>

                                        <!-- Show error message if login fails -->
                                        <p style="color: red;">${param.error ? "Invalid username or password" : ""}</p>
                                        <p style="color: green;">${param.logout ? "You have been logged out successfully" : ""}</p>
                </div>
            </form>



    </div>
</body>

</html>