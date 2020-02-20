package cn.itcast.service;

import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    // 根据用户名查询
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);//通过用户名查询用户信息
        if (user == null){
            return null;
            }
            Set<Role> roles = user.getRoles();//查询用户的所有角色信息
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();//将信息交于框架
            if (roles!= null){
                for (Role role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role.getKeyword()));
                    Set<Permission> permissions = role.getPermissions(); //获取所有的权限
                    if (permissions != null){
                        for (Permission permission : permissions) {
                            authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
                        }
                    }
                }
            }

        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);
    }
}
