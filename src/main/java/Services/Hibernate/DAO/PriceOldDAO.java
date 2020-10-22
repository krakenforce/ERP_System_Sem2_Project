package Services.Hibernate.DAO;

import Services.Hibernate.entity.PriceOld;
import Services.Hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class PriceOldDAO {

    public PriceOldDAO(){

    }

    public void savePriceOld(PriceOld priceOld){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();

            session.save(priceOld);

            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List<PriceOld> getAllPriceOldProduct(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "";
        List<PriceOld> list = null;

        try{
            session.beginTransaction();
            hql = "SELECT po FROM Product p inner join PriceOld po on p.id = po.product.id" +
                    " where p.id = :id order by po.date desc " ;
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
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
}
