package Repositories;

import Services.Hibernate.entity.Salesman;

import java.util.ArrayList;

public interface SalesmanDao {
    public ArrayList<Salesman> getAllSalesman();
}
