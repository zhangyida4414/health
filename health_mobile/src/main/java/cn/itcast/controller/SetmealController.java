package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    //1:查询所有的套餐
    @RequestMapping("/getSetmeal")
    public Result getAllSetmeal(){
        List<Setmeal> list = null;
        try {
          list = setmealService.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
       return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    }

    //根据套餐 id 查询套餐详情
    @RequestMapping("/findById")
    public Result findById(int id){

        Setmeal setmeal = null;
        try {
            setmeal = setmealService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
