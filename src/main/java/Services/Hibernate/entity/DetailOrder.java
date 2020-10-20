package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "detail_order")
public class DetailOrder implements Serializable {

    private static final long serialVersionUID = -3809551132407679447L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "true_false")
    @Column(name = "is_Pay")
    private Boolean isPay;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_DetailOrder"))
    private Customer customer;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @Column(name = "debt")
    private Long debt;

    @Column(name = "total")
    private Long total;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "detailOrder")
    private Set<Receipts> receiptHashSet=  new HashSet<Receipts>(0);

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "detailOrder")
    private  Set<Order> orderSet = new HashSet<Order>(0);

    public DetailOrder(Boolean isPay, Customer customer, Date date, Set<Receipts> receiptHashSet, Set<Order> orderSet) {
        this.isPay = isPay;
        this.customer = customer;
        this.date = date;
        this.receiptHashSet = receiptHashSet;
        this.orderSet = orderSet;
    }
    public DetailOrder(){};

    public DetailOrder(Date date, boolean b, Customer selectedCustomer, Long debt, Long total) {
        this.date = date;
        this.isPay = b;
        this.customer = selectedCustomer;
        this.debt = debt;
        this.total = total;
    }

    public DetailOrder(Long detailOrderID) {
    }

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

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long tinhTongTienDetailOrder(){
        Long tongTien = (long) 0;

        for(Order order : orderSet){
            tongTien = tongTien + order.tinhTienOrder();
        }

        return tongTien;
    }

    public Long tinhTienDaTra() {
        Long tienDaTra = (long) 0;
        for(Receipts receipts: receiptHashSet){
            tienDaTra = tienDaTra + receipts.getMoneyPay();
        }
        return tienDaTra;
    }
}
