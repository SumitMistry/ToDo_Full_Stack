package springboot.ToDo.Model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "todo_profiles" , schema = "sumit")
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    @NotNull
    private int uid;


    @NotNull
    @Email
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "f_name", nullable = false)
    @NotNull
    private String f_name;

    @Column(name = "l_name", nullable = false)
    @NotNull
    private String l_name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    public UserProfile(int uid, String username, String f_name, String l_name, String phone, String city) {
        this.uid = uid;
        this.username = username;
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone = phone;
        this.city = city;
    }

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

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
