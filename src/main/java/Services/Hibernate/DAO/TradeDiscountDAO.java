package Services.Hibernate.DAO;

import Repositories.Function;
import Repositories.IListBehavior;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.TradeDiscounts;
import Services.Hibernate.utils.HibernateUtil;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class TradeDiscountDAO implements IListBehavior {
    public TradeDiscountDAO(){

    }

    public void saveTradeDiscount(TradeDiscounts tradeDiscounts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(tradeDiscounts);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateTradeDiscount(TradeDiscounts tradeDiscounts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(tradeDiscounts);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteTradeDiscount(TradeDiscounts tradeDiscounts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(tradeDiscounts);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public TradeDiscounts findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        TradeDiscounts tradeDiscounts = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM TradeDiscounts session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            tradeDiscounts = (TradeDiscounts) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return tradeDiscounts;
        }
    }

    public TradeDiscounts findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        TradeDiscounts tradeDiscounts = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM TradeDiscounts session WHERE session.name LIKE :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            tradeDiscounts = (TradeDiscounts) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return tradeDiscounts;
        }
    }

    public TradeDiscounts findByDate(Date date){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        TradeDiscounts tradeDiscounts = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM TradeDiscounts session WHERE session.dateStars = :date";
            Query query = session.createQuery(hql);
            query.setParameter("dateStars", date);
            tradeDiscounts = (TradeDiscounts) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return tradeDiscounts;
        }
    }

    public List<TradeDiscounts> getAll(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        List<TradeDiscounts> tradeDiscountsList = null;

        try{
            session.beginTransaction();
            hql = "FROM TradeDiscounts ";
            Query query = session.createQuery(hql);
            tradeDiscountsList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return tradeDiscountsList;
        }
    }
}
