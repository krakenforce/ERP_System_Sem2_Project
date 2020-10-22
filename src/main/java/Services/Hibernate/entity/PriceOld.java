package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "PriceOld")
public class PriceOld implements Serializable {

    private static final long serialVersionUID = 4966849898264108272L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name  = "priceImport")
    private Long priceImport;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_PriceOld_Product"))
    private Product product;

    public PriceOld() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceImport() {
        return priceImport;
    }

    public void setPriceImport(Long priceImport) {
        this.priceImport = priceImport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "PriceOld{" +
                "id=" + id +
                ", priceImport=" + priceImport +
                ", date=" + date +
                ", product=" + product +
                '}';
    }
}
