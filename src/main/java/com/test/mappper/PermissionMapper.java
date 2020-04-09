package com.test.mappper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: create by wangmh
 * @name: PermissionMapper.java
 * @description:
 * @date:2020/4/8
 **/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
