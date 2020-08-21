import Services.Hibernate.DAO.CustomerDaoImpl;
import Services.Hibernate.DAO.LoginInfoDAO;
import Services.Hibernate.DAO.SalesManDaoImpl;
import Services.Hibernate.DAO.UnitDaoImpl;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.entity.Salesman;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        CustomerDaoImpl ci = new CustomerDaoImpl();
        SalesManDaoImpl si = new SalesManDaoImpl();
        UnitDaoImpl ui = new UnitDaoImpl();
        Long l =  ui.addUnit("ex", "pri");
        System.out.println(l);

    }
}
