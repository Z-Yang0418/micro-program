package com.company.program.resource.resource.controller;

import com.company.program.resource.resource.service.GetVodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/get/vod")
@Api(value = "/get/vod路径接口集合", tags = "GetVodApi", description="/get/vod路径接口集合")
public class GetVodController {
    @Autowired
    private GetVodService getVodService;

    @ApiOperation("点播接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "频率id", required = false, dataType = "String", paramType="path"),
            @ApiImplicitParam(name = "timestamp", value = "当前时间戳", required = false, dataType = "String", paramType="path")
    })
    @GetMapping(value = {"/{channelId}/{timestamp}", "/{channelId}", "/"})
    public List<Map> getLiveByClassId(@PathVariable(value = "channelId", required = false) String channelId,
                                      @PathVariable(value = "timestamp", required = false) String timestamp){


        return getVodService.getVodByChannelIdAndTimestamp(channelId, timestamp);

    }

}
