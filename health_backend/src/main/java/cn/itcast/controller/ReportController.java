package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.service.MemberService;
import cn.itcast.service.ReportService;
import cn.itcast.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = new HashMap<>();//定义一个map容器获取,返回和前台所需一样的格式

            List<String> months = new ArrayList<>(); //ArraryList封装的是包装类

            Calendar calendar = Calendar.getInstance();//获取到日期的对象,模拟的是当前的日期
            calendar.add(Calendar.MONTH, -12);//倒退12个月 倒退一年

            for (int i = 0; i <12 ; i++) {
                calendar.add(Calendar.MONTH,1);//循环遍历每月
                Date date = calendar.getTime(); //获取当月的日期
                months.add(new SimpleDateFormat("yyyy.MM").format(date));//list集合中存储日期信息

            }
            map.put("months",months);//想返回的map中存储日期数据
            List<Integer> memberCount =  memberService.findMemberCountByMonths(months);
            map.put("memberCount",memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS);
        }
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        //1:定义一个map容器
        Map<String,Object> data =  new HashMap<>();
        try {

            //2:定义个list容器 存储查出的套餐总数以及套餐的名称
            List<Map<String,Object>> setmealcount = setmealService.findSetmealCount();
            data.put("setmealCount",setmealcount);
            List<String> setmealNames= new ArrayList<>(); //存储套餐的名称:
            for (Map<String, Object> map : setmealcount) {
                String name = (String) map.get("name");
                setmealNames.add(name);
            }
            data.put("setmealNames",setmealNames);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,data);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
    @Reference
    private ReportService reportService;

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> data = reportService.getBusinessReportData();

            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data);
        }catch (Exception e){
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
}
