package springboot.ToDo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    public Todo(int uid, int id, String username, String description, LocalDate creationDate , LocalDate targetDate, boolean done, Blob attach /*, MultipartFile multipartFile, String attachedFileName */ ) {
        this.uid = uid;
        this.id = id;
        this.username = username;
        this.description = description;
        this.creationDate =creationDate;
        this.targetDate = targetDate;
        this.done = done;
        this.attach = attach;
    }

    // @ Id is for Primary Ky
    //    @Id  //this must be present else error =  Failed to initialize JPA EntityManagerFactory: Entity Model.Todo has no identifier (every '@Entity' class must declare or inherit at least one '@Id' or '@EmbeddedId' property)
    // PK primary Key is MUST else error..
    @Id   // Ensure that this UID-PK field is annotated with @GeneratedValue for automatic ID generation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name= "uid",  nullable = false)  // you're instructing JPA to create a database column that does not allow NULL values.
    private int uid;

    @NotNull
    @Column(name = "id", unique = true, nullable = false)  // you're instructing JPA to create a database column that does not allow NULL values.
    @Positive(message = "springboot-starter-validation-@size---> ID -->  ALLOWED to enter only POSITIVE ")
    @Digits(message=" springboot-starter-validation-@size---> ID --> Number should contain between 0 to 3 digits.", fraction = 0, integer = 3)
    private int id;

    @Email(message = "springboot-starter-validation-@size---> username -->  ALLOWED to enter only EMAIL ")
    @Column(name = "username")
    private String username;

    @Size(min=7 ,max = 40, message = "springboot-starter-validation-@size---> Description --> ALLOWED to enter only 7-20 char ")
    //@Column(name = "description") this is not Mandatory, it will automatically take it
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
    @JsonIgnore // this will help to avoid serialization of this field and if we dont annotate this then it wil throw error = com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class java.io.ByteArrayInputStream and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS
    private Blob attach; // Store file as binary data , but can store only small size---> byte[], big size go to BLOB

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    public Blob getAttach() {
        return attach;
    }

    public void setAttach(Blob attach) {
        this.attach = attach;
    }
}