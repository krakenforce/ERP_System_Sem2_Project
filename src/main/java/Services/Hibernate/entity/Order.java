package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "true_false")
    @Column(name = "is_enough")
    private Boolean isEnough;


    private Salesman salesman;

    private DetailOrder detailOrder;

    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnough() {
        return isEnough;
    }

    public void setEnough(Boolean enough) {
        isEnough = enough;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public DetailOrder getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrder detailOrder) {
        this.detailOrder = detailOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
