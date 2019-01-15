

package com.company.program.resource.resource.service.impl;

import com.company.program.resource.resource.dto.ProgramInfoDTO;
import com.company.program.resource.resource.entity.ProgramInfo;
import com.company.program.resource.resource.respository.ProgramInfoRepository;
import com.company.program.resource.resource.service.ProgramInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProgramInfoServiceImpl implements ProgramInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProgramInfoServiceImpl.class);

    @Autowired
    private ProgramInfoRepository programInfoRepository;


    @Override
    public List<ProgramInfoDTO> getProgramInfoByChannelId(Long channelId) {

        return convertChannelInfoEntityToDTOs(programInfoRepository.findByChannelIdAndPrograminfoDate(channelId, new Date()));
    }

    @Override
    public List<ProgramInfoDTO> getProgramInfoByDate(Date date) {
        return convertChannelInfoEntityToDTOs(programInfoRepository.findByPrograminfoDate(date));
    }


    ////以下是dto entity 互转方法
    private ProgramInfoDTO convertUserInfoEntityToDTO(ProgramInfo entity){
        ProgramInfoDTO dto = new ProgramInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<ProgramInfoDTO> convertChannelInfoEntityToDTOs(List<ProgramInfo> entitys){
        List<ProgramInfoDTO> dtos = new ArrayList<>();
        for(ProgramInfo e : entitys){
            dtos.add(convertUserInfoEntityToDTO(e));
        }
        return dtos;
    }
}
