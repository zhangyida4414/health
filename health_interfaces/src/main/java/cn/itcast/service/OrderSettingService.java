package cn.itcast.service;

import cn.itcast.pojo.Calendar;
import cn.itcast.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    public void add(List<OrderSetting> data);

    public List<Calendar> getOrderSettingByMonth(String date);

    public void editOrderSettingByDate(OrderSetting orderSetting);
}
