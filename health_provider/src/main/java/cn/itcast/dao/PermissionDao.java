package cn.itcast.dao;

import cn.itcast.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
   public Set<Permission> findByRoleId(Integer roleId);
}
