package cn.itcast.dao;

import cn.itcast.pojo.User;

public interface UserDao {
   public User findByUsername(String username);
}
