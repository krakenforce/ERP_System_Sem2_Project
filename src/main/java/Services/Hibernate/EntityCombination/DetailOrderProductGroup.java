package Services.Hibernate.EntityCombination;

import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Product;

import java.sql.Date;

public class DetailOrderProductGroup {
    DetailOrder detailOrder;
    Order order;
    Product product;
    GroupProduct groupProduct;
    Long productID, amount, detailOrderID, price, totalCost;
    Date detailOrderDate;
    Double commission;
    String productName;

    public DetailOrderProductGroup(DetailOrder detailOrder, Order order, Product product, GroupProduct groupProduct) {
        this.detailOrder = detailOrder;
        this.order = order;
        this.product = product;
        this.groupProduct = groupProduct;
    }

    public DetailOrder getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrder detailOrder) {
        this.detailOrder = detailOrder;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public GroupProduct getGroupProduct() {
        return groupProduct;
    }

    public void setGroupProduct(GroupProduct groupProduct) {
        this.groupProduct = groupProduct;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDetailOrderID() {
        return detailOrderID;
    }

    public void setDetailOrderID(Long detailOrderID) {
        this.detailOrderID = detailOrderID;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDetailOrderDate() {
        return detailOrderDate;
    }

    public void setDetailOrderDate(Date detailOrderDate) {
        this.detailOrderDate = detailOrderDate;
    }
}
