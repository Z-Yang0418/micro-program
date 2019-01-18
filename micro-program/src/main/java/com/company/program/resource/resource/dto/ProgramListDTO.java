package com.company.program.resource.resource.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "ProgramListDTO", description = "节目单信息")
public class ProgramListDTO {

    @ApiModelProperty("节目ID")
    private Long programinfoId;

    @ApiModelProperty("节目单名称")
    private String programlistName;

    @ApiModelProperty("节目单子信息备注")
    private String programlistDesc;

    @ApiModelProperty("周设定，默认每天 0＝周日")
    private String weekset;

    @ApiModelProperty("节目单信息JSON")
    private String programlistInfo;

    @ApiModelProperty("对应的频率ID")
    private Long channelId;

    @ApiModelProperty("节目单生效时间")
    private int starttime;

    @ApiModelProperty("节目单失效时间")
    private int endtime;

    @ApiModelProperty("添加时间")
    private int addtime;

    @ApiModelProperty("添加者")
    private String adduser;

    @ApiModelProperty("添加者IP")
    private String adduserIp;

    @ApiModelProperty("状态0未启用1启用2停用")
    private int status;

}

