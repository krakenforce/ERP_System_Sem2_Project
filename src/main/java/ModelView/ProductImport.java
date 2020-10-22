package ModelView;

public class ProductImport {
    private Long id_Product;
    private String tenSp;
    private Long soLuong;
    private Long giaNhaCungCap;

    public Long getGiaNhaCungCap() {
        return giaNhaCungCap;
    }

    public void setGiaNhaCungCap(Long giaNhaCungCap) {
        this.giaNhaCungCap = giaNhaCungCap;
    }

    public ProductImport() {
    }

    public Long getId_Product() {
        return id_Product;
    }

    public void setId_Product(Long id_Product) {
        this.id_Product = id_Product;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
}
