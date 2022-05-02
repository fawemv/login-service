package com.example.loginservice.servicecode.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.servicecode.entity.Exchange;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface IExchangeService extends IService<Exchange> {

    // 根据学生id返回该学生的申请信息
    List<Map<String, Object>> selectExchangeByIdInfo(String studentId);

    List<Map<String, Object>> selectExchangeByIdResultInfo(String studentId);

    List<Map<String, Object>> selectExchangeInfo();


}
