package Services.Hibernate.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_type")
public class UserType {
    public UserType(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "type_name",unique = true, length = 255, nullable = false)
    private String typeName;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "userType")
    private Set<LoginInfo> loginInfoSet = new HashSet<LoginInfo>(0);

    public Set<LoginInfo> getLoginInfoSet() {
        return loginInfoSet;
    }

    public void setLoginInfoSet(Set<LoginInfo> loginInfoSet) {
        this.loginInfoSet = loginInfoSet;
    }

    public UserType(Integer id, String typeName, Set<LoginInfo> loginInfoSet) {
        this.id = id;
        this.typeName = typeName;
        this.loginInfoSet = loginInfoSet;
    }

    public UserType(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
