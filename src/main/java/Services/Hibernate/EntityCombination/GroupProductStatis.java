package Services.Hibernate.EntityCombination;

public class GroupProductStatis {
    String name;
    Long id, totalSold;

    public GroupProductStatis(String name, Long id, Long totalSold) {
        this.name = name;
        this.id = id;
        this.totalSold = totalSold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Long totalSold) {
        this.totalSold = totalSold;
    }
}
