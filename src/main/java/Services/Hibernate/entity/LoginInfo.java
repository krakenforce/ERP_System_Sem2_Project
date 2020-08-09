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
    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name="hashpw", length = 255, nullable = false)
    private String hashpw;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "email", length = 255)
    private String email; //unique

    @Column(name ="type_id")
    private int type_id;

    public LoginInfo() {

    }

    public LoginInfo(String username, String hashpw, String email, int type_id) {
        this.username = username;
        this.hashpw = hashpw;
        this.email = email;
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "hibernate.entity.User{" +
                "username='" + username + '\'' +
                ", hashpw='" + hashpw + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type_id +
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

    public int getType() {
        return type_id;
    }

    public void setType(int type) {
        this.type_id = type;
    }
}

