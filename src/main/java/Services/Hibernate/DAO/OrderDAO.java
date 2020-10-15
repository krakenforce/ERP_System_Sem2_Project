package Services.Hibernate.DAO;

import Repositories.IListBehavior;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Discount;
import Services.Hibernate.entity.Order;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class OrderDAO implements IListBehavior {
    public OrderDAO(){

    }

    public void saveOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public Order findByID(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Order order = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            order = (Order) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return order;
        }
    }

    public List<Order> findByDetailOrderID(Long detailOrderID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.detailOrder.id = :detailOrderID";
            Query query = session.createQuery(hql);
            query.setParameter("detailOrderID", detailOrderID );
            orderList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return orderList;
        }
    }

    public List<Order> findBySalesmanID(Long salesmanID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.salesman.id = :salesmanID";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID );
            orderList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return orderList;
        }
    }

    public List<Order> findByDate(Date startDate, Date endDate){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.detailOrder.date BETWEEN :startDate AND :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            orderList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return orderList;
        }
    }

    public List<Order> findByDate(Long salesmanID, Date startDate, Date endDate){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM Order session WHERE session.salesman.id =:salesmanID AND session.detailOrder.date BETWEEN :startDate AND :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            orderList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return orderList;
        }
    }

    @Override
    public List<Order> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;

        try{
            session.beginTransaction();
            hql = "FROM Order";
            Query query = session.createQuery(hql);
            orderList = query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return orderList;
        }
    }

    public Long countOrder(Long salesmanID) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;
        Long count = null;

        try{
            session.beginTransaction();
            hql = "SELECT COUNT (*) FROM Order WHERE salesman.id = :salesmanID GROUP BY salesman.id";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return count;
        }
    }

    public Long countDetailOrder(Long salesmanID) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;
        Long count = null;

        try{
            session.beginTransaction();
            hql = "SELECT COUNT (*) FROM Order WHERE salesman.id = :salesmanID group by detailOrder";
            Query query = session.createQuery(hql);
            query.setParameter("salesmanID", salesmanID);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return count;
        }
    }

    public Long countAmount(Long groupProductID, Date startDate, Date endDate) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Order> orderList = null;
        Long count = null;

        try{
            session.beginTransaction();
            hql = "SELECT SUM(amount) FROM Order WHERE detailOrder.date BETWEEN :startDate AND :endDate " +
                    "AND product.groupProduct.id =: groupProductID group by product.groupProduct.id";
            Query query = session.createQuery(hql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("groupProductID", groupProductID);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return count;
        }
    }


    public Long countAllProductSold(Long productID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Long count = null;

        try{
            session.beginTransaction();
            hql = "SELECT SUM(amount) FROM Order WHERE product.id = :productID GROUP BY product.id";
            Query query = session.createQuery(hql);
            query.setParameter("productID", productID);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return count;
        }
    }

    public Long countProductSoldByOrderList(List<Order> orderList, Long productID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        Long count = null;

        try{
            session.beginTransaction();
            hql = "SELECT SUM(amount) FROM Order session WHERE product.id = :productID AND session IN :orderList GROUP BY product.id";
            Query query = session.createQuery(hql);
            query.setParameter("productID", productID);
            query.setParameter("orderList", orderList);
            count = (Long) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return count;
        }
    }


}
