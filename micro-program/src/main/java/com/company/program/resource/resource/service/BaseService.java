

package com.company.program.resource.resource.service;

public interface BaseService {

    /**
     * 将符合当前频率，时间的节目单模板入库
     * @param channelId
     * @return
     */
    void putProListInProInfo(Long channelId, String channelName);
}
