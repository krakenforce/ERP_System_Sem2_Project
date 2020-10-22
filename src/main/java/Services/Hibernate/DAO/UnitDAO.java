package Services.Hibernate.DAO;

import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Unit;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UnitDAO {
    public UnitDAO(){

    }

    public void saveUnit(Unit unit){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(unit);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateUnit(Unit unit){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(unit);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteUnit(Unit unit){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(unit);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public Unit findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Unit unit = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Unit session WHERE session.id = :id ";
            Query query = session.createQuery(hql);
            unit = (Unit) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return unit;
        }
    }

    public List<Unit> findAll() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Unit> list = null;

        try{
            session.beginTransaction();
            hql = "FROM Unit " ;
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

}
