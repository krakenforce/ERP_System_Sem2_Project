package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "order_product")
public class Order implements Serializable {

    private static final long serialVersionUID = 7597693586370507943L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type="true_false")
    @Column(name = "is_enough")
    private boolean isEnough;

    @Column(name = "amount")
    private Long amount;


    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "salesman_id" , foreignKey= @ForeignKey(name = "Fk_salesman_Order"), nullable = true)
    private Salesman salesman;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "detailOrder_id" , foreignKey= @ForeignKey(name = "Fk_detailOrder_Order"), nullable = true)
    private DetailOrder detailOrder;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "product_id" , foreignKey= @ForeignKey(name = "Fk_product_Order"), nullable = true)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "order")
    private Set<Delivery_Warehousing> delivery_WarehousingSet = new HashSet<Delivery_Warehousing>(0);

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "order")
    private Set<RecieptsProduct> recieptsProductSet = new HashSet<RecieptsProduct>(0);

    public Order() {
    }

    public Order(long amount, boolean enough, DetailOrder detailOrder, Product product, Salesman salesman) {
        this.amount = amount;
        this.isEnough = enough;
        this.detailOrder = detailOrder;
        this.product = product;
        this.salesman = salesman;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnough() {
        return isEnough;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setEnough(boolean enough) {
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
    public Long tinhTienOrder(){
        return amount*product.getPrice();
    }

    public Set<Delivery_Warehousing> getDelivery_WarehousingSet() {
        return delivery_WarehousingSet;
    }

    public void setDelivery_WarehousingSet(Set<Delivery_Warehousing> delivery_WarehousingSet) {
        this.delivery_WarehousingSet = delivery_WarehousingSet;
    }

    public Set<RecieptsProduct> getRecieptsProductSet() {
        return recieptsProductSet;
    }

    public void setRecieptsProductSet(Set<RecieptsProduct> recieptsProductSet) {
        this.recieptsProductSet = recieptsProductSet;
    }
}
