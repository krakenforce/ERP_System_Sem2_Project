package Services.Hibernate.DAO;

import Services.Hibernate.entity.WarehousingDetails;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class WarehousingDetailDAO {

    public WarehousingDetailDAO(){

    }

    public void saveWarehousingDetail(WarehousingDetails warehousingDetails){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.save(warehousingDetails);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void updateWarehousingDetail(WarehousingDetails warehousingDetails){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.update(warehousingDetails);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public void deleteWarehousingDetail(WarehousingDetails warehousingDetails){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.delete(warehousingDetails);
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
    public WarehousingDetails findById(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        WarehousingDetails warehousingDetails = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM WarehousingDetails session WHERE session.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            warehousingDetails = (WarehousingDetails) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetails;
        }
    }

    public WarehousingDetails findByProductId(Long productID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        WarehousingDetails warehousingDetails = null;

        try{
            session.beginTransaction();
            hql = "SELECT session FROM WarehousingDetails session WHERE session.product.id = :productID";
            Query query = session.createQuery(hql);
            query.setParameter("productID", productID);
            warehousingDetails = (WarehousingDetails) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetails;
        }
    }

    public List<WarehousingDetails> findByProductIdAndWarehousingId(Long productID, Long warehousingID){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<WarehousingDetails> warehousingDetails = null;

        try{
            session.beginTransaction();
            hql = "SELECT wd FROM WarehousingDetails wd inner join Product p on p.id = wd.product.id" +
                    " inner join BillWarehousing bw on bw.id = wd.billWarehousing.id " +
                    "WHERE p.id = :productID and bw.warehouse.id = :warehousingID";
            Query query = session.createQuery(hql);
            query.setParameter("productID", productID);
            query.setParameter("warehousingID", warehousingID);
            warehousingDetails = (List<WarehousingDetails>) query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetails;
        }
    }

    public List<WarehousingDetails> getListWarehouseDetail(Long idOrder) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<WarehousingDetails> warehousingDetailsList = null;

        try{
            session.beginTransaction();
            hql = "SELECT wd FROM WarehousingDetails wd inner join Delivery_Warehousing dw on wd.id = dw.warehousingDetails.id" +
                    " inner join Order o on o.id = dw.order.id"+
                    " WHERE o.id = :idOrder";
            Query query = session.createQuery(hql);
            query.setParameter("idOrder", idOrder);

            warehousingDetailsList = (List<WarehousingDetails>) query.getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
            return warehousingDetailsList;
        }
    }




}
