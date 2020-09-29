package Services.Hibernate.DAO;

import Repositories.TradeDiscountsDao;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.TradeDiscounts;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class TradeDiscountDaoImpl implements TradeDiscountsDao {
    public TradeDiscountDaoImpl(){

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

    public List<TradeDiscounts> findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<TradeDiscounts> tradeDiscounts = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM TradeDiscounts session WHERE session.name LIKE :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", "%" + name + "%");
            tradeDiscounts = query.getResultList();
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

    @Override
    public List<TradeDiscounts> getAllTradeDiscounts() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<TradeDiscounts> tradeDiscounts = null;
        String hql = "";

        try {
            session.beginTransaction();
            hql = "from TradeDiscounts ";
            Query query = session.createQuery(hql);
            tradeDiscounts = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tradeDiscounts;

    }

    @Override
    public Long addTradeDiscount(String name) {
        TradeDiscounts td = new TradeDiscounts();
        td.setName(name);
        saveTradeDiscount(td);
        return td.getId();
    }

    @Override
    public Long addTradeDiscount(String name, Date start, Date end, Long discount, Long limit) {
        TradeDiscounts td = new TradeDiscounts();


        td.setName(name);
        td.setDateStars(start);
        td.setDateEnd(end);
        td.setDiscountPercentage(discount);
        td.setLimitMoney(limit);

        saveTradeDiscount(td);
        return td.getId();
    }
}
