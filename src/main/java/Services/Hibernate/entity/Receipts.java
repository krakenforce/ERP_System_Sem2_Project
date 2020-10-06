package Services.Hibernate.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "receipt")
public class Receipts implements Serializable {

    private static final long serialVersionUID = 2145327346230996501L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "payer_name")
    private String payerName;

    @Column(name = "address")
    private String address;

    @Column(name = "reason")
    private String reason;


    @Type(type="org.hibernate.type.DateType")
    @Column(name = "date")
    private Date date;

    @Column(name = "money_Pay")
    private Long moneyPay;

    @Column(name = "money_Pay_In_Word")
    private String moneyPayInWords;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "detail_order_id", foreignKey = @ForeignKey(name = "fk_detail_order_Receipts"))
    private DetailOrder detailOrder;

    public Receipts(Date date, DetailOrder detailOrder) {
        this.date = date;
        this.detailOrder = detailOrder;
    }

    public Receipts() {
    }

    public Receipts(Long receiptID, Date date, Long moneyPay) {
        this.id = receiptID;
        this.date = date;
        this.moneyPay = moneyPay;
    }

    public Receipts(String payerName, String address, String reason, Long moneyPay, String moneyInWord, Date date, DetailOrder detailOrder) {
        this.payerName = payerName;
        this.address = address;
        this.reason = reason;
        this.moneyPay = moneyPay;
        this.moneyPayInWords = moneyInWord;
        this.date = date;
        this.detailOrder = detailOrder;
    }

    public Long getMoneyPay() {
        return moneyPay;
    }

    public void setMoneyPay(Long moneyPay) {
        this.moneyPay = moneyPay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DetailOrder getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrder detailOrder) {
        this.detailOrder = detailOrder;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoneyPayInWords() {
        return moneyPayInWords;
    }

    public void setMoneyPayInWords(String moneyPayInWords) {
        this.moneyPayInWords = moneyPayInWords;
    }
}
