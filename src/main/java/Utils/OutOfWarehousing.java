package Utils;

import Services.Hibernate.DAO.DeliveryDetail_WarehousingDao;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.WarehousingDetailDAO;
import Services.Hibernate.entity.Delivery_Warehousing;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.WarehousingDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OutOfWarehousing {

    private ProductDAO productDAO;
    private DeliveryDetail_WarehousingDao deliveryDetailWarehousingDao;
    //private DeliveryDetailDao deliveryDetailDao;
    private WarehousingDetailDAO warehousingDetailDAO;
    private OrderDAO orderDAO;
    public OutOfWarehousing() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        //deliveryDetailDao = new DeliveryDetailDao();
        deliveryDetailWarehousingDao = new DeliveryDetail_WarehousingDao();
        warehousingDetailDAO = new WarehousingDetailDAO();
    }

    private Long tinhTongLuongXuatKhoProductOrder(Order order){
        Long tongDaXuat = (long) 0;
        Set<Delivery_Warehousing> delivery_warehousingList = order.getDelivery_WarehousingSet();
        for(Delivery_Warehousing item : delivery_warehousingList){
            tongDaXuat = tongDaXuat + item.getAmount();
        }
        return order.getAmount() - tongDaXuat;
    }
    public Boolean createDeliveryDetail(Order order, List<WarehousingDetails> warehousingDetailsList){

        if(order.isEnough())
            return true;
        //Lỗi Xuất kho dòng này
        Long amoutDelivery = this.tinhTongLuongXuatKhoProductOrder(order);


        for(WarehousingDetails e : warehousingDetailsList){
            Long amout = e.getAmount();
            //Đã xuất đủ
            if(amout >= amoutDelivery){
                e.setAmount(amout - amoutDelivery);
                warehousingDetailDAO.updateWarehousingDetail(e);
                createDelivery_Warehousing(e,order,amoutDelivery);
                order.setEnough(true);
                orderDAO.updateOrder(order);
                return true;
            }
            //Phiếu xuất kho chưa đủ và lớn hơn 0
            if(amout > 0) {
                amoutDelivery = amoutDelivery - amout;
                e.setAmount((long) 0);
                warehousingDetailDAO.updateWarehousingDetail(e);
                createDelivery_Warehousing(e,order, amout);
            }
        };

        return false;
    }

    private void createDelivery_Warehousing(WarehousingDetails e,Order order, Long amout) {

        Date date = Date.valueOf(LocalDate.now());
        Delivery_Warehousing delivery_warehousing = new Delivery_Warehousing();
        delivery_warehousing.setAmount(amout);
        delivery_warehousing.setOrder(order);
        delivery_warehousing.setWarehousingDetails(e);
        delivery_warehousing.setDate(date);
        deliveryDetailWarehousingDao.saveDeliveryDetail(delivery_warehousing);

    }

    public Long amountNeeded(Order order){

        if(order.isEnough())
            return (long) 0;
        Long amoutDelivery = order.getAmount();
        List<WarehousingDetails> warehousingDetailsList = new ArrayList<WarehousingDetails>();
        warehousingDetailsList =  productDAO.getWarehouse(order.getProduct());

        for(WarehousingDetails e : warehousingDetailsList){
            Long amout = e.getAmount();
            //Đã xuất đủ
            if(amout >= amoutDelivery){
                amoutDelivery = (long) 0;
                break ;
            }
            //Phiếu xuất kho chưa đủ và lớn hơn 0
            if(amout > 0) {
                amoutDelivery = amoutDelivery - amout;

            }
        };

        return amoutDelivery;

    }

}
