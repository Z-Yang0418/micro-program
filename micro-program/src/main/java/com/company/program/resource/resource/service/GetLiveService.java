

package com.company.program.resource.resource.service;

import java.util.List;
import java.util.Map;

public interface GetLiveService extends BaseService{

    /**
     * 指定频率类别列表接口
     * @param classId
     * @return
     */
    List<Map> getLiveByClassId(String classId);

    /**
     * 获得全部频率列表接口
     * @return
     */
    List<Map> getLive();

    /**
     * 获得指定频率列表接口
     * @param channelId
     * @return
     */
    List<Map> getLiveByChannelId(String channelId);
}
