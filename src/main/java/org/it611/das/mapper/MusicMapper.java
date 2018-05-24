package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.Music;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface MusicMapper {

    int addMusic(Music music);

    //查询Music资产总数
    Long selectMusicTotal();

    //查询Music的所有列表
    List<HashMap> selectMusicList();

    //根据id查询Music详情
    HashMap selectMusicDetailById(@Param("id") String id);


}
