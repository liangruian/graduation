package com.lra.common.service.impl;

import com.lra.common.entity.Resources;
import com.lra.common.mapper.ResourcesMapper;
import com.lra.common.service.IResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

}
