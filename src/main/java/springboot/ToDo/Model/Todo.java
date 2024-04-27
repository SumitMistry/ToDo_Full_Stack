package springboot.ToDo.Model;


import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Date;

public class Todo {

    @Positive(message = "springboot-starter-validation-@size---> ID -->  ALLOWED to enter only POSITIVE ")
    @Digits(message=" springboot-starter-validation-@size---> ID --> Number should contain between 0 to 3 digits.", fraction = 0, integer = 3)
    private int id;
    @Email(message = "springboot-starter-validation-@size---> username -->  ALLOWED to enter only EMAIL ")
    private String username;
    @Size(min=7 ,max = 12, message = "springboot-starter-validation-@size---> Description --> ALLOWED to enter only 7-12 char ")
    private String description;
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    @Future(message ="springboot-starter-validation-@size---> targetDate -->  ALLOWED to enter only FUTURE dates " ) // to check date in future
    private LocalDate targetDate;
    @NotNull(message = "springboot-starter-validation-@size---> DONE -->  ALLOWED to enter only boolean done's NON NULL ")
    private boolean done;
    //private Blob attach;


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Todo(int id, String username, String description, LocalDate creationDate , LocalDate targetDate, boolean done /*, Blob attach */) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.creationDate =creationDate;
        this.targetDate = targetDate;
        this.done = done;
        //this.attach = attach;
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

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

//    public Blob getattach() {
//        return attach;
//    }
//
//    public void setattach(Blob attach) {
//        this.attach = attach;
//    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", username='" + username +
                ", description='" + description +
                ", targetDate=" + targetDate +
                ", creationDate=" + creationDate +
                ", done=" + done +
                ", attach= XX"  +
                '}';
    }
}
