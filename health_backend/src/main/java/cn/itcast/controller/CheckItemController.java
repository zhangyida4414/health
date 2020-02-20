package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    /*1:新增检查项目*/
    @RequestMapping("/add")
    public Result checkItem(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

   /*2:分页查询检查项目*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult = checkItemService.findPage(queryPageBean);

       return pageResult;
    }
    /*3:删除检查项*/
    //删除检查项
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /*4:编辑检查项目*/
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem ){
        try {
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /*5:根据ID回显检查项的信息*/
    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem = null;
        try {
             checkItem = checkItemService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }
    /*5:根据ID回显检查项的信息*/
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        if (list != null && list.size() != 0){
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }
       return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
