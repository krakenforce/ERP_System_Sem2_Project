package Services.Hibernate.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("WarehouseOfStore")
public class WarehouseOfStore extends Warehouse{

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "warehouseOfStore")
    private Set<DeliveryDetailsOfDisplay> deliveryDetailsOfDisplaySet = new HashSet<DeliveryDetailsOfDisplay>(0);


    public WarehouseOfStore(String address, Set<BillWarehousing> billWarehousingSet, Set<DeliveryDetailsOfDisplay> deliveryDetailsOfDisplaySet) {
        super(address, billWarehousingSet);
        this.deliveryDetailsOfDisplaySet = deliveryDetailsOfDisplaySet;
    }

    public WarehouseOfStore() {
    }
}
