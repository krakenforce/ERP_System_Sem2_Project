package Services.Hibernate.DAO;

import Services.Hibernate.entity.WarehousingDetails;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class WarehousingDetailDAO {

    public WarehousingDetailDAO(){

    }

    public void saveWarehousingDetail(WarehousingDetails warehousingDetails){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(warehousingDetails);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateWarehousingDetail(WarehousingDetails warehousingDetails){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(warehousingDetails);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteWarehousingDetail(WarehousingDetails warehousingDetails){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(warehousingDetails);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public WarehousingDetails findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        WarehousingDetails warehousingDetails = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM WarehousingDetails session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            warehousingDetails = (WarehousingDetails) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetails;
        }
    }

    public WarehousingDetails findByProductId(Long productID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        WarehousingDetails warehousingDetails = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM WarehousingDetails session WHERE session.product.id = :productID";
            Query query = session.createQuery(hql);
            query.setParameter("productID", productID);
            warehousingDetails = (WarehousingDetails) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetails;
        }
    }

}
