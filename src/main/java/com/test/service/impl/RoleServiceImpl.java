package com.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.domain.Role;
import com.test.mapper.RoleMapper;
import com.test.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: RoleServiceImpl.java
 * @description:
 * @date:2020/4/8
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
