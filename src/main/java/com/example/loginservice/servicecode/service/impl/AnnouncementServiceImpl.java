package com.example.loginservice.servicecode.service.impl;


import com.example.loginservice.servicecode.entity.Announcement;
import com.example.loginservice.servicecode.mapper.AnnouncementMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.IAnnouncementService;
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
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Map<String, Object>> pageAnnouncementInfo(Integer lens) {
        return announcementMapper.pageAnnouncementInfo(lens);
    }
}
