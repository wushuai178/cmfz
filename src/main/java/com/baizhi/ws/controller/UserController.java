package com.baizhi.ws.controller;

import com.alibaba.fastjson.JSON;
import com.baizhi.ws.entity.User;
import com.baizhi.ws.entity.UserDto;
import com.baizhi.ws.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("queryUserByPage")
    @ResponseBody
    public Map queryUserByPage(int rows,int page){
        Map map = userService.queryUserByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper, User user){
        if (oper.equals("add")){
            Map map = userService.insertUser(user);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-84a4d8cfd86d40d3b8b8c0283093e80e");
            Map map1 = queryUserByDay();
            String json = JSON.toJSONString(map1);
            goEasy.publish("cmfz", json);
            return map;
        }else if (oper.equals("edit")){
            if (user.getPhoto()!=null&&"".equals(user.getPhoto())){
                user.setPhoto(null);
            }
            return userService.updateUser(user);
        }
        return null;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map upload(String userId, MultipartFile photo, HttpSession session, HttpServletRequest request) throws IOException {
        String url = null;
        if(photo!=null){
            if (!"".equals(photo.getOriginalFilename())){
                String realPath = session.getServletContext().getRealPath("/upload/userPhoto/");
                File file = new File(realPath);
                if (!file.exists()){
                    file.mkdirs();
                }
                String name = new Date().getTime()+"_"+photo.getOriginalFilename();
                String scheme = request.getScheme();
                String ip = InetAddress.getLocalHost().toString();
                int port = request.getServerPort();
                String contextPath = request.getContextPath();
                url = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/upload/userPhoto/"+name;
                photo.transferTo(new File(realPath,name));
                User user = new User();
                user.setId(userId);
                user.setPhoto(url);
                userService.updateUser(user);
            }
        }
        return null;
    }

    @RequestMapping("queryUserByDay")
    @ResponseBody
    public Map queryUserByDay(){
        HashMap map = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userService.queryUserByTime("1", 1));
        manList.add(userService.queryUserByTime("1", 7));
        manList.add(userService.queryUserByTime("1", 30));
        manList.add(userService.queryUserByTime("1", 365));
        ArrayList womanList = new ArrayList();
        womanList.add(userService.queryUserByTime("0",1));
        womanList.add(userService.queryUserByTime("0",7));
        womanList.add(userService.queryUserByTime("0",30));
        womanList.add(userService.queryUserByTime("0",365));
        map.put("man",manList);
        map.put("woman",womanList);
        return map;
    }

    @RequestMapping("queryUserByAddress")
    @ResponseBody
    public Map queryUserByAddress(){
        HashMap map = new HashMap();
        List<UserDto> manList = userService.queryUserByAddress("1");
        List<UserDto> womanList = userService.queryUserByAddress("0");
        map.put("man",manList);
        map.put("woman",womanList);
        return map;
    }
}
