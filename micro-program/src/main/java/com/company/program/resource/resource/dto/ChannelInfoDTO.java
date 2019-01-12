package com.company.program.resource.resource.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@ApiModel(value = "ChannelInfoDTO", description = "频率信息")
public class ChannelInfoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("频率id")
    private Long channelId;

    @ApiModelProperty("分类ID")
    private String classId;

    @ApiModelProperty("频率名称")
    private String channelName;

    @ApiModelProperty("频率名称音序")
    private String channelNameE;

    @ApiModelProperty("制定排序")
    private int showOrder;

    @ApiModelProperty("频率信息JSON")
    private String channelInfo;

    @ApiModelProperty("status")
    private int status;



}
