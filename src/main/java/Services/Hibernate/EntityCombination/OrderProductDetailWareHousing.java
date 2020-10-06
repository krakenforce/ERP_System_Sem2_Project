package Services.Hibernate.EntityCombination;

import Services.Hibernate.entity.Product;
import javafx.scene.text.Text;

public class OrderProductDetailWareHousing {

    String productName;
    long productID, amount, price, total, salePrice;
    Boolean enoughStatus;
    Text enough;
    Product product;

    public OrderProductDetailWareHousing(){

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Boolean getEnoughStatus() {
        return enoughStatus;
    }

    public void setEnoughStatus(Boolean enoughStatus) {
        this.enoughStatus = enoughStatus;
    }

    public Text getEnough() {
        return enough;
    }

    public void setEnough(Text enough) {
        this.enough = enough;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }
}
