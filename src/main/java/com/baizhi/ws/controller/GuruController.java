package com.baizhi.ws.controller;

import com.baizhi.ws.entity.Guru;
import com.baizhi.ws.service.GuruService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("queryAllGuru")
    @ResponseBody
    public List<Guru> queryAllGuru(){
        return guruService.queryAll();
    }

    @RequestMapping("queryByPage")
    @ResponseBody
    public Map queryByPage(int page,int rows){
        Map map = guruService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper,Guru guru,String[] id){
        if (oper.equals("add")){
            return guruService.insertGuru(guru);
        }else if (oper.equals("edit")){
            return guruService.updateGuru(guru);
        }else {
            guruService.deleteGuru(Arrays.asList(id));
        }
        return null;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map upload(String guruId, MultipartFile photo, HttpSession session, HttpServletRequest request) throws IOException {
        String url = null;
        if(photo!=null){
            if (!"".equals(photo.getOriginalFilename())){
                String realPath = session.getServletContext().getRealPath("/upload/guruPhoto/");
                File file = new File(realPath);
                if (!file.exists()){
                    file.mkdirs();
                }
                String name = new Date().getTime()+"_"+photo.getOriginalFilename();
                String scheme = request.getScheme();
                String ip = InetAddress.getLocalHost().toString();
                int port = request.getServerPort();
                String contextPath = request.getContextPath();
                url = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/upload/guruPhoto/"+name;
                photo.transferTo(new File(realPath,name));
            }
        }
        Guru guru = new Guru();
        guru.setId(guruId);
        guru.setPhoto(url);
        guruService.updateGuru(guru);
        return null;
    }
}
