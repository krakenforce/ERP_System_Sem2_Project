package Services.Hibernate.entity;

import java.util.HashSet;
import java.util.Set;

public abstract class Warehouse {
    protected Long id;

    protected String address;

    protected Set<BillWarehousing> billWarehousingSet = new HashSet<BillWarehousing>(0);
}
