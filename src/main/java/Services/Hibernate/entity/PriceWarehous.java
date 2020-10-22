package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "PriceWarehous")
public class PriceWarehous implements Serializable {

    private static final long serialVersionUID = 4769844261269713577L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name  = "price")
    private Long price;

    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_PriceWarehous_Product"))
    private Product product;

    public PriceWarehous() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
}
