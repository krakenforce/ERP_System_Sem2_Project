package Services.Hibernate.DAO;

import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Discount;
import Services.Hibernate.entity.Order;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class OrderDAO {
    public OrderDAO(){

    }

    public void saveOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public Order findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Order order = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            order = (Order) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return order;
        }
    }

    public List<Order> findByDetailOrderID(Long detailOrderID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.detailOrder.id = :detailOrderID";
            Query query = session.createQuery(hql);
            query.setParameter("detailOrderID", detailOrderID );
            orderList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return orderList;
        }
    }

}
