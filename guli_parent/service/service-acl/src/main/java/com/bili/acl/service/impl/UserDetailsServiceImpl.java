package com.bili.acl.service.impl;

import com.bili.acl.service.PermissionService;
import com.bili.acl.service.UserService;
import com.bili.serurity.entity.SecurityUser;
import com.bili.acl.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 *
 * UserDetailsService有一个loadUserByUsername方法，根据用户名在数据源查找到用户数据（UserDetails）
 *
 *
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUsername(username);

        // 判断用户是否存在
        if (null == user){
            //throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        com.bili.serurity.entity.User curUser = new com.bili.serurity.entity.User();
        BeanUtils.copyProperties(user,curUser);

        //获得权限列表
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        //将用户列表和用户信息封装在一起
        securityUser.setPermissionValueList(authorities);
        //发送出去
        return securityUser;
    }

}
