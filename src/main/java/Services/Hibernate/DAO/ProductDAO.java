package Services.Hibernate.DAO;

import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class ProductDAO {
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

    public Product findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Product product = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Product session WHERE session.name LIKE :name";
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
}
