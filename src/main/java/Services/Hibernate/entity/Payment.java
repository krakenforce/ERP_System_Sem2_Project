package Services.Hibernate.entity;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "trade_discount_id", foreignKey = @ForeignKey(name = "fk_trade_discount"))
    private TradeDiscounts tradeDiscounts;

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
