package com.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: create by wangmh
 * @name: AdminMapper.java
 * @description:
 * @date:2020/4/8
 **/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
