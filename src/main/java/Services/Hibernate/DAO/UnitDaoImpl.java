package Services.Hibernate.DAO;

import Repositories.UnitDao;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Unit;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class UnitDaoImpl implements UnitDao {
    public UnitDaoImpl(){

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
            query.setParameter("id", id);
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

    @Override
    public Long addUnit(String exchange, String primary) {
        Unit u = new Unit();
        u.setUnitExchange(exchange);
        u.setUnitPrimary(primary);
        saveUnit(u);
        return u.getId();
    }
}
