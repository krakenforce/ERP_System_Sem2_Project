package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Unit {

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

    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
