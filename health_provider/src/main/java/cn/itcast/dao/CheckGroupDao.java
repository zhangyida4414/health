package cn.itcast.dao;

import cn.itcast.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);

    public void setCheckgroupAndCheckitem(Map map);

    public Page<CheckGroup> findPageByqueryString(String queryString);

    public CheckGroup findByid(Integer id);

    public List<Integer> findcheckitemIdBygroupId(Integer id);

    public void edit(CheckGroup checkGroup);

    public void deleteAssocication(Integer id);

    public List<CheckGroup> findAll();
}
