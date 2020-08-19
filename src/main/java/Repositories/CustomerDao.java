package Repositories;

import Services.Hibernate.entity.Customer;

import java.awt.*;
import java.util.ArrayList;

public interface CustomerDao {
    public int addCustomer(String name, String phone, int Salesman); // return id of customer just added
    public ArrayList<Customer> getAllCustomers();
}
