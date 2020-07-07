package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Bill_Warehousing")
public class BillWarehousing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "warehousing_Detail_Set", nullable = false)
    private Set<WarehousingDetails> warehousingDetailsSet = new HashSet<WarehousingDetails>(0);

    @Column(name = "warehouse")
    private Warehouse warehouse;

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

    public Set<WarehousingDetails> getWarehousingDetailsSet() {
        return warehousingDetailsSet;
    }

    public void setWarehousingDetailsSet(Set<WarehousingDetails> warehousingDetailsSet) {
        this.warehousingDetailsSet = warehousingDetailsSet;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
