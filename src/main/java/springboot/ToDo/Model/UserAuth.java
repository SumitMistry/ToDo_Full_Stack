package springboot.ToDo.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity // Marks the class as a JPA entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_user_auth", schema = "sumit")
@Data       // @Data = Lombok annotation.. generates boilerplate code for a class.., like @getters, @setters, equals(), hashCode(), and toString()
@EntityListeners(AuditingEntityListener.class)  // Enables auditing ---> REQUIRED to get @Creation date data
public class UserAuth {


    public UserAuth(String username, String password_raw, String password_encoded) {
        this.username = username;
        this.password_raw = password_raw;
        this.password_encoded = password_encoded;
    }


    public UserAuth(String username, String password_raw) {
        this.username = username;
        this.password_raw = password_raw;
    }


    public UserAuth(String username, String password_raw, String password_encoded, String[] user_role) {
        this.username = username;
        this.password_raw = password_raw;
        this.password_encoded = password_encoded;
        this.user_role = user_role;
    }


    public UserAuth(String password_raw) {
        this.password_raw = password_raw;
    }


    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    @NotNull
    private int uid;

    @Column(name = "username", nullable = false, unique = true)
    @Email(message = "ONLY emails allowed as USERNAME")
    @NotNull
    private String username;

    @Column(name = "password_raw", nullable = false)
    private String password_raw;

    // @Null  --> this will enforce all the values mut be null....ONLY NULL all values, that is not we want.
    @Column(name = "password_encoded", nullable = true)
    private String password_encoded;

    @Column(name = "user_role", nullable = true) // nullable = true means, null values allowed
    private String[] user_role;


    /*
    What is @EntityListeners(AuditingEntityListener.class)?
        The annotation @EntityListeners(AuditingEntityListener.class) is used in a JPA entity to enable auditing functionality
        provided by Spring Data JPA. It listens for lifecycle events (e.g., insert, update) and automatically updates auditing fields like @CreatedDate and @LastModifiedDate
    Why Do We Need It?
        1)  Enables Automatic Timestamping----If you use @CreatedDate and @LastModifiedDate, you need AuditingEntityListener to populate these fields automatically.
        2)  It listens for entity lifecycle events and sets values accordingly.
    How It Works?
        Spring Boot scans the entity
        @EntityListeners(AuditingEntityListener.class) is detected
        Spring injects auditing values during entity creation or update
        No need to set timestamps manually!
     */
    @CreatedDate
    @Column(nullable = false, updatable = false) // Ensures immutability
    private LocalDateTime createdDate;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_raw() {
        return password_raw;
    }

    public void setPassword_raw(String password_raw) {
        this.password_raw = password_raw;
    }

    public String getPassword_encoded() {
        return password_encoded;
    }

    public void setPassword_encoded(String password_encoded) {
        this.password_encoded = password_encoded;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String[] getUser_role() {
        return user_role;
    }

    public void setUser_role(String[] user_role) {
        this.user_role = user_role;
    }


    @Override
    public String toString() {
        return "UserAuth{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password_raw='" + password_raw + '\'' +
                ", password_encoded='" + password_encoded + '\'' +
                ", user_role=" + Arrays.toString(user_role) +
                ", createdDate=" + createdDate +
                '}';
    }
}
