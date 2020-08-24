package Controller.module2;

public class Item {
    private Long id;


    private Long qty;
    private String name;
    private Long price;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    private Long total;

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Item(Long id, Long qty, String name, Long price, Long total) {
        this.qty = qty;
        this.name = name;
        this.price = price;
        this.total = total;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQty() {
        return qty;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public Long getTotal() {
        return total;
    }
}
