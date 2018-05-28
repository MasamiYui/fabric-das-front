package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.Music;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface MusicMapper {

    int addMusic(Music music);

    List<HashMap> selectMusicAssertList(@Param("userId") String userId);

    Long selectMusicAssertTotal(@Param("userId") String userId);

    HashMap selectMusicRecordById(String id);

}
