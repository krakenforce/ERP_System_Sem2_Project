import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.Salesman;

public class MainTest {
    public static void main(String[] args) {
        Salesman salesman = new Salesman();

        salesman.setName("ádasd");
        salesman.setAddress("sdfsdfsdf");
        salesman.setPhone("24324234");
        SalesManDAO salesManDao = new SalesManDAO();

        salesManDao.saveSalesMan(salesman);
        Salesman salesman1 = salesManDao.findById((long) 1);
        System.out.println(salesman1);
        salesman1.setName("dat3333");
        salesman.setAddress("asdasd");
        salesman.setPhone("9999999");
        salesManDao.updateSalesMan(salesman1);
        System.out.println(salesman1);
        salesManDao.deleteSalesMan(salesman1);
        salesman1 = salesManDao.findById((long) 1);
        System.out.println(salesman1);
    }
}
