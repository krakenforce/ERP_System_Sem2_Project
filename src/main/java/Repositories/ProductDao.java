package Repositories;

import Services.Hibernate.entity.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface ProductDao {
    public List<Product> getAllProducts();
    public void addProduct(String name, Long price, Long unit);
    public int amountNeeded(Long productId, int buyingAmount); // return 0 if there is enough products. Otherwise return the amount needed.
}
