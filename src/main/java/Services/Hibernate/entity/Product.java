package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

    private static final long serialVersionUID = 859022454278766265L;

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

    @Column(name = "bar_code")
    private String barCode;


    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "group_product_id", foreignKey = @ForeignKey(name = "fk_group_product_Product"))
    private GroupProduct groupProduct;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "unit_id", foreignKey = @ForeignKey(name = "fk_Unit_Product") )
    private Unit unit;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "product")
    private Set<PriceWarehous> priceWarehousSet = new HashSet<PriceWarehous>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "product")
    private Set<TranportFee> tranportFeesSet = new HashSet<TranportFee>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "product")
    private Set<PriceOld> priceOldHashSet = new HashSet<PriceOld>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "product")
    private Set<WarehousingDetails> warehousingDetailsSet = new HashSet<WarehousingDetails>(0);

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "product")
    private Set<Discount> discountSet = new HashSet<Discount>(0);

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "product")
    private Set<Order> orderSet = new HashSet<Order>(0);



    public Product() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Set<PriceWarehous> getPriceWarehousSet() {
        return priceWarehousSet;
    }

    public void setPriceWarehousSet(Set<PriceWarehous> priceWarehousSet) {
        this.priceWarehousSet = priceWarehousSet;
    }

    public Set<TranportFee> getTranportFeesSet() {
        return tranportFeesSet;
    }

    public void setTranportFeesSet(Set<TranportFee> tranportFeesSet) {
        this.tranportFeesSet = tranportFeesSet;
    }

    public Set<PriceOld> getPriceOldHashSet() {
        return priceOldHashSet;
    }

    public void setPriceOldHashSet(Set<PriceOld> priceOldHashSet) {
        this.priceOldHashSet = priceOldHashSet;
    }
}
