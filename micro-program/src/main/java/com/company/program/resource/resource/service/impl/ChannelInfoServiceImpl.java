

package com.company.program.resource.resource.service.impl;

import com.company.program.resource.resource.dto.ChannelInfoDTO;
import com.company.program.resource.resource.entity.ChannelInfo;
import com.company.program.resource.resource.respository.ChannelInfoRepository;
import com.company.program.resource.resource.service.ChannelInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChannelInfoServiceImpl implements ChannelInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelInfoServiceImpl.class);

    @Autowired
    private ChannelInfoRepository channelInfoRepository;

    @Override
    public List<ChannelInfoDTO> getChannelInfoByClassId(String classId) {
        if (StringUtils.isBlank(classId)){
            classId = "1";
        }
        long class_id = Long.parseLong(classId);
        return convertChannelInfoEntityToDTOs(channelInfoRepository.findByClassId(class_id));
    }

    @Override
    public List<ChannelInfoDTO> getChannelInfoById(String channelId) {
        if (StringUtils.isBlank(channelId)){
            channelId = "1";
        }
        long channel_id = Long.parseLong(channelId);
        return convertChannelInfoEntityToDTOs(channelInfoRepository.findByChannelId(channel_id));
    }





    ////以下是dto entity 互转方法
    private ChannelInfoDTO convertUserInfoEntityToDTO(ChannelInfo entity){
        ChannelInfoDTO dto = new ChannelInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<ChannelInfoDTO> convertChannelInfoEntityToDTOs(List<ChannelInfo> entitys){
        List<ChannelInfoDTO> dtos = new ArrayList<>();
        for(ChannelInfo e : entitys){
            dtos.add(convertUserInfoEntityToDTO(e));
        }
        return dtos;
    }
}
