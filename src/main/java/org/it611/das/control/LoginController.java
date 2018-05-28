package org.it611.das.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @RequestMapping("/frontLogin")
    public String frontLogin(){  return "frontLogin";}

    @RequestMapping("/user/commonUser")
    public String  commonUserhome(){ return "commonUserHome";}

    @RequestMapping("/user/companyUser")
    public String  companyUserhome(){ return "companyUserHome";}


}
