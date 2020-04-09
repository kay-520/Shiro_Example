package com.test.controller;

import com.alibaba.druid.util.StringUtils;
import com.test.domain.Admin;
import com.test.service.AdminService;
import com.test.util.IpUtil;
import com.test.util.RespMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by wangmh
 * @name: AdminAuthController.java
 * @description:
 * @date:2020/4/8
 **/
@RestController
@RequestMapping("/wmh/auth")
@Slf4j
public class AdminAuthController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public RespMsg<?> login(@RequestParam String name, @RequestParam String password, HttpServletRequest request) {
        if (StringUtils.isEmpty("name") || StringUtils.isEmpty(password)) {
            RespMsg.error(401, "参数值不对");
        }
        Subject currentUser = SecurityUtils.getSubject();
        ;
        try {
            currentUser.login(new UsernamePasswordToken(name, password));//->走自定义AdminAuthorizingRealm的doGetAuthenticationInfo身份验证方法
        } catch (UnknownAccountException uae) {
            log.error("登录", "用户帐号或密码不正确");
            return RespMsg.error("用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            log.error("登录", "用户帐号已锁定不可用");
            return RespMsg.error("用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
            log.error("登录", "认证失败");
            return RespMsg.error("认证失败");
        }
        //获取用户信息
        currentUser = SecurityUtils.getSubject();
        Admin admin = (Admin) currentUser.getPrincipal();
        //设置登录ip
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(LocalDateTime.now());
        adminService.updateById(admin);

        //封装返回结果
        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put("nickName", admin.getUsername());
        adminInfo.put("avatar", admin.getAvatar());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", currentUser.getSession().getId());
        result.put("adminInfo", adminInfo);
        return RespMsg.ok(result);
    }

    @RequiresAuthentication
    @PostMapping("/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return RespMsg.ok("logout");
    }

    @GetMapping("/401")
    public Object page401() {
        return RespMsg.error(401, "unlogin");
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return "index successs!";
    }

    @GetMapping("/403")
    public Object page403() {
        return RespMsg.error(403, "unauthz");
    }

}
