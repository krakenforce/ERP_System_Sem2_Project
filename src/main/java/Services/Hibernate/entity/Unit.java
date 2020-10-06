package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "unit")
public class Unit implements Serializable {

    private static final long serialVersionUID = 8005054112062019759L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "unit_primary", length = 255, nullable = false)
    private String unitPrimary;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "unit_exchange", length = 255, nullable = false)
    private String unitExchange;

    @Column(name = "value_primary")
    private Long valuePrimary;

    @Column(name = "value_exchange")
    private Long valueExchange;

//    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    private Product product;

    public Unit(String unitPrimary, String unitExchange, Long valuePrimary, Long valueExchange, Product product) {
        this.unitPrimary = unitPrimary;
        this.unitExchange = unitExchange;
        this.valuePrimary = valuePrimary;
        this.valueExchange = valueExchange;
        //this.product = product;
    }

    public Unit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitPrimary() {
        return unitPrimary;
    }

    public void setUnitPrimary(String unitPrimary) {
        this.unitPrimary = unitPrimary;
    }

    public String getUnitExchange() {
        return unitExchange;
    }

    public void setUnitExchange(String unitExchange) {
        this.unitExchange = unitExchange;
    }

    public Long getValuePrimary() {
        return valuePrimary;
    }

    public void setValuePrimary(Long valuePrimary) {
        this.valuePrimary = valuePrimary;
    }

    public Long getValueExchange() {
        return valueExchange;
    }

    public void setValueExchange(Long valueExchange) {
        this.valueExchange = valueExchange;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
}
