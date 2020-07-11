package Services.Hibernate.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = -4581675898265303366L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "salesman")
    @JoinColumn(name = "salesman_id", foreignKey = @ForeignKey(name = "fk_salesman"))
    private Salesman salesman;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "customer")
    @JoinColumn(name = "tradeDiscounts_id", foreignKey = @ForeignKey(name = "fk_tradeDiscount"))
    private Set<TradeDiscounts> tradeDiscountsSet = new HashSet<TradeDiscounts>(0);

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "customer")
    @JoinColumn(name = "detail_order_id", foreignKey = @ForeignKey(name = "fk_detail_order"))
    private Set<DetailOrder> detailOrderSet = new HashSet<DetailOrder>(0);


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public Set<TradeDiscounts> getTradeDiscountsSet() {
        return tradeDiscountsSet;
    }

    public void setTradeDiscountsSet(Set<TradeDiscounts> tradeDiscountsSet) {
        this.tradeDiscountsSet = tradeDiscountsSet;
    }

    public Set<DetailOrder> getDetailOrderSet() {
        return detailOrderSet;
    }

    public void setDetailOrderSet(Set<DetailOrder> detailOrderSet) {
        this.detailOrderSet = detailOrderSet;
    }
}
