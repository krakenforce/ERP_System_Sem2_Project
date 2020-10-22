package Services.Hibernate.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("WarehouseOfStore")
public class WarehouseOfStore extends Warehouse{

    public WarehouseOfStore(String address, Set<BillWarehousing> billWarehousingSet) {
        super(address, billWarehousingSet);

    }

    public WarehouseOfStore() {
    }
}
