package com.baizhi.ws.controller;

import com.baizhi.ws.entity.Admin;
import com.baizhi.ws.service.AdminService;
import com.baizhi.ws.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(String password,String username,String code,HttpSession session){
        String code1 = session.getAttribute("code").toString();
        if (code1.equals(code)){
            String loginResult = adminService.login(new Admin(null,username,password));
            return loginResult;
        }
        return "验证码错误！";
    }

    @RequestMapping("/getCode")
    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode cv = new CreateValidateCode();
        String code = cv.getCode();
        session.setAttribute("code",code);

        cv.write(response.getOutputStream());
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/jsp/login.jsp";
    }
}
