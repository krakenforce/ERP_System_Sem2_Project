package Services.Hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cental_warehouse")
public class CentalWarehouse extends Warehouse {

    private int id;

}
