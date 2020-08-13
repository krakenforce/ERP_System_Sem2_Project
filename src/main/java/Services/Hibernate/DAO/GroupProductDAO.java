package Services.Hibernate.DAO;

import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class GroupProductDAO {
    public GroupProductDAO(){

    }

    public void saveGroupProduct(GroupProduct groupProduct){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(groupProduct);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateGroupProduct(GroupProduct groupProduct){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(groupProduct);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteGroupProduct(GroupProduct groupProduct){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(groupProduct);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public GroupProduct findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        GroupProduct groupProduct = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM GroupProduct session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            groupProduct = (GroupProduct) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return groupProduct;
        }
    }

    public GroupProduct findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        GroupProduct groupProduct = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM GroupProduct session WHERE session.name LIKE :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            groupProduct = (GroupProduct) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return groupProduct;
        }
    }
}
