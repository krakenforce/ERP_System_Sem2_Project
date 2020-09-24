package Services.Hibernate.EntityCombination;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Receipts;

import java.sql.Date;

public class ReceiptDetailOrderCustomer {
    Receipts receipts;
    Customer customer;
    DetailOrder detailOrder;
    Long receiptID, detailOrderID, customerID, moneyPay, totalCost, remaining;
    Date date;
    String customerName;

    public ReceiptDetailOrderCustomer(Receipts receipts, Customer customer, DetailOrder detailOrder) {
        this.receipts = receipts;
        this.customer = customer;
        this.detailOrder = detailOrder;
    }

    public Receipts getReceipts() {
        return receipts;
    }

    public void setReceipts(Receipts receipts) {
        this.receipts = receipts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DetailOrder getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrder detailOrder) {
        this.detailOrder = detailOrder;
    }

    public Long getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(Long receiptID) {
        this.receiptID = receiptID;
    }

    public Long getDetailOrderID() {
        return detailOrderID;
    }

    public void setDetailOrderID(Long detailOrderID) {
        this.detailOrderID = detailOrderID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getMoneyPay() {
        return moneyPay;
    }

    public void setMoneyPay(Long moneyPay) {
        this.moneyPay = moneyPay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setRemaining(Long remaining) {
        this.remaining = remaining;
    }
}
