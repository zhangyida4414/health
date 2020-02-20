package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.MemberDao;
import cn.itcast.dao.OrderDao;
import cn.itcast.dao.OrderSettingDao;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import cn.itcast.pojo.Order;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderService;
import cn.itcast.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImlp implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;

    public Result order(Map map)throws Exception {
        //1:检查用户选择的预约日期有没有进行预约设置,没有则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if (orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2:检查用户选择的预约日期是否已经预约满
        int number = orderSetting.getNumber(); //当前日期可以预约的人数
        int reservations = orderSetting.getReservations();//当前日期已经预约的人数
        if (number <= reservations){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3:检查用户重复预约,同一个用户同一天预约同一个套餐
        String  telephone = (String) map.get("telephone");
        Member memeber = memberDao.findBytelephone(telephone);//根据手机号查询会员信息
        //4:检查当前用户是否是会员,如果不是自动注册会员
        if (memeber != null){
            Integer memeberId = memeber.getId();//会员id
            Date date = DateUtils.parseString2Date(orderDate);
            String setmealId = (String) map.get("setmealId");
            Order order = new Order(memeberId,date,Integer.parseInt(setmealId));
            List<Order> orders = orderDao.findByCondition(order);
            if (orders !=null && orders.size() > 0 ){
               return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else { //不是会员直接注册:
            memeber = new Member();
            memeber.setName((String) map.get("name"));
            memeber.setPhoneNumber(telephone);
            memeber.setIdCard((String) map.get("idCard"));
            memeber.setSex((String) map.get("sex"));
            memeber.setRegTime(new Date());
            memberDao.add(memeber);
        }
        //5:预约成功,更新当日的预约日数
        Order order = new Order();
        order.setId(memeber.getId());  //设置会员id
        order.setOrderDate(DateUtils.parseString2Date(orderDate));//设置预约的日期
        order.setOrderType((String) map.get("orderType"));  //预约的类型
        order.setOrderStatus(Order.ORDERSTATUS_NO); //就诊的状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));//套餐的id
        orderDao.add(order); // 将预约信息添加到表中

        orderSetting.setReservations(orderSetting.getReservations()+1);// 将预约人数 +1
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,order.getId());
    }

    // 2: 根据id查询预约信息(体检人姓名,预约信息,套餐名称,预约的类型)
    public Map findById(Integer id)throws Exception{
        Map map = orderDao.findById4Detail(id);
        if (map != null){
            Date orderDate = (Date) map.get("orderDate");
          map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

}
