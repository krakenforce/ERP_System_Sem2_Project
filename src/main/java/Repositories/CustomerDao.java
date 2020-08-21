package Repositories;

import Services.Hibernate.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CustomerDao {
    public Long addCustomer(String name, String phone, int salesman); // return id of customer just added
    public List<Customer> getAllCustomers();
}
