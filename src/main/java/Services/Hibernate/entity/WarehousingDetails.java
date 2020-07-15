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

    @Column(name = "transport_fee")
    private Long transportFee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_WarehousingDetails"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "bill_warehousing_id", foreignKey = @ForeignKey(name = "fk_bill_warehousing_WarehousingDetails"))
    private BillWarehousing billWarehousing;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "warehousingDetails")
    private Set<DeliveryDetails> deliveryDetailsSet = new HashSet<DeliveryDetails>(0);

    public WarehousingDetails(Long amount, Long price, Long transportFee,
                              Product product, BillWarehousing billWarehousing, Set<DeliveryDetails> deliveryDetailsSet) {
        this.amount = amount;
        this.price = price;
        this.transportFee = transportFee;
        this.product = product;
        this.billWarehousing = billWarehousing;
        this.deliveryDetailsSet = deliveryDetailsSet;
    }

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

    public Long getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(Long transportFee) {
        this.transportFee = transportFee;
    }

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

    public Set<DeliveryDetails> getDeliveryDetailsSet() {
        return deliveryDetailsSet;
    }

    public void setDeliveryDetailsSet(Set<DeliveryDetails> deliveryDetailsSet) {
        this.deliveryDetailsSet = deliveryDetailsSet;
    }
}
