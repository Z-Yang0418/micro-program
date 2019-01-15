

package com.company.program.resource.resource.service;

import java.util.List;
import java.util.Map;

public interface GetLiveService {

    /**
     * 指定频率类别列表接口
     * @param classId
     * @return
     */
    List<Map> getLiveByClassId(String classId);
}