package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class Salesman {

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

    private Set<Customer> customerSet = new HashSet<Customer>(0);

    private Set<Order> orderSet = new HashSet<Order>(0);

    private Set<Salesman_GroupProduct> salesman_groupProductSet = new HashSet<Salesman_GroupProduct>(0);

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
