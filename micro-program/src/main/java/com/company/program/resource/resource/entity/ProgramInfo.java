package com.company.program.resource.resource.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Component
@Data
@Table(name="hndt_programinfo_table")
public class ProgramInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programinfoId;                 //节目详情ID

    private String programinfoName;             //节目详情记录名称

    private Long channelId;                     //对应的频率ID

    private String programinfoInfo;             //节目单

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date programinfoDate;               //节目对应日期

    private String programinfoInfo2;            //节目单,备选资源

}

