package cn.itcast.dao;

import cn.itcast.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
   public void add(Setmeal setmeal);

   public void setSetmealIdAndCheckgroupId(Map map);

   public Page<Setmeal> findByCondition(String queryString);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public List<Map<String, Object>> findSetmealCount();
}
