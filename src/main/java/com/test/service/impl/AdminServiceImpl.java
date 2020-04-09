package com.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.domain.Admin;
import com.test.mappper.AdminMapper;
import com.test.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: AdminServiceImpl.java
 * @description:
 * @date:2020/4/8
 **/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}