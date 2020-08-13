package Services.Hibernate.DAO;

import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.Receipts;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;

public class ReceiptsDAO {
    public ReceiptsDAO(){

    }

    public void saveReceipt(Receipts receipts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(receipts);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateReceipt(Receipts receipts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(receipts);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteReceipt(Receipts receipts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(receipts);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public Receipts findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Receipts receipts = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Receipts session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            receipts = (Receipts) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return receipts;
        }
    }

    public Receipts findByDate(Date date){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Receipts receipts = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Receipts session WHERE session.date = :date";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            receipts = (Receipts) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return receipts;
        }
    }
}
