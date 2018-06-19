package org.it611.das.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {

    @RequestMapping("/authenticatino/drivingLicense")
    public String  authenticatinoDrivingLicense(){
        return "authentication_drivingLicenseAssert";
    }

}
