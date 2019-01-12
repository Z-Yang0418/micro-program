package com.company.program.resource.resource.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.company.program.resource.resource.common.util.FastJsonUtils;
import com.company.program.resource.resource.dto.ChannelInfoDTO;
import com.company.program.resource.resource.service.ChannelInfoService;
import com.company.program.resource.util.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/get/live")
@Api(value = "频率信息", tags = "ChannelInfoApi", description="频率信息")
public class ChannelInfoController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelInfoController.class);

    @Autowired
    private ChannelInfoService channelInfoService;

    @ApiOperation("指定频率类别列表接口")
    @ApiImplicitParam(name = "classId", value = "根据classId查询频率", required = true, dataType = "String", paramType="path")
    @GetMapping(value = {"/class/{classId}", "/class"})
    public List<Map> getUserInfoByClassId(@PathVariable(value = "classId", required = false) String classId) {
        List<Map> channelMapList = new ArrayList<>();
        List<ChannelInfoDTO> channelInfoList = channelInfoService.getChannelInfoByClassId(classId);
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
            channelMapList.add(MapUtil.nullToEmpty(channelMap));
        }

        return channelMapList;
    }

}
