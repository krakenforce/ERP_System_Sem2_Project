package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "Delivery_Warehousing")
public class Delivery_Warehousing implements Serializable {

    private static final long serialVersionUID = 8122565350103329406L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "warehousingDetails_id", foreignKey = @ForeignKey(name = "fk_warehousingDetails_Delivery_Warehousing"))
    private WarehousingDetails warehousingDetails;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_Delivery_Warehousing_oder"))
    private Order order;

    public Delivery_Warehousing(Long amount, WarehousingDetails warehousingDetails, Order order) {
        this.amount = amount;
        this.warehousingDetails = warehousingDetails;
        this.order = order;
    }

    public Delivery_Warehousing() {
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

    public WarehousingDetails getWarehousingDetails() {
        return warehousingDetails;
    }

    public void setWarehousingDetails(WarehousingDetails warehousingDetails) {
        this.warehousingDetails = warehousingDetails;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
