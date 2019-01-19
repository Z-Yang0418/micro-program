

package com.company.program.resource.resource.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.program.resource.resource.common.util.FastJsonUtils;
import com.company.program.resource.resource.dto.ChannelInfoDTO;
import com.company.program.resource.resource.dto.ProgramInfoDTO;
import com.company.program.resource.resource.entity.ChannelInfo;
import com.company.program.resource.resource.entity.ProgramInfo;
import com.company.program.resource.resource.respository.ChannelInfoRepository;
import com.company.program.resource.resource.respository.ProgramInfoRepository;
import com.company.program.resource.resource.service.GetLiveService;
import com.company.program.resource.util.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class GetLiveServiceImpl implements GetLiveService {

    private static final Logger logger = LoggerFactory.getLogger(GetLiveServiceImpl.class);

    @Autowired
    private ChannelInfoRepository channelInfoRepository;
    @Autowired
    private ProgramInfoRepository programInfoRepository;


    /**
     * 指定频率列表接口方法
     * @param classId
     * @return
     */
    @Override
    public List<Map> getLiveByClassId(String classId) {
        List<Map> channelMapList = new ArrayList<>();
        if (StringUtils.isBlank(classId)){
            classId = "1";
        }
        long class_id = Long.parseLong(classId);
        List<ChannelInfoDTO> channelInfoList = convertChannelInfoEntityToDTOs(channelInfoRepository.findByClassId(class_id));
        //获取当前日期下所有节目单信息
        List<ProgramInfoDTO> programInfoDTOS = convertProgramInfoEntityToDTOs(programInfoRepository.findByPrograminfoDate(new Date()));


        //提取并转化成与原接口一致的标准参数
        for(ChannelInfoDTO channelInfoDTO : channelInfoList){
            Map channelMap = new LinkedHashMap();
            String channelInfoJsonStr = channelInfoDTO.getChannelInfo();
            //将channelInfo字段内的json数据转为object
            JSONObject channelJsonObj = FastJsonUtils.convertStringToJSONObject(channelInfoJsonStr);
            channelMap.put("cid", channelInfoDTO.getChannelId());
            channelMap.put("description", channelJsonObj.getString("desc"));
            channelMap.put("image", channelJsonObj.getString("logo"));
            channelMap.put("name", channelInfoDTO.getChannelName());
            channelMap.put("hotline", channelJsonObj.getString("channel_hotline"));
            if (channelJsonObj.containsKey("interact")){
                channelMap.put("interact", channelJsonObj.getString("interact_set"));
            } else {
                channelMap.put("interact", "0");
            }

            if(channelJsonObj.containsKey("streams")){
                channelMap.put("streams", new String[]{channelJsonObj.getString("streams")});
            } else {
                channelMap.put("streams", "");
            }
            if(channelJsonObj.containsKey("video_streams")){
                channelMap.put("video_streams", new String[]{channelJsonObj.getString("video_streams")});
            } else {
                channelMap.put("video_streams", new String[]{""});
            }

            //默认该频率下没有节目单
            channelMap.put("update_id", "");
            channelMap.put("isprograms", 0);
            channelMap.put("live", "");
            channelMap.put("time", "");

            //组装对应频率日期下的节目单详情参数
            for(ProgramInfoDTO programInfoDTO : programInfoDTOS){
                if(programInfoDTO.getChannelId().equals(channelInfoDTO.getChannelId())){
                    try {
                        this.assembedProgramParams(programInfoDTO, channelMap);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }

            }

            channelMapList.add(MapUtil.nullToEmpty(channelMap));
        }

        return channelMapList;
    }

    /**
     * 全部频率列表接口方法
     * @return
     */
    @Override
    public List<Map> getLive() {
        List<Map> channelMapList = new ArrayList<>();

        List<ChannelInfoDTO> channelInfoList = convertChannelInfoEntityToDTOs(channelInfoRepository.findAllLive());
        //获取当前日期下所有节目单信息
        List<ProgramInfoDTO> programInfoDTOS = convertProgramInfoEntityToDTOs(programInfoRepository.findByPrograminfoDate(new Date()));

        //提取并转化成与原接口一致的标准参数
        for(ChannelInfoDTO channelInfoDTO : channelInfoList){
            Map channelMap = new LinkedHashMap();
            String channelInfoJsonStr = channelInfoDTO.getChannelInfo();
            //将channelInfo字段内的json数据转为object
            JSONObject channelJsonObj = FastJsonUtils.convertStringToJSONObject(channelInfoJsonStr);
            channelMap.put("cid", channelInfoDTO.getChannelId());
            channelMap.put("description", channelJsonObj.getString("desc"));
            channelMap.put("image", channelJsonObj.getString("logo"));
            channelMap.put("name", channelInfoDTO.getChannelName());
            channelMap.put("hotline", channelJsonObj.getString("channel_hotline"));
            if (channelJsonObj.containsKey("interact")){
                channelMap.put("interact", channelJsonObj.getString("interact_set"));
            } else {
                channelMap.put("interact", "0");
            }

            if(channelJsonObj.containsKey("streams")){
                channelMap.put("streams", new String[]{channelJsonObj.getString("streams")});
            } else {
                channelMap.put("streams", "");
            }
            if(channelJsonObj.containsKey("video_streams")){
                channelMap.put("video_streams", new String[]{channelJsonObj.getString("video_streams")});
            } else {
                channelMap.put("video_streams", new String[]{""});
            }

            //默认该频率下没有节目单
            channelMap.put("update_id", "");
            channelMap.put("isprograms", 0);
            channelMap.put("live", "");
            channelMap.put("time", "");

            //组装对应频率日期下的节目单详情参数
            for(ProgramInfoDTO programInfoDTO : programInfoDTOS){
                if(programInfoDTO.getChannelId().equals(channelInfoDTO.getChannelId())){
                    try {
                        this.assembedProgramParams(programInfoDTO, channelMap);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }

            }

            channelMapList.add(MapUtil.nullToEmpty(channelMap));
        }

        return channelMapList;
    }



    //-------------------------------共有方法----------------------------------------------


    /**
     * 将频率下正在播出的节目参数，组装进接口
     * @param programInfoDTO
     * @param channelMap
     * @throws Exception
     */
    private void assembedProgramParams(ProgramInfoDTO programInfoDTO, Map channelMap) throws Exception {
        channelMap.put("update_id", programInfoDTO.getPrograminfoId());
        JSONArray jsonArr = FastJsonUtils.parseArray(programInfoDTO.getPrograminfoInfo().trim());
        int isProgram = 1;
        for (Iterator iterator = jsonArr.iterator(); iterator.hasNext();) {
            JSONObject job = (JSONObject) iterator.next();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String nowDate = format.format(new Date());
            String startDate = job.getString("start");
            String endDate = job.getString("end");
            channelMap.put("update_id", programInfoDTO.getPrograminfoId());
            channelMap.put("isprograms", isProgram);

            //得到属于当前时间的时间段的节目详情
            if(this.hourMinuteBetween(nowDate, startDate, endDate)){
                channelMap.put("update_id", programInfoDTO.getPrograminfoId());
                channelMap.put("isprograms", isProgram);
                channelMap.put("live", job.getString("title"));
                channelMap.put("time", startDate+"-"+endDate);
                break;
            }

        }

    }


    /**
     * 判断当前时间是否在节目开始结束时间之内
     * @param nowDate   要比较的时间
     * @param startDate   开始时间
     * @param endDate   结束时间
     * @return   true在时间段内，false不在时间段内
     * @throws Exception
     */
    public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) throws Exception{

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        Date now = format.parse(nowDate);
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);

        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime >= startTime && nowTime <= endTime;
    }

    ////以下是dto entity 互转方法
    private ChannelInfoDTO convertChannelInfoEntityToDTO(ChannelInfo entity){
        ChannelInfoDTO dto = new ChannelInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<ChannelInfoDTO> convertChannelInfoEntityToDTOs(List<ChannelInfo> entitys){
        List<ChannelInfoDTO> dtos = new ArrayList<>();
        for(ChannelInfo e : entitys){
            dtos.add(convertChannelInfoEntityToDTO(e));
        }
        return dtos;
    }

    private ProgramInfoDTO convertProgramInfoEntityToDTO(ProgramInfo entity){
        ProgramInfoDTO dto = new ProgramInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<ProgramInfoDTO> convertProgramInfoEntityToDTOs(List<ProgramInfo> entitys){
        List<ProgramInfoDTO> dtos = new ArrayList<>();
        for(ProgramInfo e : entitys){
            dtos.add(convertProgramInfoEntityToDTO(e));
        }
        return dtos;
    }
}
