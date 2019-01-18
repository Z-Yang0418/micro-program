package com.company.program.resource.resource.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Component
@Data
@Table(name="hndt_programlist_table")
public class ProgramList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programlistId;                 //节目ID

    private String programlistName;             //节目单名称

    private String programlistDesc;             //节目单子信息备注

    private String weekset;                     //周设定，默认每天 0＝周日

    private String programlistInfo;             //节目单信息JSON

    private Long channelId;                     //对应的频率ID

    private int starttime;                      //节目单生效时间

    private int endtime;                        //节目单失效时间

    private int addtime;                        //添加时间

    private String adduser;                     //添加者

    private String adduserIp;                   //添加者IP

    private int status;                         //状态0未启用1启用2停用

}

