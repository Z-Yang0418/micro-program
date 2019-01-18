

package com.company.program.resource.resource.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.program.resource.resource.common.util.DateUtil;
import com.company.program.resource.resource.common.util.FastJsonUtils;
import com.company.program.resource.resource.dto.ChannelInfoDTO;
import com.company.program.resource.resource.dto.ProgramInfoDTO;
import com.company.program.resource.resource.dto.ProgramListDTO;
import com.company.program.resource.resource.entity.ChannelInfo;
import com.company.program.resource.resource.entity.ProgramInfo;
import com.company.program.resource.resource.entity.ProgramList;
import com.company.program.resource.resource.respository.ChannelInfoRepository;
import com.company.program.resource.resource.respository.ProgramInfoRepository;
import com.company.program.resource.resource.respository.ProgramListRepository;
import com.company.program.resource.resource.service.GetVodService;
import com.company.program.resource.util.MapUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Service
@Transactional
public class GetVodServiceImpl implements GetVodService {

    private static final Logger logger = LoggerFactory.getLogger(GetVodServiceImpl.class);

    @Autowired
    private ChannelInfoRepository channelInfoRepository;
    @Autowired
    private ProgramInfoRepository programInfoRepository;
    @Autowired
    private ProgramListRepository programListRepository;

    @Value("${playURL.prefix}")
    private String playURLPrefix;
    @Value("${playURL.suffix}")
    private String playURLSuffix;

    @Value("${downloadUrl.prefix}")
    private String downloadUrlPrefix;
    @Value("${downloadUrl.suffix}")
    private String downloadUrlSuffix;


    @Override
    public Map getVodByChannelIdAndTimestamp(String channelId, String timestamp) {
        String weekStr = ""; //得到当前周数
        boolean isFuture = false;
        long time_stamp = 0;
        List<ProgramInfoDTO> programInfoList = null;
        List<ProgramListDTO> programListDTOs = null;
        Map channelMap = new LinkedHashMap();
        if (StringUtils.isBlank(channelId)){
            channelId = "1";
        }
        long channel_id = Long.parseLong(channelId);
        List<ChannelInfoDTO> channelInfoList = convertChannelInfoEntityToDTOs(channelInfoRepository.findByChannelId(channel_id));
        //获取当前频道，日期下所有节目单信息
        String channelTimeFormat = "yyyy-MM-dd";
        Date channelDate;
        if(StringUtils.isBlank(timestamp)){
            channelDate = new Date();
        }else{
            time_stamp = Long.parseLong(timestamp);
            channelDate = DateUtil.timeStampToDate(timestamp, channelTimeFormat);
            weekStr = DateUtil.parseDateToWeek(channelDate);
            //判断传入的时间是否在当前日期之后/天
            isFuture = DateUtil.isFutureDay(channelDate);
        }
        //得到传入时间得星期数
        if(!isFuture){//如果是当天或者之前的，则读节目单日志表
            programInfoList = convertProgramInfoEntityToDTOs(programInfoRepository.findByChannelIdAndPrograminfoDate(channel_id, channelDate));
        }else{
            programListDTOs = convertProgramListEntityToDTOs(programListRepository.findByChannelIdAndWeekdayAndValidTime(channel_id, weekStr, time_stamp));
        }


        //提取频率表信息并转化成与原接口一致的标准参数
        String channelInfoJsonStr = channelInfoList.get(0).getChannelInfo();
        //将channelInfo字段内的json数据转为object
        JSONObject channelJsonObj = FastJsonUtils.convertStringToJSONObject(channelInfoJsonStr);
        channelMap.put("cid", channelInfoList.get(0).getChannelId());
        channelMap.put("description", channelJsonObj.getString("desc"));
        channelMap.put("image", channelJsonObj.getString("logo"));
        channelMap.put("name", channelInfoList.get(0).getChannelName());
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
        channelMap.put("update_id", 0);
        channelMap.put("isprograms", 0);
        channelMap.put("live", "");
        channelMap.put("time", "");

        if(!isFuture && programInfoList.size()!=0){
            try {
                //组装对应频率日期下正在播出的节目单详情参数
                this.assembedProgramInfoParams(programInfoList.get(0), channelMap);
                //组装该频道下的所有节目单详情信息
                this.assembedProgramInfosParams(programInfoList.get(0), channelMap);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return MapUtil.nullToEmpty(channelMap);
    }


    /**
     * 将频率下正在播出的节目单详情参数组装进接口
     * @param programInfoDTO
     * @param channelMap
     * @throws Exception
     */
    private void assembedProgramInfoParams(ProgramInfoDTO programInfoDTO, Map channelMap) throws Exception {
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
     * 将该频率下对应日期的节目单详情参数组装进接口
     * @param programInfoDTO
     * @param channelMap
     */
    private void assembedProgramInfosParams(ProgramInfoDTO programInfoDTO, Map channelMap) {
        List<Map> programMapList = new ArrayList(50);
        JSONArray jsonArr = FastJsonUtils.parseArray(programInfoDTO.getPrograminfoInfo().trim());
        for (Iterator iterator = jsonArr.iterator(); iterator.hasNext();) {
            JSONObject job = (JSONObject) iterator.next();
            Map map = new LinkedHashMap();
            String programTimeFormat = "yyyy-MM-dd HH:mm";
            String beginTime = programInfoDTO.getPrograminfoDate()+" "+job.getString("start");
            String endTime = programInfoDTO.getPrograminfoDate()+" "+job.getString("end");
            map.put("beginTime", DateUtil.date2TimeStamp(beginTime, programTimeFormat));
            map.put("endTime", DateUtil.date2TimeStamp(endTime, programTimeFormat));
            map.put("title", job.getString("title"));
            map.put("signa", job.getString("signa"));
            if(job.containsKey("filepath")){
                JSONArray filePathJsonArray = FastJsonUtils.parseArray(job.getString("filepath"));
                String filePath = filePathJsonArray.getString(0);
                String playUrl = playURLPrefix+filePath+playURLSuffix;
                String downloadUrl = downloadUrlPrefix+filePath+downloadUrlSuffix;
                map.put("playUrl", new String[]{playUrl});
                map.put("downloadUrl", new String[]{downloadUrl});
            }
            if(job.containsKey("comperename")){
                map.put("comperename", job.getString("comperename"));
            }
            if(job.containsKey("compere")){
                map.put("compere", job.getString("compere"));
            }

            programMapList.add(map);
        }
        channelMap.put("programs", programMapList);
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
}
