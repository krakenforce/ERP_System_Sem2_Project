package Controller.SalesManageModule;

import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Payment;

public class PaymentCreationController {

    Customer selectedCustomer;


    public void createPayment(){
        Payment payment = new Payment();
        PaymentDAO paymentDAO = new PaymentDAO();

        payment.setCustomer(selectedCustomer);

        paymentDAO.savePayment(payment);
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
