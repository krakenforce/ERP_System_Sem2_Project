package Services.Hibernate.DAO;

import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.RecieptsProduct;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class ReceiptProductDao {

    public ReceiptProductDao(){

    }

    public static List<RecieptsProduct> getAllOfOrder(Order order) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<RecieptsProduct> list = null;

        try{
            session.beginTransaction();
            hql = "SELECT rp FROM RecieptsProduct rp inner join Order o on rp.order.id = o.id" +
                    " where o.id = :id " ;
            Query query = session.createQuery(hql);
            query.setParameter("id", order.getId());
            list = query.getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return list;
    }

    public void saveRecieptsProduct(RecieptsProduct RecieptsProduct){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();

            session.save(RecieptsProduct);

            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
