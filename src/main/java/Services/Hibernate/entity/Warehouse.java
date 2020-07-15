package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="Warehouse")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Warehouse_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Warehouse implements Serializable {
    private static final long serialVersionUID = -4070778434856813984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "address", length = 50, nullable = false)
    protected String address;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "warehouse")
    protected Set<BillWarehousing> billWarehousingSet = new HashSet<BillWarehousing>(0);

    public Warehouse(String address, Set<BillWarehousing> billWarehousingSet) {
        this.address = address;
        this.billWarehousingSet = billWarehousingSet;
    }

    public Warehouse() {

    }
}
