package Services.Hibernate.DAO;

import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Discount;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;

public class DiscountDAO {

    public DiscountDAO(){

    }

    public void saveDiscount(Discount discount){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(discount);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateDiscount(Discount discount){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(discount);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteDiscount(Discount discount){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(discount);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public Discount findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Discount discount = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Discount session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            discount = (Discount) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return discount;
        }
    }
    public Discount findByDate(Date date){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Discount discount = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM DetailOrder session WHERE session.date = :date";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            discount = (Discount) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return discount;
        }
    }
}
