package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Calendar;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import cn.itcast.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    //1:文件上传,实现预约设置数据批量导入
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile exceFile){
        try {
            //调用POI工具类读取文件
            List<String[]> list = POIUtils.readExcel(exceFile);
            List<OrderSetting> data = new ArrayList<>();

            for (String[] strings : list) {
                String orderDate = strings[0];
                String number = strings[1];
                System.out.println(new Date(orderDate));
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.parseInt(number));
                data.add(orderSetting);
            }
            orderSettingService.add(data);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }


    //2:根据年月份查询对应的预约设置数据:
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        List<Calendar> list = null;
        try {
             list =  orderSettingService.getOrderSettingByMonth(date);

        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
    }

    /*3:设置预约管理*/
    @RequestMapping("/editOrderSettingByDate")
    public Result editOrderSettingByDate(@RequestBody OrderSetting orderSetting){
        System.out.println(orderSetting.getOrderDate());
        try {
             orderSettingService.editOrderSettingByDate(orderSetting);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.ORDERSETTING_SUCCESS);
        }
        return new Result(true,MessageConstant.ORDERSETTING_FAIL);
    }
}
