package Services.Hibernate.DAO;

import Services.Hibernate.entity.Unit;
import Services.Hibernate.entity.UserType;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserTypeDAO {

    public UserTypeDAO() {

    }

    public void saveUserType(UserType userType){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(userType);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateUserType(UserType userType){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(userType);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deteleUserType(UserType userType){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(userType);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public List<UserType> getAll(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<UserType> userTypesList = null;

        try{
            session.beginTransaction();
            hql = "FROM UserType ";
            Query query = session.createQuery(hql);
            userTypesList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            session.close();
            return userTypesList;
        }
    }

    public UserType findByName(String typeName){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        UserType userTypes = null;

        try{
            session.beginTransaction();
            hql = "FROM UserType WHERE typeName = :typeName ";
            Query query = session.createQuery(hql);
            query.setParameter("typeName", typeName);
            userTypes = (UserType) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            session.close();
            return userTypes ;
        }
    }

    public UserType findByID(int id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        UserType userTypes = null;

        try{
            session.beginTransaction();
            hql = "FROM UserType WHERE id = :id ";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            userTypes = (UserType) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            session.close();
            return userTypes ;
        }
    }


}
