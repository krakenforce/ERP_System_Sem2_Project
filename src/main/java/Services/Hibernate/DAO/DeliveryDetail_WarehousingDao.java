package Services.Hibernate.DAO;

import Services.Hibernate.entity.Delivery_Warehousing;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class DeliveryDetail_WarehousingDao {
    public DeliveryDetail_WarehousingDao() {
    }

    public void saveDeliveryDetail(Delivery_Warehousing delivery_warehousing){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session  = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(delivery_warehousing);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List<Delivery_Warehousing> getListDeliveryWarehouse(Long idOrder) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<Delivery_Warehousing> delivery_warehousingList = null;

        try{
            session.beginTransaction();
            hql = "SELECT dw FROM WarehousingDetails wd inner join Delivery_Warehousing dw on wd.id = dw.warehousingDetails.id" +
                    " inner join Order o on o.id = dw.order.id"+
                    " WHERE o.id = :idOrder";
            Query query = session.createQuery(hql);
            query.setParameter("idOrder", idOrder);

            delivery_warehousingList = (List<Delivery_Warehousing>) query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return delivery_warehousingList;
        }
    }

}
