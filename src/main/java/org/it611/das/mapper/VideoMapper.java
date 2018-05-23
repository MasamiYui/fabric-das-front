package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.Video;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface VideoMapper {

    int addVideo(Video video);

    //查询视频资产总数
    Long selectVideoTotal();

    //查询Video的所有列表
    List<HashMap> selectVideoList();

    //根据id查询Video详情
    HashMap selectVideoDetailById(@Param("id") String id);


}
