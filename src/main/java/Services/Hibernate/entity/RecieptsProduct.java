package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "RecieptsProduct")
public class RecieptsProduct implements Serializable {
    private static final long serialVersionUID = -3743428259186967839L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name  = "amout")
    private Long amout;

    @Column(name  = "totalMoney")
    private Long totalMoney;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "reason", length = 255, nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_RecieptsProduct_order"))
    private Order order;

    public RecieptsProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmout() {
        return amout;
    }

    public void setAmout(Long amout) {
        this.amout = amout;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

