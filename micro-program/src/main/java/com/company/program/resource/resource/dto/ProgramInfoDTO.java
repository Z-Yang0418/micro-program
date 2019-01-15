package com.company.program.resource.resource.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;


@Data
@ApiModel(value = "ProgramInfoDTO", description = "节目详情信息")
public class ProgramInfoDTO {

    @ApiModelProperty("节目详情ID")
    private Long programinfoId;             //节目详情ID

    @ApiModelProperty("节目详情记录名称")
    private String programinfoName;        //节目详情记录名称

    @ApiModelProperty("对应的频率ID")
    private Long channelId;                //对应的频率ID

    @ApiModelProperty("节目单")
    private String programinfoInfo;        //节目单

    @ApiModelProperty("节目对应日期")
    private Date programinfoDate;          //节目对应日期

    @ApiModelProperty("节目单,备选资源")
    private String programinfoInfo2;       //节目单,备选资源

}

