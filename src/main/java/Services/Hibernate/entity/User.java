package Services.Hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_info")
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name="hashpw")
    private String hashpw;

    @Column(name = "email")
    private String email; //unique

    @Column(name ="type")
    private int type;

    public User() {

    }

    public User(String username, String hashpw, String email, int type) {
        this.username = username;
        this.hashpw = hashpw;
        this.email = email;
        this.type = type;
    }

    @Override
    public String toString() {
        return "hibernate.entity.User{" +
                "username='" + username + '\'' +
                ", hashpw='" + hashpw + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
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
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
