package Services.Hibernate.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DeliveryDetailsOfDisplay")
public class DeliveryDetailsOfDisplay extends DeliveryDetails {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "warehouseOfStore_id", foreignKey = @ForeignKey(name = "fk_warehouseOfStore_DeliveryDetailsOfDisplay"))
    private WarehouseOfStore warehouseOfStore;

    public DeliveryDetailsOfDisplay(WarehousingDetails warehousingDetails, WarehouseOfStore warehouseOfStore) {
        super(warehousingDetails);
        this.warehouseOfStore = warehouseOfStore;
    }

    public DeliveryDetailsOfDisplay() {
        super();

    }

}
