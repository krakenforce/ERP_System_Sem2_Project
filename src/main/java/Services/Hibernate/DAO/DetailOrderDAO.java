package Services.Hibernate.DAO;

import Repositories.IListBehavior;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class DetailOrderDAO implements IListBehavior {
    public DetailOrderDAO(){

    }

    public void saveDetailOrder(DetailOrder detailOrder){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(detailOrder);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateDetailOrder(DetailOrder detailOrder){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(detailOrder);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteDetailOrder(DetailOrder detailOrder){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(detailOrder);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public DetailOrder findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        DetailOrder detailOrder = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM DetailOrder session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            detailOrder = (DetailOrder) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return detailOrder;
        }
    }
    public DetailOrder findByDate(Date date){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        DetailOrder detailOrder = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM DetailOrder session WHERE session.date = :date";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            detailOrder = (DetailOrder) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return detailOrder;
        }
    }


    @Override
    public List<DetailOrder> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<DetailOrder> list = null;

        try{
            session.beginTransaction();
            hql = "FROM DetailOrder " ;
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

    public List<DetailOrder> findByDateRange(Date startDate, Date endDate) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<DetailOrder> list = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM DetailOrder session WHERE session.date BETWEEN :startDate AND :endDate" ;
            Query query = session.createQuery(hql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
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

    public Long countDetailOrderByCustomerID(Long customerID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Long count = null;

        try{
            session.beginTransaction();
            hql = "SELECT count (*) FROM DetailOrder WHERE customer.id = :customerID GROUP BY customer.id";
            Query query = session.createQuery(hql);
            query.setParameter("customerID", customerID);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return count;
    }

    public List<DetailOrder> findByCustomerID(Long customerID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<DetailOrder> list = null;

        try{
            session.beginTransaction();
            hql = "FROM DetailOrder WHERE customer.id = :customerID " ;
            Query query = session.createQuery(hql);
            query.setParameter("customerID", customerID);
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
}
