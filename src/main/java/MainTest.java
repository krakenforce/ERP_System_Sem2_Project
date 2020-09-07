import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.DAO.Salesman_ProductGroupDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.entity.Salesman_GroupProduct;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Form/SalesmanModule/SalesManList.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)  {
        launch(args);
        //testAddCustomer();
    }

    public static void testAddGroupProduct(){
        GroupProduct groupProduct = new GroupProduct();
        groupProduct.setName("day dien");
        groupProduct.setCommission(55.2);
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        groupProductDAO.saveGroupProduct(groupProduct);
    }

    public static void testGetGroupProduct(){
        Salesman_ProductGroupDAO dao = new Salesman_ProductGroupDAO();

        List<Salesman_GroupProduct> list = dao.selectBySalesmanID((long) 1);
        for(Salesman_GroupProduct items : list){
            System.out.println(items.getSalesman().getId());
            System.out.println(items.getGroupProduct().getId());
            System.out.println(items.getSalesman().getName());
            System.out.println(items.getGroupProduct().getName());
        }
    }

    public static void testAddCustomer(){
        //Test add customer
        SalesManDAO dao = new SalesManDAO();
        Salesman salesman = dao.findById((long) 2);

        Customer customer = new Customer();
        customer.setName("customer 4");
        customer.setPhone("793475");
        customer.setAddress("china");
        customer.setSalesman(salesman);

        CustomerDAO cusDao = new CustomerDAO();
        cusDao.saveCustomer(customer);
    }

    public static void getCustomerBySalesmanId(Long salesmanId){

        CustomerDAO cusDao = new CustomerDAO();
        List<Customer> list = cusDao.findBySalesmanID(salesmanId);
        for(Customer items : list){
            System.out.println(items.getName());
        }


    }
}
