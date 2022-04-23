package com.example.loginservice.servicecode.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.servicecode.entity.Announcement;

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
public interface IAnnouncementService extends IService<Announcement> {
    //
    List<Map<String, Object>> pageAnnouncementInfo(Integer lens);

}
