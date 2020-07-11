package Services.Hibernate.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

public abstract class Warehouse {
    protected Long id;

    protected String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    protected Set<BillWarehousing> billWarehousingSet = new HashSet<BillWarehousing>(0);
}
