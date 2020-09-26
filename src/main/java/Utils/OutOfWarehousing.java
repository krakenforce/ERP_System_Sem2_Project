package Utils;

import Repositories.ProductDao;
import Services.Hibernate.DAO.*;
import Services.Hibernate.entity.*;

import java.util.ArrayList;
import java.util.List;

//public class OutOfWarehousing {
//    private ProductDao productDAO;
//    private DeliveryDetail_WarehousingDao deliveryDetailWarehousingDao;
//    //private DeliveryDetailDao deliveryDetailDao;
//    private WarehousingDetailDAO warehousingDetailDAO;
//    private OrderDAO orderDAO;
//    public OutOfWarehousing() {
//        orderDAO = new OrderDAO();
//        productDAO = new ProductDAO();
//        //deliveryDetailDao = new DeliveryDetailDao();
//        deliveryDetailWarehousingDao = new DeliveryDetail_WarehousingDao();
//        warehousingDetailDAO = new WarehousingDetailDAO();
//    }
//
//    public void createDeliveryDetail(Order order){
//
//        if(order.isEnough())
//            return;
//        Long amoutDelivery = order.getAmount();
//        List<WarehousingDetails> warehousingDetailsList = new ArrayList<WarehousingDetails>();
//        warehousingDetailsList =  productDAO.getWarehouse(order.getProduct());
////        DetailOutOfStockOrder deliveryDetails = new DetailOutOfStockOrder();
////        deliveryDetails.setOrder(order);
////        deliveryDetailDao.saveDeliveryDetail(deliveryDetails);
//
//
//        for(WarehousingDetails e : warehousingDetailsList){
//            Long amout = e.getAmount();
//            //Đã xuất đủ
//            if(amout >= amoutDelivery){
//                e.setAmount(amout - amoutDelivery);
//                warehousingDetailDAO.updateWarehousingDetail(e);
//                createDelivery_Warehousing(e,order,amoutDelivery);
//                order.setEnough(true);
//                orderDAO.updateOrder(order);
//                break;
//            }
//            //Phiếu xuất kho chưa đủ và lớn hơn 0
//            if(amout > 0) {
//                amoutDelivery = amoutDelivery - amout;
//                e.setAmount((long) 0);
//                warehousingDetailDAO.updateWarehousingDetail(e);
//                createDelivery_Warehousing(e,order, amout);
//            }
//        };
//
//    }
//
//    private void createDelivery_Warehousing(WarehousingDetails e,Order order, Long amout) {
//
//        Delivery_Warehousing delivery_warehousing = new Delivery_Warehousing();
//        delivery_warehousing.setAmount(amout);
//        delivery_warehousing.setOrder(order);
//        delivery_warehousing.setWarehousingDetails(e);
//        deliveryDetailWarehousingDao.saveDeliveryDetail(delivery_warehousing);
//
//    }
//
//    public Long amountNeeded(Order order){
//
//        if(order.isEnough())
//            return (long) 0;
//        Long amoutDelivery = order.getAmount();
//        List<WarehousingDetails> warehousingDetailsList = new ArrayList<WarehousingDetails>();
//        warehousingDetailsList =  productDAO.getWarehouse(order.getProduct());
//
//        for(WarehousingDetails e : warehousingDetailsList){
//            Long amout = e.getAmount();
//            //Đã xuất đủ
//            if(amout >= amoutDelivery){
//                amoutDelivery = (long) 0;
//                break ;
//            }
//            //Phiếu xuất kho chưa đủ và lớn hơn 0
//            if(amout > 0) {
//                amoutDelivery = amoutDelivery - amout;
//
//            }
//        };
//
//        return amoutDelivery;
//
//    }
//
//}
