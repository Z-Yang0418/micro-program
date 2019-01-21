package com.company.program.resource.resource.respository;

import com.company.program.resource.resource.entity.ProgramList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProgramListRepository extends JpaSpecificationExecutor<ProgramList>,JpaRepository<ProgramList, Long> {


    @Query(value="SELECT * FROM `hndt_programlist_table` t WHERE t.channel_id=?1 AND FIND_IN_SET(?2, t.weekset) AND ?3 BETWEEN t.starttime AND IF(t.endtime=0,9999999999,t.endtime) AND t.`status`= 1 ORDER BY t.addtime DESC LIMIT 1", nativeQuery=true)
    List<ProgramList> findByChannelIdAndWeekdayAndValidTime(Long channelId, String weekStr, Long time);



    /**
     *  方法命名自动匹配
     * @param
     * @return
     */



}
