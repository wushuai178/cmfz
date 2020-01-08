package com.baizhi.ws.controller;

import com.alibaba.fastjson.JSON;
import com.baizhi.ws.dao.ChatDao;
import com.baizhi.ws.entity.Admin;
import com.baizhi.ws.entity.Chat;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("chat")
public class ChatController {
    @Autowired
    ChatDao chatDao;
    @Autowired
    HttpSession session;

    @RequestMapping("addChat")
    public Map addChat(Chat chat){
        chat.setCreateDate(new Date());
        chat.setId(UUID.randomUUID().toString());
        Admin admin =  (Admin) session.getAttribute("admin");
        chat.setUserName(admin.getUsername());
        chatDao.insertSelective(chat);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-84a4d8cfd86d40d3b8b8c0283093e80e");
        List<Chat> chats = queryAllContent();
        String json = JSON.toJSONString(chats);
        goEasy.publish("cmfz",json);
        HashMap map1 = new HashMap();
        map1.put("status",200);
        return map1;
    }


    @RequestMapping("queryAllContent")
    public List<Chat> queryAllContent(){
        List<Chat> chats = chatDao.queryByTime();
        return chats;
    }
}
