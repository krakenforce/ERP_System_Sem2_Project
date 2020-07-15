package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bill_warehousing")
public class BillWarehousing {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "billWarehousing")
    private Set<WarehousingDetails> warehousingDetailsSet = new HashSet<WarehousingDetails>(0);

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "fk_warehouse_BillWarehousing"))
    private Warehouse warehouse;

    public Set<WarehousingDetails> getWarehousingDetailsSet() {
        return warehousingDetailsSet;
    }

    public void setWarehousingDetailsSet(Set<WarehousingDetails> warehousingDetailsSet) {
        this.warehousingDetailsSet = warehousingDetailsSet;
    }

    public BillWarehousing() {
    }

    public BillWarehousing(Date date, Set<WarehousingDetails> warehousingDetailsSet, Warehouse warehouse) {
        this.date = date;
        this.warehousingDetailsSet = warehousingDetailsSet;
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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
}
