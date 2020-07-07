package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class GroupProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "commission")
    private Double commission;

    @Type( type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "name")
    private String name;

    private Set<Salesman_GroupProduct> salesman_groupProductSet = new HashSet<Salesman_GroupProduct>(0);

    private Set<Product> productHashSet = new HashSet<Product>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Salesman_GroupProduct> getSalesman_groupProductSet() {
        return salesman_groupProductSet;
    }

    public void setSalesman_groupProductSet(Set<Salesman_GroupProduct> salesman_groupProductSet) {
        this.salesman_groupProductSet = salesman_groupProductSet;
    }

    public Set<Product> getProductHashSet() {
        return productHashSet;
    }

    public void setProductHashSet(Set<Product> productHashSet) {
        this.productHashSet = productHashSet;
    }
}
