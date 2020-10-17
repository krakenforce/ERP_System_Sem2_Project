package Services.Hibernate.DAO;

import Repositories.IListBehavior;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class PaymentDAO implements IListBehavior {
    public PaymentDAO(){

    }

    public void savePayment(Payment payment){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(payment);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updatePayment(Payment payment){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(payment);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deletePayment(Payment payment){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(payment);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public Payment findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Payment payment = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Payment session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            payment = (Payment) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return payment;
        }
    }

    public Payment findByDate(Date date){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Payment payment = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Payment session WHERE session.date = :date";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            payment = (Payment) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return payment;
        }
    }

    public List<Payment> findPaymentByDate(Date startDate, Date endDate){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Payment> paymentList = null;

        try{
            session.beginTransaction();
            hql = "FROM Payment WHERE date BETWEEN :startDate AND :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            paymentList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return paymentList;
        }
    }

    @Override
    public List<Payment> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        List<Payment> paymentList = null;

        try{
            session.beginTransaction();
            hql = "FROM Payment ";
            Query query = session.createQuery(hql);
            paymentList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return paymentList;
        }
    }
    
    public List<Payment> getPaymentsByTradeDiscount(TradeDiscounts tradeDiscounts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        List<Payment> paymentList = null;

        try{
            session.beginTransaction();
            hql = "FROM Payment WHERE tradeDiscounts = :tradeDiscounts";
            Query query = session.createQuery(hql);
            query.setParameter("tradeDiscounts", tradeDiscounts);
            paymentList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return paymentList;
        }
    }

    public Long countPayments(TradeDiscounts tradeDiscounts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        Long count = 0L;

        try{
            session.beginTransaction();
            hql = "SELECT COUNT (*) FROM Payment WHERE tradeDiscounts = :tradeDiscounts";
            Query query = session.createQuery(hql);
            query.setParameter("tradeDiscounts", tradeDiscounts);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return count;
        }
    }

    public Long sumTotalMoney(TradeDiscounts tradeDiscounts){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        Long sum = 0L;

        try{
            session.beginTransaction();
            hql = "SELECT SUM(money) FROM Payment WHERE tradeDiscounts = :tradeDiscounts";
            Query query = session.createQuery(hql);
            query.setParameter("tradeDiscounts", tradeDiscounts);
            sum = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return sum;
        }
    }

    public Long totalPayment(Long customerID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        Long totalPayment = 0L;

        try{
            session.beginTransaction();
            hql = "SELECT SUM(money) FROM Payment session WHERE customer.id = :customerID GROUP BY customer ";
            Query query = session.createQuery(hql);
            query.setParameter("customerID", customerID);
            totalPayment = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return totalPayment;
        }
    }

    public List<Payment> getPaymentByCustomer(Long customerID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        List<Payment> paymentList = null;

        try{
            session.beginTransaction();
            hql = "FROM Payment session WHERE customer.id = :customerID";
            Query query = session.createQuery(hql);
            query.setParameter("customerID", customerID);
            paymentList = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return paymentList;
        }
    }

    public Payment findByVoucherCode(Long voucherCode){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();;
        String hql = "";
        Payment payment = null;

        try{
            session.beginTransaction();
            hql = "FROM Payment session WHERE voucherCode = :voucherCode";
            Query query = session.createQuery(hql);
            query.setParameter("voucherCode", voucherCode);
            payment = (Payment) query.getSingleResult();
            session.getTransaction().commit();
        }catch(Exception exception){
            exception.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
            return payment;
        }
    }
}
