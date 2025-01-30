package springboot.ToDo.Model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // Marks the class as a JPA entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todohlogin", schema = "sumit")
@Data       // @Data = Lombok annotation.. generates boilerplate code for a class.., like getters, setters, equals(), hashCode(), and toString()
public class User {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    @NotNull
    private int uid;

    @Column(name = "username", nullable = false, unique = true)
    @Email
    @NotNull
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creationDate")
    @CreatedDate
    private LocalDate creationDate;

    @CreatedDate
    @Column(nullable = false, updatable = false) // Ensures immutability
    private LocalDateTime createdDate;

}
