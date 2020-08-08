package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_product")
public class GroupProduct implements Serializable {

    private static final long serialVersionUID = -7918105939904676862L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "commission")
    private Double commission;

    @Type( type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}
            ,mappedBy = "groupProduct")
    private Set<Salesman_GroupProduct> salesman_groupProductSet = new HashSet<Salesman_GroupProduct>(0);


    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "groupProduct")
    private Set<Product> productHashSet = new HashSet<Product>(0);

    public GroupProduct(Double commission, String name, Set<Salesman_GroupProduct> salesman_groupProductSet, Set<Product> productHashSet) {
        this.commission = commission;
        this.name = name;
        this.salesman_groupProductSet = salesman_groupProductSet;
        this.productHashSet = productHashSet;
    }

    public GroupProduct() {
    }

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
