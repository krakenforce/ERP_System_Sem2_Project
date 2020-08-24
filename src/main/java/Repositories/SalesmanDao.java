package Repositories;

import Services.Hibernate.entity.Salesman;

import java.util.ArrayList;
import java.util.List;

public interface SalesmanDao {
    public Long addSalesman(String name, String address, String phone);
    public List<Salesman> getAllSalesman();
}
