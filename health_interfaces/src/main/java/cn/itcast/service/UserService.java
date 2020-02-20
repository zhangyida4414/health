package cn.itcast.service;

import cn.itcast.pojo.User;

public interface UserService {
   public User findByUsername(String username);
}
