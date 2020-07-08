package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_product")
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE})
    @JoinColumn(name = "salesman_group_product_id", foreignKey = @ForeignKey(name = "fk_salesman_group_product"))
    private Set<Salesman_GroupProduct> salesman_groupProductSet = new HashSet<Salesman_GroupProduct>(0);


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE})
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product"))
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
