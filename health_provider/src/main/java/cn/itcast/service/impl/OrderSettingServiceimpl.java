package cn.itcast.service.impl;

import cn.itcast.dao.OrderSettingDao;
import cn.itcast.pojo.Calendar;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceimpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //1:添加预约设置信息
    public void add(List<OrderSetting> data) {
        if (data != null && data.size() > 0){

            //遍历集合
            for (OrderSetting orderSetting : data) {
                //获取日期;查询是否有数据;有直接修改,没有直接添加
                System.out.println(orderSetting.getOrderDate());
               Long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
               if (count > 0){
                   orderSettingDao.updateNumberByOrderDate(orderSetting);
               }else {
                   orderSettingDao.add(orderSetting);
               }
            }
        }
    }

    //2:根据月份查询对应的预约设置的数据
    public List<Calendar> getOrderSettingByMonth(String date) {
        String datebegin = date + "-1";
        String dateend   = date + "-31";
        Map<String,String> map = new HashMap<>();
        map.put("datebegin",datebegin);
        map.put("dateEnd",dateend);

        List<OrderSetting> list =  orderSettingDao.getOrderSettingByMonth(map);
        System.out.println(list);

        List<Calendar> calendarList = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Calendar calendar = new Calendar();

            calendar.setDate(orderSetting.getOrderDate().getDate());
            calendar.setNumber(orderSetting.getNumber());
            calendar.setReservations(orderSetting.getReservations());
            calendarList.add(calendar);
        }

        return calendarList;
    }

    //3：预约设置
    public void editOrderSettingByDate(OrderSetting orderSetting) {
        //根据日期来查询是否已经进行了预约设置:
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询;如果有数据则修改,否则直接添加
        Long count =  orderSettingDao.findCountByOrderDate(orderDate);
        if (count>0){
            orderSettingDao.updateNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
