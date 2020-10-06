package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = -2444677953472206022L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @Column(name  = "money")
    private Long money;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "trade_discount_id", foreignKey = @ForeignKey(name = "fk_trade_discount_Payment"))
    private TradeDiscounts tradeDiscounts;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_TradeDiscounts"))
    private Customer customer;

    public Payment(Date date, TradeDiscounts tradeDiscounts, Customer customer) {
        this.date = date;
        this.tradeDiscounts = tradeDiscounts;
        this.customer = customer;
    }

    public Payment() {
    }

    public Payment(Long id, Date date, Long money, TradeDiscounts tradeDiscounts) {
        this.id = id;
        this.date = date;
        this.money = money;
        this.tradeDiscounts = tradeDiscounts;
    }

    public Payment(Long id, Date date, Long money) {
        this.id = id;
        this.date = date;
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TradeDiscounts getTradeDiscounts() {
        return tradeDiscounts;
    }

    public void setTradeDiscounts(TradeDiscounts tradeDiscounts) {
        this.tradeDiscounts = tradeDiscounts;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
