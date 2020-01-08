package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.AdminDao;
import com.baizhi.ws.entity.Admin;
import com.baizhi.ws.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminDao adminDao;
    @Autowired
    HttpSession session;

    @Override
    public String login(Admin admin) {
        String password = admin.getPassword();
        admin.setId(null);
        admin.setPassword(null);
        Admin admin1 = adminDao.selectOne(admin);
        if (admin1==null){
            return "用户不存在！";
        }else {
            if (admin1.getPassword().equals(password)){
                session.setAttribute("admin",admin1);
                return "success";
            }else {
                return "密码错误！";
            }
        }
    }
}
