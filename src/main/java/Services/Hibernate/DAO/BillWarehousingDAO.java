package Services.Hibernate.DAO;

import Services.Hibernate.entity.BillWarehousing;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class BillWarehousingDAO {
    public BillWarehousingDAO(){

    }

    public void saveBillWarehousing(BillWarehousing billWarehousing){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();

            session.save(billWarehousing);

            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateBillWarehousing(BillWarehousing billWarehousing){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(billWarehousing);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();

            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteBillWarehousing(BillWarehousing billWarehousing){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(billWarehousing);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public BillWarehousing findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        BillWarehousing billWarehousing = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "SELECT session FROM BillWarehousing session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);
            billWarehousing = (BillWarehousing) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return billWarehousing;
        }
    }
}
