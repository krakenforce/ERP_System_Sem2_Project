package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "salesman")
public class Salesman implements Serializable {

    private static final long serialVersionUID = 9027935215919441437L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "salesman")
    private Set<Customer> customerSet = new HashSet<Customer>(0);

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "salesman")
    private Set<Order> orderSet = new HashSet<Order>(0);

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH},
            mappedBy = "salesman")
    private Set<Salesman_GroupProduct> salesman_groupProductSet = new HashSet<Salesman_GroupProduct>(0);

    public Salesman(String name, String phone, String address, Set<Customer> customerSet,
                    Set<Order> orderSet, Set<Salesman_GroupProduct> salesman_groupProductSet) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.customerSet = customerSet;
        this.orderSet = orderSet;
        this.salesman_groupProductSet = salesman_groupProductSet;
    }

    public Salesman() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Customer> getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(Set<Customer> customerSet) {
        this.customerSet = customerSet;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public Set<Salesman_GroupProduct> getSalesman_groupProductSet() {
        return salesman_groupProductSet;
    }

    public void setSalesman_groupProductSet(Set<Salesman_GroupProduct> salesman_groupProductSet) {
        this.salesman_groupProductSet = salesman_groupProductSet;
    }
}
