package cn.itcast.service.impl;

import cn.itcast.dao.PermissionDao;
import cn.itcast.dao.RoleDao;
import cn.itcast.dao.UserDao;
import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    //1:根据用户名查询用户的信息
    public User findByUsername(String username) {
        User user =  userDao.findByUsername(username);
        if (user == null){
            return null;
        }
        Integer userId = user.getId();// 获取到user;id
        Set<Role> roles= roleDao.findByUserId(userId);//查询到所有角色信息
        if (roles != null){
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);

                //然后角色关联权限
                role.setPermissions(permissions);
            }
        }
        user.setRoles(roles); //让用户关联角色
        return user;
    }
}
