package Services.Hibernate.DAO;

import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class SalesManDAO {

    public SalesManDAO() {
    }

    public void saveSalesMan(Salesman salesman) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();

        try {
            s.beginTransaction();

            s.save(salesman);

            s.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // Rollback in case an error occurs
            s.getTransaction().rollback();
        }finally {
            s.close();
        }
    }

    public void updateSalesMan(Salesman salesman) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();

        try {
            s.beginTransaction();

            s.update(salesman);

            s.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // Rollback trong trường hợp có lỗi xẩy ra.
            s.getTransaction().rollback();
        }
        finally {
            s.close();
        }
    }

    public void deleteSalesMan(Salesman salesman) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();

        try {
            s.beginTransaction();

            s.delete(salesman);

            s.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // Rollback trong trường hợp có lỗi xẩy ra.
            s.getTransaction().rollback();
        }
        finally {
            s.close();
        }
    }

    public Salesman findById(Long id){
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();
        Salesman salesman = null;
        String hql = "";
        try {
            s.beginTransaction();

            hql = "select s from Salesman s where s.id = :id";
            Query query = s.createQuery(hql);
            query.setParameter("id",id);
            salesman = (Salesman) query.getSingleResult();
            s.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // Rollback trong trường hợp có lỗi xẩy ra.
            s.getTransaction().rollback();
        }
        finally {
            s.close();
            return salesman;
        }
    }

    public Salesman findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Salesman salesMan = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Salesman session WHERE session.name LIKE :name";
            javax.persistence.Query query = session.createQuery(hql);
            query.setParameter("name", name);
            salesMan = (Salesman) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return salesMan;
        }
    }
}
