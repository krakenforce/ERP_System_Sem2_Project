package Controller.module2;

import Boxes.AddDiscount;
import Services.Hibernate.DAO.TradeDiscountDaoImpl;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class AddDiscountController {
    private AddDiscount mainBox;
    private TradeDiscountDaoImpl ti = new TradeDiscountDaoImpl();

    public TextField nameField;
    public TextField limitField;
    public TextField percentField;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public Button okBtn;
    public Label promptLabel;



    public void addDiscount(ActionEvent event) {
        TradeDiscounts t = new TradeDiscounts();
        try {
            String name = nameField.getText();
            if (name.isBlank()) {
                return;
            }

            Long limit = Long.parseLong(limitField.getText());
            Long percent = Long.parseLong(percentField.getText());

            if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
                Date s = Date.valueOf(startDatePicker.getValue());
                Date e = Date.valueOf(endDatePicker.getValue());
                if (s.before(Date.valueOf(LocalDate.now())) || s.after(e)) {
                    promptLabel.setText("Invalid Dates");
                    return;
                }
                t.setDateEnd(e);
                t.setDateStars(s);

            }



            t.setName(name);
            t.setLimitMoney(limit);
//            t.setPercent
            ti.saveTradeDiscount(t);


            promptLabel.setText("Successful!");

        } catch (NumberFormatException e) {
            promptLabel.setText("Invalid Number!");
        }

    }

    public void setBox(AddDiscount box) {
        this.mainBox = box;
    }
}
