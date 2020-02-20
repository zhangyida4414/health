package cn.itcast.dao;

import cn.itcast.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public Long findCountByOrderDate(Date orderDate);

    public void updateNumberByOrderDate(OrderSetting orderSetting);

    public void add(OrderSetting orderSetting);

    public List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    public OrderSetting findByOrderDate(Date parseString2Date);

    public void editReservationsByOrderDate(OrderSetting orderSetting);
}
