package Services.Hibernate.DAO;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Salesman_GroupProduct;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class Salesman_ProductGroupDAO {
    public Salesman_ProductGroupDAO(){

    }
    public void saveGroup(Salesman_GroupProduct group){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public void updateGroup(Salesman_GroupProduct group){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(group);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public void deleteGroup(Salesman_GroupProduct group){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(group);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public Salesman_GroupProduct selectBySalesmanAndGroupProductID(Long salesmanID, Long groupProductID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Salesman_GroupProduct obj = null;
        String hql = "";

        try{
            hql = "FROM Salesman_GroupProduct session WHERE salesman.id = :salesmanID AND groupProduct.id = :groupProductID";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            query.setParameter("groupProductID", groupProductID);
            obj = (Salesman_GroupProduct) query.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
            return obj;
        }
    }

    public List<Salesman_GroupProduct> selectBySalesmanID(Long salesmanID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Salesman_GroupProduct> groupList = null;
        String hql = "";

        try{
            hql = "FROM Salesman_GroupProduct session WHERE salesman.id = :salesmanID";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            groupList = query.getResultList();
            session.getTransaction();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            session.close();
            return groupList;
        }
    }
}
