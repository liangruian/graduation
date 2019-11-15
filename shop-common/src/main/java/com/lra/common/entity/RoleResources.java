package com.lra.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="RoleResources对象", description="角色权限表")
public class RoleResources extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String resourcesId;

}
