package org.it611.das.mapper;

import org.it611.das.domain.Company;
import org.springframework.stereotype.Component;

@Component
public interface CompanyMapper {

    int addCompany(Company company);

}
