package Services.Hibernate.DAO;


import Services.Hibernate.entity.DeliveryBill;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;

public class DeliveryBillDAO {
    public DeliveryBillDAO(){

    }

    public void saveDeliveryBill(DeliveryBill deliveryBill){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.save(deliveryBill);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateDeliveryBill(DeliveryBill deliveryBill){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(deliveryBill);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteDeliveryBill(DeliveryBill deliveryBill){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(deliveryBill);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public DeliveryBill findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();
        DeliveryBill deliveryBill = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "SELECT session FROM DeliveryBill session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            deliveryBill = (DeliveryBill) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return deliveryBill;
        }
    }

    public DeliveryBill findByDate(Date date){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();
        DeliveryBill deliveryBill = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "SELECT session FROM DeliveryBill session WHERE session.date = :date";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            deliveryBill = (DeliveryBill) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return deliveryBill;
        }
    }
}
