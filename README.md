## REVISION / ENHANCEMENTS: 


1. Create Database /table 
    - with "username", "algorithm", "encrpted_pass", "decrpted_pass" password stored with Bcrpt and able to decrpyt


2.  SIGN UP page Construction
    -  Create new singup.jsp / SpringSecurityConfiguration{}
    - Create db to save email, encrpted(bcrypted pass) / connect AD
    - Add a new line of code in SpringSecurityConfiguration::configure_each_user_detail() 
      - UserDetails user3 = createNewUSer(adminUsername2, adminPass2, adminRole2, adminRole3);
    -  Change flow of signup page to this user3, once new userAuth created stores in db, and while login user3, retrive data from db, validate pass and then allow login
 

3. SEARCH API - Date Range Picker (From To)
   - jsp /
   - 

    

---------------
Ref in-progress: D:\hackerRank\src\Spring_SpringBoot_applications\Udemy\07 - Build Java Web Application with Spring Framework, Spring Boot and Hibernate

---------------




