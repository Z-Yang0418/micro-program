

package com.company.program.resource.resource.service;

import com.company.program.resource.resource.dto.ChannelInfoDTO;
import com.company.program.resource.resource.dto.UserInfoDTO;
import com.company.program.resource.resource.dto.UserInfoQuery;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ChannelInfoService {

    /**
     * 根据归属分类id查询频率
     * @param classId
     * @return
     */
    List<ChannelInfoDTO> getChannelInfoByClassId(String classId);
}
