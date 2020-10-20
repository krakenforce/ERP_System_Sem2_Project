package Services.Hibernate.entity;

//Delete this table
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "delivery_bill")
public class DeliveryBill implements Serializable {

    private static final long serialVersionUID = 3290225096670717279L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "deliveryBill")
    private Set<DetailOutOfStockOrder> detailOutOfStockOrderSet = new HashSet<DetailOutOfStockOrder>(0);

    public DeliveryBill() {
    }

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

    public DeliveryBill(Date date, Set<DetailOutOfStockOrder> detailOutOfStockOrderSet) {
        this.date = date;
        this.detailOutOfStockOrderSet = detailOutOfStockOrderSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
