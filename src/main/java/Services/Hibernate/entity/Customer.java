package Services.Hibernate.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
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
    @Column(name = "address", length = 200, nullable = false)
    private String address;



    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "salesman_id", foreignKey = @ForeignKey(name = "fk_salesman_customer"))
    private Salesman salesman;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "customer")
    private Set<Payment> paymentSet = new HashSet<Payment>(0);

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "customer")
    private Set<DetailOrder> detailOrderSet = new HashSet<DetailOrder>(0);


    public Customer(String name, String phone, String address, Salesman salesman, Set<Payment> paymentSet, Set<DetailOrder> detailOrderSet) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.salesman = salesman;
        this.paymentSet = paymentSet;
        this.detailOrderSet = detailOrderSet;
    }

    public Customer() {
    }

    public Customer(Long id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Customer(Long id, String name) {
        this.id = id;
        this.name =  name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public Set<DetailOrder> getDetailOrderSet() {
        return detailOrderSet;
    }

    public void setDetailOrderSet(Set<DetailOrder> detailOrderSet) {
        this.detailOrderSet = detailOrderSet;
    }

    public Set<Payment> getPaymentSet() {
        return paymentSet;
    }

    public void setPaymentSet(Set<Payment> paymentSet) {
        this.paymentSet = paymentSet;
    }

    //    public Long totalSpent(Long customerID, Date fromDate, Date toDate){
//        Long tongTien = (long) 0;
//        for(DetailOrder detailOrder : detailOrderSet){
//            if(!detailOrder.getPay()){
//                if(fromDate.after(detailOrder.getDate()) && toDate.before(detailOrder.getDate())){
//                    tongTien = tongTien + detailOrder.tinhTongTienDetailOrder();
//                }
//            }
//        }
//        return tongTien;
//    }
//
//    public Long totalSpent(Long customerID){
//        Long tongTien = (long) 0;
//        for(DetailOrder detailOrder : detailOrderSet){
//            if(!detailOrder.getPay()){
//                tongTien = tongTien + detailOrder.tinhTongTienDetailOrder();
//            }
//
//        }
//        return tongTien;
//    }
}
