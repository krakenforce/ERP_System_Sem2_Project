package Services.Hibernate.EntityCombination;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;

import java.sql.Date;

public class DetailOrderCustomer {
    Long detailOrderID,salesmanID, customerID, debt, total, count, totalSpent, amountOfInvoice, totalDebt;
    String customerName, salesmanName;
    Date date;
    Boolean status;
    DetailOrder detailOrder;
    Customer customer;

    public DetailOrderCustomer() {

    }

    public DetailOrderCustomer(Long salesmanID, Long amountOfInvoice, String salesmanName, Long total) {
        this.salesmanID = salesmanID;
        this.amountOfInvoice = amountOfInvoice;
        this.salesmanName = salesmanName;
        this.total = total;
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

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public DetailOrder getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrder detailOrder) {
        this.detailOrder = detailOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Long totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Long getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(Long salesmanID) {
        this.salesmanID = salesmanID;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Long getAmountOfInvoice() {
        return amountOfInvoice;
    }

    public void setAmountOfInvoice(Long amountOfInvoice) {
        this.amountOfInvoice = amountOfInvoice;
    }

    public Long getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Long totalDebt) {
        this.totalDebt = totalDebt;
    }
}
