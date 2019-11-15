package com.lra.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Resources对象", description="权限表")
public class Resources extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String resUrl;

    private Integer userType;

    private String parentId;

    private Integer userSort;

}
