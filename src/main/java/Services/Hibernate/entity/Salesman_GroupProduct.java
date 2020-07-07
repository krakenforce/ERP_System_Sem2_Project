package Services.Hibernate.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Salesman_GroupProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private Salesman salesman;

    private GroupProduct groupProduct;

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
