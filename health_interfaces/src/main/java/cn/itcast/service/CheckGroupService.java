package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CheckGroupService {

    public void add( CheckGroup checkGroup ,Integer[] CheckitemIds);

    public PageResult findPage(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public List<Integer> findcheckitemIdBygroupId(Integer id);

    public void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    public List<CheckGroup> findAll();
}
