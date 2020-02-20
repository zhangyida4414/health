package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import cn.itcast.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController{

    @Autowired
    private JedisPool jedisPool;

    /*1:图片上传:*/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        int indexOf = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(indexOf - 1);
        String filename = UUID.randomUUID().toString() + extention;
        try {
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(),filename);
            //点击上传时将图片存储到集合中:
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,filename);
    }

    @Reference
    private SetmealService setmealService;

    /*1:新增套餐组合:*/
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
          e.printStackTrace();
          return  new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    /*2:分页查询方法:*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPage(queryPageBean);

        return pageResult;
    }
}
