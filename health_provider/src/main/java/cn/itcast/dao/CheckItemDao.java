package cn.itcast.dao;

import cn.itcast.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface CheckItemDao {
    public void add(CheckItem checkItem);

    public Page<CheckItem> findPage(String queryString);

    public Long findCountByCheckItemId(Integer id);

    public void delete(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
}
