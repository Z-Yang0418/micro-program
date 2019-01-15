

package com.company.program.resource.resource.service;

import com.company.program.resource.resource.dto.ProgramInfoDTO;

import java.util.Date;
import java.util.List;

public interface ProgramInfoService {

    /**
     * 根据频率id、当前日期查询节目详情信息
     * @param channelId
     * @return
     */
    List<ProgramInfoDTO> getProgramInfoByChannelId(Long channelId);

    /**
     * 根据当前日期查询节目详情信息
     * @param
     * @return
     */
    List<ProgramInfoDTO> getProgramInfoByDate(Date date);
}
