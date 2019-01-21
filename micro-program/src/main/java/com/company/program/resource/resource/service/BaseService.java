

package com.company.program.resource.resource.service;

import com.company.program.resource.resource.dto.ProgramInfoDTO;
import com.company.program.resource.resource.entity.ProgramInfo;

public interface BaseService {

    /**
     * 将符合当前频率，时间的节目单模板入库
     * @param channelId
     * @return
     */
    ProgramInfoDTO putProListInProInfo(Long channelId, String channelName);
}
