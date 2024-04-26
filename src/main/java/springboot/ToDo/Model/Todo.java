package springboot.ToDo.Model;


import java.time.LocalDate;
import java.util.Date;

public class Todo {

    private int id;
    private String username;
    private String description;
    private LocalDate creationDate;
    private LocalDate targetDate;
    private boolean done;
    //private Blob attachment;


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Todo(int id, String username, String description, LocalDate creationDate , LocalDate targetDate, boolean done /*, Blob attachment */) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.creationDate =creationDate;
        this.targetDate = targetDate;
        this.done = done;
        //this.attachment = attachment;
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

//    public Blob getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(Blob attachment) {
//        this.attachment = attachment;
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
                ", attachment= XX"  +
                '}';
    }
}
