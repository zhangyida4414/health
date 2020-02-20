package cn.itcast.dao;

import cn.itcast.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public List<Order> findByCondition(Order order);

    public void add(Order order);

    public Map findById4Detail(Integer id);

    public Integer findOrderCountByDate(String today);

    public Integer findOrderCountAfterDate(String thisWeekMonday);

    public Integer findVisitsCountByDate(String today);

    public Integer findVisitsCountAfterDate(String thisWeekMonday);

    public List<Map> findHotSetmeal();
}
