package com.company.program.resource.resource.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;


@Data
@ApiModel(value = "ProgramInfoDTO", description = "节目单日志信息")
public class ProgramInfoDTO {

    @ApiModelProperty("节目详情ID")
    private Long programinfoId;

    @ApiModelProperty("节目详情记录名称")
    private String programinfoName;

    @ApiModelProperty("对应的频率ID")
    private Long channelId;

    @ApiModelProperty("节目单")
    private String programinfoInfo;

    @ApiModelProperty("节目对应日期")
    private Date programinfoDate;

    @ApiModelProperty("节目单,备选资源")
    private String programinfoInfo2;

}

