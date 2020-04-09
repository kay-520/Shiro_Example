package com.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.domain.Permission;
import com.test.mappper.PermissionMapper;
import com.test.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: PermissionServiceImpl.java
 * @description:
 * @date:2020/4/8
 **/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
