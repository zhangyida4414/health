package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    //1:新增检查项目
    public void add(CheckItem checkItem);
    //2:分页查询
    public PageResult findPage(QueryPageBean queryPageBean);

    public void delete(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
}
