package Services.Hibernate.entity;

import javax.persistence.*;


@Entity
@DiscriminatorValue("DetailOutOfStockOrder")
public class DetailOutOfStockOrder extends DeliveryDetails {

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "delivery_bill_id", foreignKey = @ForeignKey(name = "fk_delivery_bill_DetailOutOfStockOrder"))
    private DeliveryBill deliveryBill;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_DetailOutOfStockOrder"))
    private Order order;

    public DetailOutOfStockOrder(WarehousingDetails warehousingDetails, DeliveryBill deliveryBill, Order order) {
        super(warehousingDetails);
        this.deliveryBill = deliveryBill;
        this.order = order;
    }

    public DetailOutOfStockOrder() {
    }

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
