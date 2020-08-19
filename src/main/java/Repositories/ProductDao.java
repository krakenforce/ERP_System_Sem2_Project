package Repositories;

import Services.Hibernate.entity.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ProductDao {
    public ArrayList<Product> getAllProducts();
    public void addProduct(String name, String price, int unit);
    public int amountNeeded(int productId, int buyingAmount); // return 0 if there is enough products. Otherwise return the amount needed.
}
