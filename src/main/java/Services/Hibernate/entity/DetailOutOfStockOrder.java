package Services.Hibernate.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DetailOutOfStockOrder extends DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "delivery_Bill")
    private DeliveryBill deliveryBill;

    @Column(name = "order")
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
