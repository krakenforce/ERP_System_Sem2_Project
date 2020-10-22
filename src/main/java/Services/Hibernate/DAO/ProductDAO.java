package Services.Hibernate.DAO;

import Repositories.IListBehavior;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.WarehousingDetails;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IListBehavior {
    public ProductDAO(){

    }

    public void saveProduct(Product product){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateProduct(Product product){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteProduct(Product product){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public Product findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Product product = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            product = (Product) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return product;
        }
    }

    public Product findByBarcode(String barcode){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Product product = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.barCode = :barcode";
            Query query = session.createQuery(hql);
            query.setParameter("barcode", barcode);
            product = (Product) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return product;
        }
    }

    public Product findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Product product = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.name = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            product = (Product) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return product;
        }
    }

    public Product findByPrice(Long price){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Product product = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.price = :price";
            Query query = session.createQuery(hql);
            query.setParameter("price", price);
            product = (Product) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return product;
        }

    }

    public List<Product> findByGroupProductID(Long groupProductID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Product> productList = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.groupProduct.id = :groupProductID";
            Query query = session.createQuery(hql);
            query.setParameter("groupProductID", groupProductID );
            productList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return productList;
        }
    }

    @Override
    public List<Product> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Product> list = null;

        try{
            session.beginTransaction();
            hql = "FROM Product " ;
            Query query = session.createQuery(hql);
            list = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return list;
    }

    public List<WarehousingDetails> getWarehouse(Product product){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<WarehousingDetails> warehousingDetailsList = new ArrayList<WarehousingDetails>();
        String hql = "";

        try{
            session.beginTransaction();

            hql = "select w\n" +
                    "from Product p inner join WarehousingDetails w on p.id = w.product.id\n" +
                    "inner join BillWarehousing bw on bw.id = w.billWarehousing.id\n" +
                    "where p.id = :id\n" +
                    "order by bw.date asc";
            Query query = session.createQuery(hql);
            query.setParameter("id", product.getId());
            warehousingDetailsList = (List<WarehousingDetails>) query.getResultList();


            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetailsList;
        }
    }

    public List<Product> GetListProductByName(String nameSeacrh) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Product> productList = null;
        String name = "%" + nameSeacrh + "%";
        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.name like :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            productList =  query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return productList;
        }
    }
}
