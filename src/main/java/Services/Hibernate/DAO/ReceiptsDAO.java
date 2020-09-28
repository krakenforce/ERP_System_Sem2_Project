package Services.Hibernate.DAO;

import Repositories.IListBehavior;
import Services.Hibernate.entity.Receipts;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class ReceiptsDAO implements IListBehavior {
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

    public List<Receipts> searchByDate(Date startDay, Date endDay){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Receipts> list = null;

        try{
            session.beginTransaction();
            hql = "FROM Receipts WHERE date BETWEEN : startDay AND :endDay";
            Query query = session.createQuery(hql);
            query.setParameter("startDay",startDay);
            query.setParameter("endDay", endDay);
            list = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
        }

        return list;
    }

    @Override
    public List<Receipts> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Receipts> list = null;

        try{
            session.beginTransaction();
            hql = "FROM Receipts";
            Query query = session.createQuery(hql);
            list = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return list;
    }

    public Long countReceiptByDate(Date startDay, Date endDay){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        long count = 0;

        try{
            session.beginTransaction();
            hql = "SELECT COUNT(*) FROM Receipts WHERE date BETWEEN : startDay AND :endDay";
            Query query = session.createQuery(hql);
            query.setParameter("startDay",startDay);
            query.setParameter("endDay", endDay);
            count = (long) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return count;
    }

    public Long countReceipt(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        long count = 0;

        try{
            session.beginTransaction();
            hql = "SELECT COUNT(*) FROM Receipts";
            Query query = session.createQuery(hql);
            count = (long) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return count;
    }
}
