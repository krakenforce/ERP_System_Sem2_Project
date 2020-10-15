package Services.Hibernate.EntityCombination;

import java.sql.Date;

public class TradeDiscountCustomer {
    Long tradeDiscountID, customerAmount, totalPayment;
    Date startDate, endDate;

    public TradeDiscountCustomer(Long tradeDiscountID, Long customerAmount, Long totalPayment, Date startDate, Date endDate) {
        this.tradeDiscountID = tradeDiscountID;
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
}
