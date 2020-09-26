package Services.Hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "warehousing_details")
public class WarehousingDetails implements Serializable {

    private static final long serialVersionUID = -3295642969588711730L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_WarehousingDetails"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "bill_warehousing_id", foreignKey = @ForeignKey(name = "fk_bill_warehousing_WarehousingDetails"))
    private BillWarehousing billWarehousing;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "warehousingDetails")
    private Set<Delivery_Warehousing> delivery_WarehousingSet = new HashSet<Delivery_Warehousing>(0);



    public WarehousingDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

//    public Long getTransportFee() {
//        return transportFee;
//    }
//
//    public void setTransportFee(Long transportFee) {
//        this.transportFee = transportFee;
//    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BillWarehousing getBillWarehousing() {
        return billWarehousing;
    }

    public void setBillWarehousing(BillWarehousing billWarehousing) {
        this.billWarehousing = billWarehousing;
    }

    public Set<Delivery_Warehousing> getDelivery_WarehousingSet() {
        return delivery_WarehousingSet;
    }

    public void setDelivery_WarehousingSet(Set<Delivery_Warehousing> delivery_WarehousingSet) {
        this.delivery_WarehousingSet = delivery_WarehousingSet;
    }

    @Override
    public String toString() {
        return "\nWarehousingDetails{" +
                "id=" + id +
                ", amount=" + amount +
                ", price=" + price +
                "} ";
    }
}
