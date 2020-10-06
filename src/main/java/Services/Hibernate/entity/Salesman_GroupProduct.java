package Services.Hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "salesman_group_product")
public class Salesman_GroupProduct implements Serializable {

    private static final long serialVersionUID = 5381057270387414743L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "salesman_id", foreignKey = @ForeignKey(name = "fk_salesman_Salesman_GroupProduct"))
    private Salesman salesman;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "groupProduct_id", foreignKey = @ForeignKey(name = "fk_groupProduct_Salesman_GroupProduct"))
    private GroupProduct groupProduct;

    public Salesman_GroupProduct(Salesman salesman, GroupProduct groupProduct) {
        this.salesman = salesman;
        this.groupProduct = groupProduct;
    }

    public Salesman_GroupProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public GroupProduct getGroupProduct() {
        return groupProduct;
    }

    public void setGroupProduct(GroupProduct groupProduct) {
        this.groupProduct = groupProduct;
    }
}
