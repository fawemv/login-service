package com.example.loginservice.servicecode.service.impl;


import com.example.loginservice.servicecode.entity.Repairs;
import com.example.loginservice.servicecode.mapper.RepairsMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.IRepairsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Service
public class RepairsServiceImpl extends ServiceImpl<RepairsMapper, Repairs> implements IRepairsService {
    @Resource
    RepairsMapper repairsMapper;

    @Override
    public List<Map<String, Object>> selectRepairsByIdInfo(String studentIdStr) {


        return repairsMapper.selectRepairsByIdInfo(studentIdStr);
    }

    @Override
    public List<Repairs> getInitData() {

        return  repairsMapper.getInitData();
    }
}
