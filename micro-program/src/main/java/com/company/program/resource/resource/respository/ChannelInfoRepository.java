package com.company.program.resource.resource.respository;

import com.company.program.resource.resource.entity.ChannelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChannelInfoRepository extends JpaSpecificationExecutor<ChannelInfo>,JpaRepository<ChannelInfo, Long> {

    @Query(value="SELECT * FROM hndt_channel_table WHERE FIND_IN_SET(?1, class_id) AND status=1",nativeQuery=true)
    List<ChannelInfo> findByClassId(Long classId);

    @Query(value="SELECT * FROM hndt_channel_table WHERE status=1",nativeQuery=true)
    List<ChannelInfo> findAllLive();

    @Query(value="SELECT * FROM hndt_channel_table WHERE channel_id=?1 AND status=1",nativeQuery=true)
    List<ChannelInfo> findByChannelId(Long channelId);

    /**
     *  方法命名自动匹配
     * @param
     * @return
     */

}
