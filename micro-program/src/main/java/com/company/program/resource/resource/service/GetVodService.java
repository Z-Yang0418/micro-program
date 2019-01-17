

package com.company.program.resource.resource.service;

import java.util.Map;

public interface GetVodService {

    /**
     * 指定频率类别列表接口
     * @param channelId
     * @param timestamp
     * @return
     */
    Map getVodByChannelIdAndTimestamp(String channelId, String timestamp);
}
