package Repositories;

import Services.Hibernate.entity.TradeDiscounts;

import java.sql.Date;
import java.util.List;

public interface TradeDiscountsDao {
    public List<TradeDiscounts> getAllTradeDiscounts();
    public Long addTradeDiscount(String name);
    public Long addTradeDiscount(String name, Date start, Date end);
}
