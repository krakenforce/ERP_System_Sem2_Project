package Services.Hibernate.DAO;

import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.entity.TradeDiscounts;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public CustomerDAO(){

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
        List<Customer> customerList = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Customer session WHERE session.name LIKE :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            customerList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return customerList;
        }
    }

    public List<Customer> findBySalesmanID(Long salesmanID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Customer> customerList = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "FROM Customer session WHERE session.salesman.id = :salesmanID ";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            customerList = query.getResultList();
            session.getTransaction().rollback();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return customerList;
        }
    }

    public List<Customer> findBySalesmanIDAndCustomer(Long salesmanID, String customerName){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Customer> customerList = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "FROM Customer session WHERE session.name =: customerName AND session.salesman.id = :salesmanID ";
            Query query = session.createQuery(hql);
            query.setParameter("customerName", customerName);
            query.setParameter("salesmanID", salesmanID);
            customerList = query.getResultList();
            session.getTransaction().rollback();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return customerList;
        }
    }
    public List<Customer> findBySalesmanIDAndCustomerPhone(Long salesmanID, String phone){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Customer> customerList = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "FROM Customer session WHERE session.phone = :phone AND session.salesman.id = :salesmanID ";
            Query query = session.createQuery(hql);
            query.setParameter("phone", phone);
            query.setParameter("salesmanID", salesmanID);
            customerList = query.getResultList();
            session.getTransaction().rollback();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return customerList;
        }
    }


    public List<Customer> selectAllCustomer(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Customer> customerList = null;
        String hql = "";

        try{
            session.beginTransaction();
            hql = "FROM Customer";
            Query query = session.createQuery(hql);
            customerList = query.getResultList();
            session.getTransaction().rollback();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return customerList;
        }
    }

    public Long addCustomer(String name, String phone, Long salesman, String address) {
        Salesman s = null;
        try {
            SalesManDAO si = new SalesManDAO();
            s = si.findById(salesman);

        } catch (NoResultException e) {
            return 0L;
        }
        Customer c = new Customer(name, phone, address);
        c.setSalesman(s);
        saveCustomer(c);
        return c.getId();
    }

    public List<TradeDiscounts> getAllDiscounts(Long id) {
        // get all valid discounts
        TradeDiscountDAO ti = new TradeDiscountDAO();
        List<TradeDiscounts> ts = ti.getAll();
        List<TradeDiscounts> rs = new ArrayList<>();
        for (TradeDiscounts t : ts) {
            Date d = t.getDateEnd();
            Date today = Date.valueOf(LocalDate.now());
            if (d.compareTo(today) < 0) {
                continue;
            }
//            if (t.getCustomer() == findByID(id)) {
//                rs.add(t);
//            }

        }

        return rs;
    }
}
