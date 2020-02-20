package cn.itcast.service;

import cn.itcast.entity.Result;

import java.util.Map;

public interface OrderService  {
    public Result order(Map map)throws Exception;

    public Map findById(Integer id)throws Exception;
}
