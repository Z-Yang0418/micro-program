

package com.company.program.resource.resource.service.impl;

import com.company.program.resource.resource.common.util.DateUtil;
import com.company.program.resource.resource.dto.ProgramListDTO;
import com.company.program.resource.resource.entity.ProgramInfo;
import com.company.program.resource.resource.entity.ProgramList;
import com.company.program.resource.resource.respository.ChannelInfoRepository;
import com.company.program.resource.resource.respository.ProgramInfoRepository;
import com.company.program.resource.resource.respository.ProgramListRepository;
import com.company.program.resource.resource.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private ChannelInfoRepository channelInfoRepository;
    @Autowired
    private ProgramInfoRepository programInfoRepository;
    @Autowired
    private ProgramListRepository programListRepository;


    @Override
    public void putProListInProInfo(Long channelId, String channelName) {
        String weekStr = DateUtil.parseDateToWeek(new Date()); //得到当前周数
        String timeStamp = DateUtil.timeStamp();    //取得当前时间戳
        long time_stamp = Long.parseLong(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());

        List<ProgramListDTO> programListDTOs = convertProgramListEntityToDTOs(programListRepository.findByChannelIdAndWeekdayAndValidTime(channelId, weekStr, time_stamp));
        if(programListDTOs.size()!=0){
            List<ProgramList> programListList = convertProgramListDTOToEntity(programListDTOs);
            ProgramList programList = programListList.get(0);
            ProgramInfo programInfo = new ProgramInfo();
            programInfo.setPrograminfoName(nowDate+" "+channelName+"节目单test111");
            programInfo.setChannelId(channelId);
            programInfo.setPrograminfoInfo(programList.getProgramlistInfo());
            programInfo.setPrograminfoDate(new Date());

        }


    }








    private ProgramListDTO convertProgramListEntityToDTO(ProgramList entity){
        ProgramListDTO dto = new ProgramListDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<ProgramListDTO> convertProgramListEntityToDTOs(List<ProgramList> entitys){
        List<ProgramListDTO> dtos = new ArrayList<>();
        for(ProgramList e : entitys){
            dtos.add(convertProgramListEntityToDTO(e));
        }
        return dtos;
    }

    private ProgramList convertProgramListDTOToEntity(ProgramListDTO dto){
        ProgramList entity = new ProgramList();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private List<ProgramList> convertProgramListDTOToEntity(List<ProgramListDTO> dtos){
        List<ProgramList> entitys = new ArrayList<>();
        for(ProgramListDTO d : dtos){
            entitys.add(convertProgramListDTOToEntity(d));
        }
        return  entitys;
    }
}
