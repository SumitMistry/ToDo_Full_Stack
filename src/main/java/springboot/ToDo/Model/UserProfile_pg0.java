package springboot.ToDo.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "todo_user_profiles" , schema = "sumit")
public class UserProfile_pg0 {

    // 1️⃣ Establish One-to-One Relationship in Entities
    //    Modify your UserAuth and UserProfile_pg0 entities to define a one-to-one relationship.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName ="username", insertable = false, updatable = false, nullable = false, unique = false )
    private UserAuth userAuth;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false, unique = true, updatable = false)
    @NotNull
    private int uid;

    @NotNull
    @Email(message = "ONLY emails allowed as USERNAME")
    @Column(name = "username", nullable = false, unique = true)
    //  @OneToOne(mappedBy = "username", )
    private String username;

    @DateTimeFormat(pattern = "dd/MM/yyyy")  // --> It only works when binding Java objects via @ModelAttribute. means if front frontend jsp file, we sending data to controller using via modelattribute. Int his case it works, otherwise this will not work.
    @Past(message ="springboot-starter-validation-@Past---> birth_date -->  ALLOWED to enter only PAST dates " ) // to check date in past
    // this past works only if I am binign data to frontend using modelattribute, individual tag binding case, this valid will not work, use @past directly at controlelr level, so @valid not needed to use
    @Column(name = "birth_date", nullable = true)
    private LocalDate birth_date;

    @Column(name = "f_name", nullable = true)
    private String f_name;

    @Column(name = "l_name", nullable = true)
    private String l_name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;



    //adding <this.userAuth=userAuth>  to constructor


    public UserProfile_pg0(UserAuth userAuth, int uid, String username, LocalDate birth_date, String f_name, String l_name, String phone, String city) {
        this.userAuth = userAuth;
        this.uid = uid;
        this.username = username;
        this.birth_date = birth_date;
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone = phone;
        this.city = city;
    }

    public UserProfile_pg0(String username) {
        this.username = username;
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

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString() {
        return "UserProfile_pg0{" +
                "userAuth=" + userAuth +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", birth_date=" + birth_date +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
