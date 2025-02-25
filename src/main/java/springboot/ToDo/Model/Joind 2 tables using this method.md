Response 1 : Join data, display all user data from both UserAuth and UserProfile. 

To fetch and display all user data from both UserAuth and UserProfile on a single JSP page, follow these steps:
1️⃣ Establish One-to-One Relationship in Entities


------------

Modify your UserAuth and UserProfile entities to define a one-to-one relationship.
✅ UserAuth.java (Modify It)

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private UserProfile userProfile;

✅ UserProfile.java (Modify It)
    
    @OneToOne(mappedBy = "userProfile")
    private UserAuth userAuth;

2️⃣ Create a Repository to Fetch Joined Data

We need a method in the JPA repository to fetch user details by username.
✅ Repo_DAO_UserAuth_JPA.java
    
    @Repository
    public interface Repo_DAO_UserAuth_JPA extends JpaRepository<UserAuth, Integer> {
        
        // Fetch user details by username, including UserProfile
        @Query("SELECT u FROM UserAuth u JOIN FETCH u.userProfile WHERE u.username = :username")
        Optional<UserAuth> findByUsernameWithProfile(@Param("username") String username);
    }

3️⃣ Create a Service to Fetch the Data

Define a service method that fetches user details including profile data.
✅ UserAuthService.java
    
    @Service
    public class UserAuthService {
    
        @Autowired
        private Repo_DAO_UserAuth_JPA repo_dao_userAuth_jpa;
    
        public UserAuth getUserWithProfile(String username) {
            return repo_dao_userAuth_jpa.findByUsernameWithProfile(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
    }

4️⃣ Modify Controller to Pass Data to JSP

Now, modify your controller to fetch user details and send them to the JSP page.
✅ User_Profile_Controller.java
    
    @Controller
    @RequestMapping("/")
    @SessionAttributes({"uid_email"})
    public class User_Profile_Controller {
    
        @Autowired
        private UserAuthService userAuthService;
    
        @RequestMapping(value = "/userDetails", method = RequestMethod.GET)
        public String showUserDetails(@SessionAttribute(value = "uid_email") String username, ModelMap modelMap) {
            UserAuth userAuth = userAuthService.getUserWithProfile(username);
            modelMap.addAttribute("userAuth", userAuth);
            modelMap.addAttribute("userProfile", userAuth.getUserProfile());
            return "userDetails"; // This is the JSP page name
        }
    }

5️⃣ Create JSP Page to Display Data

Now, create userDetails.jsp to show all user data.
✅ userDetails.jsp
    
    <%@ include file="common/header.jspf" %>
    <%@ include file="common/navigation.jspf" %>
    
    <div class="container">
        <h2>User Details</h2>
        <table class="table table-bordered">
            <tr><th>Username</th><td>${userAuth.username}</td></tr>
            <tr><th>Role</th><td>${userAuth.user_role}</td></tr>
            <tr><th>Created Date</th><td>${userAuth.createdDate}</td></tr>
            <tr><th>First Name</th><td>${userProfile.f_name}</td></tr>
            <tr><th>Last Name</th><td>${userProfile.l_name}</td></tr>
            <tr><th>Phone</th><td>${userProfile.phone}</td></tr>
            <tr><th>City</th><td>${userProfile.city}</td></tr>
        </table>
    </div>
    
    <%@ include file="common/footer.jspf" %>



------------

🎯 Final Result

✔ Retrieves UserAuth and UserProfile data together.
✔ Displays all user data on userDetails.jsp.
✔ Uses session attributes to get the logged-in user.
✔ Handles errors properly if the user is not found.

🚀 Now, when you navigate to /userDetails, you will see all user data in one JSP page. 🎯

Let me know if you need further tweaks! 😊