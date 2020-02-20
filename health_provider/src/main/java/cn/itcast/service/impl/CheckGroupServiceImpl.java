package cn.itcast.service.impl;

import cn.itcast.dao.CheckGroupDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    //1:新增检查组,同时需要让检查组关联检查项目
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1:新增检查组,操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //2:设置检查组和检查项的多对多的关联关系,操作t_checkegroup_checkItem
        Integer checkGroupId = checkGroup.getId();
        this.setCheckgroupAndCheckitem(checkGroupId,checkitemIds);
    }

    /*2:分页查询:*/
    public PageResult findPage(QueryPageBean queryPageBean) {
        /*获取pageBean中的三个参数*/
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        /*设置分页:分页助手会在SQL后 进行limt拼接*/
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findPageByqueryString(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /*3:通过 id 回显检查组*/
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findByid(id);
    }

    /*4:根据检查组的ID 获取到检查项的集合*/
    public List<Integer> findcheckitemIdBygroupId(Integer id) {
        return checkGroupDao.findcheckitemIdBygroupId(id);
    }

    /*6:编辑检查组:*/
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1:修改checkgroup表中数据:
        checkGroupDao.edit(checkGroup);
        //2:删除所有选择的检查项的id
        checkGroupDao.deleteAssocication(checkGroup.getId());
        //3:重写关联检查组合检查项
        this.setCheckgroupAndCheckitem(checkGroup.getId(),checkitemIds);
    }

    /*7:查询所有的检查组*/
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /*重写建立检查项和检查组的关系:*/
    public void setCheckgroupAndCheckitem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckgroupAndCheckitem(map);
            }
        }
    }
}
