package com.example.loginservice.servicecode.service.impl;


import com.example.loginservice.servicecode.entity.Exchange;
import com.example.loginservice.servicecode.mapper.ExchangeMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.IExchangeService;
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
public class ExchangeServiceImpl extends ServiceImpl<ExchangeMapper, Exchange> implements IExchangeService {

    @Resource
    private ExchangeMapper exchangeMapper;


    @Override
    public List<Map<String, Object>> selectExchangeByIdInfo(String studentId) {

        return exchangeMapper.selectExchangeByIdInfo(studentId);
    }


    @Override
    public List<Map<String, Object>> selectExchangeByIdResultInfo(String studentId) {
        return exchangeMapper.selectExchangeByIdResultInfo(studentId);
    }

    @Override
    public List<Map<String, Object>> selectExchangeInfo() {

        return exchangeMapper.selectExchangeInfo();
    }


}
