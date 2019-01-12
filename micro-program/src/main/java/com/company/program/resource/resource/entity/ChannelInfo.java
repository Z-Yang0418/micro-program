package com.company.program.resource.resource.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Entity
@Component
@Data
@Table(name="hndt_channel_table")
public class ChannelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;         //ID

    private String classId;         //分类ID

    private String channelName;     //频率名称

    @Column(name="channel_name_e", length = 1)
    private String channelNameE;    //频率名称音序

    private int showOrder;          //制定排序

    private String channelInfo;     //频率信息JSON

    private int status;             //status



}

