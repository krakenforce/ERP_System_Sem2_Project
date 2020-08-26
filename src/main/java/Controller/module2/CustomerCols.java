package Controller.module2;

public class CustomerCols {
    private Long id;
    private String name;
    private String phone;
    private Long debt;
    private Long spent;

    public CustomerCols(Long id, String name, String phone, Long debt, Long spent) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.debt = debt;
        this.spent = spent;
    }

    public CustomerCols() {

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

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    public Long getSpent() {
        return spent;
    }

    public void setSpent(Long spent) {
        this.spent = spent;
    }
}
