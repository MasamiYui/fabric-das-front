package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.DegreeCertificate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface DegreeCertificateMapper {
    int addDegreeCertificate(DegreeCertificate dc);

    //查询学位证书资产总数
    Long selectCertTotal(@Param("userId") String userId);

    //查询学位证书的所有
    List<HashMap> selectCertAssetList(@Param("userId") String userId);

    //根据id查询学位证书详情
    HashMap selectCertDetailById(String id);
}