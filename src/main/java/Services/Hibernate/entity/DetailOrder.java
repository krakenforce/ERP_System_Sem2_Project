package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "detail_order")
public class DetailOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "true_false")
    @Column(name = "is_Pay")
    private Boolean isPay;

    @Column(name = "customer")
    private Customer customer;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE}, mappedBy = "detailOrder")
    @JoinColumn(name = "receipt_hash_id", foreignKey = @ForeignKey(name = "fk_receipt_hash"))
    private Set<Receipts> receiptHashSet=  new HashSet<Receipts>(0);

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE}, mappedBy = "detailOrder")
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order"))
    private  Set<Order> orderSet = new HashSet<Order>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Receipts> getReceiptHashSet() {
        return receiptHashSet;
    }

    public void setReceiptHashSet(Set<Receipts> receiptHashSet) {
        this.receiptHashSet = receiptHashSet;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }
}
