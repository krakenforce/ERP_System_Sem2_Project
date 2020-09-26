package Services.Hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="DeliveryDetails")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DeliveryDetails_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class DeliveryDetails implements Serializable {

    private static final long serialVersionUID = 402523884880669487L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "warehousingDetails_id", foreignKey = @ForeignKey(name = "fk_warehousingDetails_DeliveryDetails"))
    protected WarehousingDetails warehousingDetails;

    public DeliveryDetails(WarehousingDetails warehousingDetails) {
        this.warehousingDetails = warehousingDetails;
    }

    public DeliveryDetails() {
    }
}
