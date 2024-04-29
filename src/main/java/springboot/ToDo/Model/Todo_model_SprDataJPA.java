package springboot.ToDo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todoh", schema = "sumit") // // Here "todoh" will be the table name created by hibernate automatically under schema/db=sumit
public class Todo_model_SprDataJPA {


    @Id
    @Column(name = "id")
    @Positive(message = "springboot-starter-validation-@size---> ID -->  ALLOWED to enter only POSITIVE ")
    @Digits(message=" springboot-starter-validation-@size---> ID --> Number should contain between 0 to 3 digits.", fraction = 0, integer = 3)
    private int id;
    @Email(message = "springboot-starter-validation-@size---> username -->  ALLOWED to enter only EMAIL ")
    @Column(name = "username")
    private String username;
    @Size(min=7 ,max = 12, message = "springboot-starter-validation-@size---> Description --> ALLOWED to enter only 7-12 char ")
    @Column(name = "description")
    private String description;
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name ="creationDate")
    private LocalDate creationDate;
    @Future(message ="springboot-starter-validation-@size---> targetDate -->  ALLOWED to enter only FUTURE dates " ) // to check date in future
    @Column(name="targetDate")
    private LocalDate targetDate;
    @NotNull(message = "springboot-starter-validation-@size---> DONE -->  ALLOWED to enter only boolean done's NON NULL ")
    @Column(name="done")
    private boolean done;
    @Column(name ="attach")
    private Blob attach;



    public Todo_model_SprDataJPA(int id, String username, String description, LocalDate creationDate , LocalDate targetDate, boolean done) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.creationDate =creationDate;
        this.targetDate = targetDate;
        this.done = done;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Blob getattach() {
        return attach;
    }

    public void setattach(Blob attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", username='" + username +
                ", description='" + description +
                ", targetDate=" + targetDate +
                ", creationDate=" + creationDate +
                ", done=" + done +
                ", attach="  +attach+
                '}';
    }
}
