package springboot.ToDo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Table(name = "todoh", schema = "sumit") // // Here "todoh" will be the table name created by hibernate automatically under schema/db=sumit
public class Todo {


    public Todo(int id, String username, String description, LocalDate creationDate , LocalDate targetDate, boolean done, Blob attach /*, MultipartFile multipartFile, String attachedFileName */ ) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.creationDate =creationDate;
        this.targetDate = targetDate;
        this.done = done;
        this.attach = attach;
    }

    @Id  //this must be present else error =  Failed to initialize JPA EntityManagerFactory: Entity Model.Todo has no identifier (every '@Entity' class must declare or inherit at least one '@Id' or '@EmbeddedId' property)
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

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name ="creationDate")
    private LocalDate creationDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message ="springboot-starter-validation-@size---> targetDate -->  ALLOWED to enter only FUTURE dates " ) // to check date in future
    @Column(name="targetDate")
    private LocalDate targetDate;


    @Column(name="done")
    private boolean done;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Store file as binary data
    @Column(name ="attach")
    @Lob
    private Blob attach; // Store file as binary data , but can store only small size---> byte[], big size go to BLOB
    public Blob getattach() {
        return attach;
    }
    public void setattach(Blob attach) {
        this.attach = attach;
    }

//    @Transient // Exclude from JPA mapping // this will not reach to databse, and will just work internally to java...
//    // MultipartFile field for file upload  // this variable name to be set into upload.jsp form at binding object like this:  form:bind="*{multipartFile}"/>
//    private MultipartFile multipartFile; // MultipartFile field for file upload / this variable name to be set into upload.jsp form at binding object like this:  form:bind="*{multipartFile}"/>
//    // this variable name to be set into upload.jsp form at binding object like this:  form:bind="*{multipartFile}"/>
//    public MultipartFile getMultipartFile(){
//        return  multipartFile;
//    }
//    public void setMultipartFile(MultipartFile multipartFile){
//        this.multipartFile = multipartFile;
//    }
//
//    @Transient // Exclude from JPA mapping // this will not reach to databse, and will just work internally to java...
//    private String attachedFileName; // Store file name
//    public String getAttachedFileName() {
//        return attachedFileName;
//    }
//    public void setAttachedFileName(String attachedFileName) {
//        this.attachedFileName = attachedFileName;
//    }

    ///////////////////////////////////////////////////////////////////////////////////////
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
