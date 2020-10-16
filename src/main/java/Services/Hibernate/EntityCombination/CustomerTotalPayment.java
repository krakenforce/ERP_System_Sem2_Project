package Services.Hibernate.EntityCombination;

public class CustomerTotalPayment {
    Long customerID, totalPayment;
    String customerName;

    public CustomerTotalPayment(){

    }

    public CustomerTotalPayment(Long customerID, Long totalPayment, String customerName) {
        this.customerID = customerID;
        this.totalPayment = totalPayment;
        this.customerName = customerName;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Long totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
