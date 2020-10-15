package Services.Hibernate.EntityCombination;

public class GroupProductDetailStatistic {

    Long productID, soldAmount;
    String productName;

    public GroupProductDetailStatistic(Long productID, Long soldAmount, String productName) {
        this.productID = productID;
        this.soldAmount = soldAmount;
        this.productName = productName;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Long soldAmount) {
        this.soldAmount = soldAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
