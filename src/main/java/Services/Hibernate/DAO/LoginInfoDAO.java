package Services.Hibernate.DAO;

import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.entity.WarehousingDetails;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class LoginInfoDAO {
    public LoginInfoDAO(){

    }

    public void saveLoginInfo(LoginInfo loginInfo){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(loginInfo);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateLoginInfo(LoginInfo loginInfo){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(loginInfo);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteLoginInfo(LoginInfo loginInfo){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(loginInfo);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public LoginInfo findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        LoginInfo loginInfo = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM LoginInfo session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            loginInfo = (LoginInfo) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return loginInfo;
        }
    }

    public LoginInfo findByUsername(String username){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        LoginInfo loginInfo = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM LoginInfo session WHERE session.username LIKE :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            loginInfo = (LoginInfo) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return loginInfo;
        }
    }

    public LoginInfo findByEmail(String email){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        LoginInfo loginInfo = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM LoginInfo session WHERE session.email LIKE :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            loginInfo = (LoginInfo) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return loginInfo;
        }
    }
}
