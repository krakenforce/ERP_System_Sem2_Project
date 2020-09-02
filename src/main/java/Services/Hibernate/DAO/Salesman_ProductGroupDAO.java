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
            session.save(group);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List<Salesman_GroupProduct> selectBySalesmanID(Long salesmanID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Salesman_GroupProduct> groupList = null;
        String hql = "";

        try{
            hql = "FROM salesman_group_product session WHERE session.salesman_id = :salesmanID";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            groupList = query.getResultList();
            session.getTransaction().commit();

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            session.close();
            return groupList;
        }
    }
}
