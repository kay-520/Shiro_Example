package com.test.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.domain.Admin;
import com.test.domain.Permission;
import com.test.domain.Role;
import com.test.service.AdminService;
import com.test.service.PermissionService;
import com.test.service.RoleService;
import com.test.util.bcrypt.BCryptPasswordEncoder;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: create by wangmh
 * @name: AdminAuthorizingRealm.java
 * @description: 自定义Realm，用于查询用户的角色和权限信息并保存到权限管理器：身份验证
 * @date:2020/4/8
 **/
public class AdminAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AdminService adminService;

    /***
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        //获取用户信息
        Admin admin = (Admin) getAvailablePrincipal(principalCollection);
        Integer[] roleIds = admin.getRoleIds();
        //获取角色信息
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.lambda().in(Role::getId, roleIds);
        List<Role> roleList = roleService.list(roleQueryWrapper);
        Set<String> roles = roleList.stream().map(Role::getName).collect(Collectors.toSet());

        //获取权限信息
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.lambda().in(Permission::getId, roleIds);
        List<Permission> permissionList = permissionService.list(permissionQueryWrapper);
        Set<String> permissions = permissionList.stream().map(Permission::getPermission).collect(Collectors.toSet());

        //保存到权限管理器
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /***
     * 身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取用户信息
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new AccountException("密码不能为空");
        }
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.lambda().eq(Admin::getUsername, username);
        List<Admin> adminList = adminService.list(adminQueryWrapper);
        Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
        if (adminList.size() == 0) {
            throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
        }
        Admin admin = adminList.get(0);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, admin.getPassword())) {
            throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
        }
        return new SimpleAuthenticationInfo(admin, password, getName());
    }
}
