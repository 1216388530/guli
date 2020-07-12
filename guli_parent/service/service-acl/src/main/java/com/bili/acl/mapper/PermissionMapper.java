package com.bili.acl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.acl.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {


    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}
