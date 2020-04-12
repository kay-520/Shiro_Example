package com.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: create by wangmh
 * @name: RoleMapper.java
 * @description:
 * @date:2020/4/8
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
