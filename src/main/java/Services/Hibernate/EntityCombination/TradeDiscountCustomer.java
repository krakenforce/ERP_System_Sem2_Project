package Services.Hibernate.EntityCombination;

import Services.Hibernate.entity.Customer;

import java.sql.Date;

public class TradeDiscountCustomer {
    Customer customer;
    Long tradeDiscountID, customerAmount, totalPayment, customerID, totalSpent;
    Date startDate, endDate;
    String tradeDiscountName, customerName;


    public TradeDiscountCustomer(Long tradeDiscountID, String tradeDiscountName, Long customerAmount, Long totalPayment, Date startDate, Date endDate) {
        this.tradeDiscountID = tradeDiscountID;
        this.tradeDiscountName = tradeDiscountName;
        this.customerAmount = customerAmount;
        this.totalPayment = totalPayment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getTradeDiscountID() {
        return tradeDiscountID;
    }

    public void setTradeDiscountID(Long tradeDiscountID) {
        this.tradeDiscountID = tradeDiscountID;
    }

    public Long getCustomerAmount() {
        return customerAmount;
    }

    public void setCustomerAmount(Long customerAmount) {
        this.customerAmount = customerAmount;
    }

    public Long getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Long totalPayment) {
        this.totalPayment = totalPayment;
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

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Long totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
