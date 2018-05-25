package org.it611.das.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/frontLogin")
    public String frontLogin(){  return "frontLogin";}

    @RequestMapping("/home")
    public String  home(){ return "home";}

}
