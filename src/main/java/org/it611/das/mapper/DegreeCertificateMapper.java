package org.it611.das.mapper;

import org.it611.das.domain.DegreeCertificate;
import org.springframework.stereotype.Component;

@Component
public interface DegreeCertificateMapper {
    int addDegreeCertificate(DegreeCertificate dc);
}
