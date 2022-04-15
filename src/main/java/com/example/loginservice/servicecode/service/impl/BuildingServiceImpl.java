package com.example.loginservice.servicecode.service.impl;


import com.example.loginservice.servicecode.entity.Building;
import com.example.loginservice.servicecode.mapper.BuildingMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.IBuildingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements IBuildingService {

}
