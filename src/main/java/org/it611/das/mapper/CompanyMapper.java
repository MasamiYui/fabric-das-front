package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.Company;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface CompanyMapper {

    int addCompany(Company company);

    HashMap<String, Object> findCompanyByCreditId(@Param("creditId") String creditId);

    HashMap<String, Object> findCompanyByUsername(@Param("username") String username);

    HashMap<String, Object> findCompanyByPhone(@Param("phone") String phone);

    HashMap<String, Object> findCompanyByEmail(@Param("email") String email);
}
