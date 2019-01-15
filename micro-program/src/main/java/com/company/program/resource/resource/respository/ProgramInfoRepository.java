package com.company.program.resource.resource.respository;

import com.company.program.resource.resource.entity.ProgramInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;


public interface ProgramInfoRepository extends JpaSpecificationExecutor<ProgramInfo>,JpaRepository<ProgramInfo, Long> {



    /**
     *  方法命名自动匹配
     * @param
     * @return
     */
    List<ProgramInfo> findByChannelIdAndPrograminfoDate(Long channelId, Date date);
}
