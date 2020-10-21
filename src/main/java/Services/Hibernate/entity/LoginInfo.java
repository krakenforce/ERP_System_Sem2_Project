package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Login_Info")
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1693792627127034955L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "username",unique = true, length = 255, nullable = false)
    private String username;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name="hashpw", length = 255, nullable = false)
    private String hashpw;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "email", length = 255)
    private String email; //unique

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "fk_loginType_user"))
    private UserType userType;


    public LoginInfo() {

    }

    public LoginInfo(String username, String hashpw, String email, UserType userType) {
        this.username = username;
        this.hashpw = hashpw;
        this.email = email;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "hibernate.entity.User{" +
                "username='" + username + '\'' +
                ", hashpw='" + hashpw + '\'' +
                ", email='" + email + '\'' +
                ", type=" + userType +
                '}';
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

    public String getHashpw() {
        return hashpw;
    }

    public void setHashpw(String hashpw) {
        this.hashpw = hashpw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

