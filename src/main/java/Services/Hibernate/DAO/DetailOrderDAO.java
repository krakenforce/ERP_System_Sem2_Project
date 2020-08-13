package Services.Hibernate.DAO;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode;

import javax.persistence.Query;
import java.sql.Date;

public class DetailOrderDAO {
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
}
