package Repositories;

import Services.Hibernate.entity.BillWarehousing;

import java.util.List;

public interface BillWarehousingDao {

    List<BillWarehousing> getBillWarehousing();
    void update(int bill_id);
    void findById(int bill_id);
    void addBill(int bill_id);

}
