package Services.Hibernate.EntityCombination;

import java.sql.Date;

public class TradeDiscountPayment {
    Long tradeDiscountID, payment;
    Date startDate, endDate;

    public Long getTradeDiscountID() {
        return tradeDiscountID;
    }

    public void setTradeDiscountID(Long tradeDiscountID) {
        this.tradeDiscountID = tradeDiscountID;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
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
