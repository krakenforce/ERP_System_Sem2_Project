package Services.Hibernate.EntityCombination;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;

import java.sql.Date;

public class PaymentTradeCustomer {
    Payment payment;
    TradeDiscounts tradeDiscounts;
    Customer customer;
    Long paymentId,customerID,moneyLimit, money;
    Date paymentDate,startDate,endDate;
    String tradeDiscountName,customerName;


    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(Long moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTradeDiscountName() {
        return tradeDiscountName;
    }

    public void setTradeDiscountName(String tradeDiscountName) {
        this.tradeDiscountName = tradeDiscountName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public PaymentTradeCustomer(){

    };

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public PaymentTradeCustomer(Payment payment, TradeDiscounts tradeDiscounts, Customer customer) {
        this.payment = payment;
        this.tradeDiscounts = tradeDiscounts;
        this.customer = customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public TradeDiscounts getTradeDiscounts() {
        return tradeDiscounts;
    }

    public void setTradeDiscounts(TradeDiscounts tradeDiscounts) {
        this.tradeDiscounts = tradeDiscounts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
