package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name  = "price")
    private Long price;

    @Column(name  = "minimum_inventory")
    private Long minimumInventory;

    @Column(name  = "rate_profit")
    private Double rateProfit;

    @Column(name  = "retail_price")
    private Long retailPrice;

    private GroupProduct groupProduct;

    private Set<WarehousingDetails> warehousingDetailsSet = new HashSet<WarehousingDetails>(0);

    private Set<Discount> discountSet = new HashSet<Discount>(0);

    private Set<Order> orderSet = new HashSet<Order>(0);

    private Unit unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getMinimumInventory() {
        return minimumInventory;
    }

    public void setMinimumInventory(Long minimumInventory) {
        this.minimumInventory = minimumInventory;
    }

    public Double getRateProfit() {
        return rateProfit;
    }

    public void setRateProfit(Double rateProfit) {
        this.rateProfit = rateProfit;
    }

    public Long getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Long retailPrice) {
        this.retailPrice = retailPrice;
    }

    public GroupProduct getGroupProduct() {
        return groupProduct;
    }

    public void setGroupProduct(GroupProduct groupProduct) {
        this.groupProduct = groupProduct;
    }

    public Set<WarehousingDetails> getWarehousingDetailsSet() {
        return warehousingDetailsSet;
    }

    public void setWarehousingDetailsSet(Set<WarehousingDetails> warehousingDetailsSet) {
        this.warehousingDetailsSet = warehousingDetailsSet;
    }

    public Set<Discount> getDiscountSet() {
        return discountSet;
    }

    public void setDiscountSet(Set<Discount> discountSet) {
        this.discountSet = discountSet;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
