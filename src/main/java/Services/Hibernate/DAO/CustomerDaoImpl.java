package Services.Hibernate.DAO;

import Repositories.CustomerDao;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.entity.TradeDiscounts;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    public CustomerDaoImpl(){

    }

    public void saveCustomer(Customer customer){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateCustomer(Customer customer){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(customer);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteCustomer(Customer customer){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(customer);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public Customer findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();
        Customer customer = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Customer session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            customer = (Customer) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return customer;
        }
    }

    public List<Customer> findByName(String name){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Customer> customers = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Customer session WHERE session.name LIKE :name";
            Query query = session.createQuery(hql);
            query.setParameter("name","%" + name + "%");
            customers = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return customers;
        }
    }

    @Override
    public Long addCustomer(String name, String phone, Long salesman) {
        Salesman s = null;
        try {
            SalesManDaoImpl si = new SalesManDaoImpl();
            s = si.findById(salesman);

        } catch (NoResultException e) {
            return 0L;
        }
        Customer c = new Customer(name, phone);
        c.setSalesman(s);
        saveCustomer(c);
        return c.getId();
    }

    @Override
    public List<Customer> getAllCustomers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Customer> customers = null;
        String hql = "";

        try {
            session.beginTransaction();
            hql = "from Customer ";
            Query query = session.createQuery(hql);
            customers = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return customers;

    }

    public List<TradeDiscounts> getAllDiscounts(Long id) {
        // get all valid discounts
        TradeDiscountDaoImpl ti = new TradeDiscountDaoImpl();
        List<TradeDiscounts> ts = ti.getAllTradeDiscounts();
        List<TradeDiscounts> rs = new ArrayList<>();
        for (TradeDiscounts t : ts) {
            Date d = t.getDateEnd();
            Date today = Date.valueOf(LocalDate.now());
            if (d.compareTo(today) < 0) {
                continue;
            }
            if (t.getCustomer() == findByID(id)) {
                rs.add(t);
            }

        }

        return rs;
    }
}
