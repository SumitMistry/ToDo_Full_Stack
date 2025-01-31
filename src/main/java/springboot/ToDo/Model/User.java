package springboot.ToDo.Model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // Marks the class as a JPA entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todohusers", schema = "sumit")
@Data       // @Data = Lombok annotation.. generates boilerplate code for a class.., like @getters, @setters, equals(), hashCode(), and toString()
@EntityListeners(AuditingEntityListener.class)  // Enables auditing ---> REQUIRED
public class User {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    @NotNull
    private int uid;

    @Column(name = "username", nullable = false, unique = true)
    @Email(message = "ONLY emails allowed as USERNAME")
    @NotNull
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

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

}
