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
    @Column(name = "name", length = 50, nullable = false)
    protected String name;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<BillWarehousing> getBillWarehousingSet() {
        return billWarehousingSet;
    }

    public void setBillWarehousingSet(Set<BillWarehousing> billWarehousingSet) {
        this.billWarehousingSet = billWarehousingSet;
    }

    @Override
    public String toString() {
        return name;
    }
}
