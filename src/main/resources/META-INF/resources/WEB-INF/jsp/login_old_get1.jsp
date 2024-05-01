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
        <pre>${authmsg}</pre>

            <form method="post">
                <div class="container">
                    <label>Email : </label>
                    <input type="text" placeholder="Enter Email" name="uid_email" value="${prefill_login_old_get1}" >
                    <label>Password : </label>
                    <input type="password" placeholder="Enter Password" name="pass" >
                    <button type="submit">Login</button>
                    <input type="checkbox" checked="checked"> Remember me
                    <button type="button" class="cancelbtn"> Cancel</button>
                    Forgot <a href="#"> password? </a>
                </div>
            </form>

    </div>
</body>

</html>