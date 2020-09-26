package Repositories;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.TradeDiscounts;

import java.util.ArrayList;
import java.util.List;

public interface CustomerDao {
    public Long addCustomer(String name, String phone, Long salesman); // return id of customer just added
    public List<Customer> getAllCustomers();

    public List<TradeDiscounts> getAllDiscounts(Long id);
}
