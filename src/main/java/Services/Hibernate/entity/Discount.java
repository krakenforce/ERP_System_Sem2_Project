package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "discount")
public class Discount implements Serializable {

    private static final long serialVersionUID = -751434831335276808L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "money_Reduction")
    private Long moneyReduction;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date_start")
    private Date dateStarts;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date_end")
    private Date dateEnd;

    @Type(type = "true_false")
    @Column(name = "status")
    private Boolean status;


    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_Discount"))
    private Product product;

    public Discount(Long amount, Long moneyReduction, Date dateStarts, Date dateEnd, Boolean status, Product product) {
        this.amount = amount;
        this.moneyReduction = moneyReduction;
        this.dateStarts = dateStarts;
        this.dateEnd = dateEnd;
        this.status = status;
        this.product = product;
    }

    public Discount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getMoneyReduction() {
        return moneyReduction;
    }

    public void setMoneyReduction(Long moneyReduction) {
        this.moneyReduction = moneyReduction;
    }

    public Date getDateStarts() {
        return dateStarts;
    }

    public void setDateStarts(Date dateStarts) {
        this.dateStarts = dateStarts;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
