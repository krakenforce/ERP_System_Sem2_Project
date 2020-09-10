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

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "trade_discount_id", foreignKey = @ForeignKey(name = "fk_trade_discount_Payment"))
    private TradeDiscounts tradeDiscounts;

    public Payment(Date date, TradeDiscounts tradeDiscounts) {
        this.date = date;
        this.tradeDiscounts = tradeDiscounts;
    }

    public Payment() {
    }

    public Payment(Long id, Date date, TradeDiscounts tradeDiscounts) {
        this.id = id;
        this.date = date;
        this.tradeDiscounts = tradeDiscounts;
    }

    public Payment(Long id, Date date) {
        this.id = id;
        this.date = date;
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
}
