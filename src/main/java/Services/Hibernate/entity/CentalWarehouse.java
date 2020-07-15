package Services.Hibernate.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@DiscriminatorValue("cental_warehouse")
public class CentalWarehouse extends Warehouse {

    public CentalWarehouse(String address, Set<BillWarehousing> billWarehousingSet) {
        super(address, billWarehousingSet);
    }

    public CentalWarehouse() {
        super();
    }

}
