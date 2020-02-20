package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisMessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Order;
import cn.itcast.service.OrderService;
import cn.itcast.utils.SMSUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/*
* 体检预约处理
* */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        //从redis中获取保存的验证码
        String valiadateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        //将用户输入的验证码和redis中保存的验证码进行比对
        if (valiadateCodeInRedis != null && validateCode != null && valiadateCodeInRedis.equals(validateCode)){
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result  result = null;
            try{
                result = orderService.order(map);
            }catch (Exception e){
                return result;
            }
            if (result.isFlag()){
                try {
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, (String) map.get("orderDate"));
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }else {
            //比对失败 直接返回错误信息
            return  new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    //根据预约的id 查询预约相关的信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }


    }
}
