package Services.Hibernate.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "delivery_bill")
public class DeliveryBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE})
    @JoinColumn(name = "detail_out_of_stock_id", foreignKey = @ForeignKey(name = "detail_out_of_order"))
    private Set<DetailOutOfStockOrder> detailOutOfStockOrderSet = new HashSet<DetailOutOfStockOrder>(0);

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<DetailOutOfStockOrder> getDetailOutOfStockOrderSet() {
        return detailOutOfStockOrderSet;
    }

    public void setDetailOutOfStockOrderSet(Set<DetailOutOfStockOrder> detailOutOfStockOrderSet) {
        this.detailOutOfStockOrderSet = detailOutOfStockOrderSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
