package Controller.module2;

import Boxes.MakePayReceipt;
import Services.Hibernate.DAO.PaymentDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MakeAPayReceiptController {

    private MakePayReceipt box;
    public Long amountPaidSoFar;

    public TextField amountTextField;
    public Button okBtn;
    public Label promptLabel;



    public void setBox(MakePayReceipt makePayReceipt) {
        this.box = makePayReceipt;

    }

    public void makeAReceipt(ActionEvent event) {
        try {

            Long amount = Long.parseLong(amountTextField.getText());
//            PaymentDaoImpl pi = new PaymentDAO();
            /// waiting for database change, need column for the payment;
            // Long amountPaid =  pi.customerPay(Long customer, Long amount);
            // return the actually amount in case they pay over, if no debt return 0
            promptLabel.setText("success");
//            amountPaidSoFar += amountPaid;
        } catch (NumberFormatException e) {
            promptLabel.setText("Invalid Number");
        }
        PaymentDAO pi = new PaymentDAO();

    }
}
