package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trade_discounts")
public class TradeDiscounts implements Serializable {
    private static final long serialVersionUID = 1060157365724340112L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "limit_money")
    private Long limitMoney;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date_start")
    private Date dateStars;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date_end")
    private Date dateEnd;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_TradeDiscounts"))
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "tradeDiscounts")
    private Set<Payment> paymentSet = new HashSet<Payment>(0);

    public TradeDiscounts(String name, Long limitMoney, Date dateStars, Date dateEnd, Customer customer, Set<Payment> paymentSet) {
        this.name = name;
        this.limitMoney = limitMoney;
        this.dateStars = dateStars;
        this.dateEnd = dateEnd;
        this.customer = customer;
        this.paymentSet = paymentSet;
    }

    public TradeDiscounts() {
    }

    public TradeDiscounts(String name, Long limitMoney, Date dateStars, Date dateEnd) {
        this.name = name;
        this.limitMoney = limitMoney;
        this.dateStars = dateStars;
        this.dateEnd = dateEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(Long limitMoney) {
        this.limitMoney = limitMoney;
    }

    public Date getDateStars() {
        return dateStars;
    }

    public void setDateStars(Date dateStars) {
        this.dateStars = dateStars;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Payment> getPaymentSet() {
        return paymentSet;
    }

    public void setPaymentSet(Set<Payment> paymentSet) {
        this.paymentSet = paymentSet;
    }
}
