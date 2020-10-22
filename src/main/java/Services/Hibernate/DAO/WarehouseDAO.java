package Services.Hibernate.DAO;

import Services.Hibernate.entity.Warehouse;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class WarehouseDAO {

    public WarehouseDAO() {
    }
    public void saveWarehouse(Warehouse warehouse){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(warehouse);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public List<Warehouse> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Warehouse> list = null;

        try{
            session.beginTransaction();
            hql = "FROM Warehouse " ;
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
