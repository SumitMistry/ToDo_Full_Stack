## REVISION / ENHANCEMENTS: 

1.  SIGN UP page Construction
    -  Create new singup.jsp / SpringSecurityConfiguration{}
    - Create db to save email, encrpted(bcrypted pass) / connect AD
    - Add a new line of code in SpringSecurityConfiguration::configure_each_user_detail() 
      - UserDetails user3 = createNewUSer(adminUsername2, adminPass2, adminRole2, adminRole3);
    -  Change flow of signup page to this user3, once new user created stores in db, and while login user3, retrive data from db, validate pass and then allow login
    - 

2. 