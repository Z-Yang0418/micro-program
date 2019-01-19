package com.company.program.resource.resource.controller;

import com.company.program.resource.resource.service.GetLiveService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/get/live")
@Api(value = "/get/live路径接口集合", tags = "GetLiveApi", description="/get/live路径接口集合")
public class GetLiveController {

    @Autowired
    private GetLiveService getLiveService;

    @ApiOperation("指定频率类别列表接口")
    @ApiImplicitParam(name = "classId", value = "频率所属分类ID", required = false, dataType = "String", paramType="path")
    @GetMapping(value = {"/class/{classId}", "/class"})
    public List<Map> getLiveByClassId(@PathVariable(value = "classId", required = false) String classId) {
        return getLiveService.getLiveByClassId(classId);
    }

    @ApiOperation("全部频率列表接口")
    @GetMapping(value = "")
    public List<Map> getLive() {
        return getLiveService.getLive();
    }

    @ApiOperation("指定频率列表接口")
    @GetMapping(value = {"/channel/{channelId}", "/channel"})
    public List<Map> getLiveByChannelId(@PathVariable(value = "channelId", required = false) String channelId) {
        return getLiveService.getLiveByChannelId(channelId);
    }

}
