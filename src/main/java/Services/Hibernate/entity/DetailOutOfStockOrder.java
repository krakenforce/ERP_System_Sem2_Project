package Services.Hibernate.entity;

import javax.persistence.*;


@Entity
@Table(name = "detail_out_of_stock_order")
public class DetailOutOfStockOrder extends DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE})
    @JoinColumn(name = "delivery_bill_id", foreignKey = @ForeignKey(name = "fk_delivery_bill"))
    private DeliveryBill deliveryBill;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE})
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order"))
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryBill getDeliveryBill() {
        return deliveryBill;
    }

    public void setDeliveryBill(DeliveryBill deliveryBill) {
        this.deliveryBill = deliveryBill;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
