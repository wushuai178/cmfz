package com.baizhi.ws.controller;

import com.baizhi.ws.entity.Album;
import com.baizhi.ws.service.AlbumService;
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
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping("queryAlbumByPage")
    @ResponseBody
    public Map queryAlbumByPage(int page,int rows){
        Map map = albumService.queryAlbumByPage(page, rows);
        return map;
    }


    @RequestMapping("edit")
    @ResponseBody
    public Map edit(Album album,String oper,String[] id){
        if (oper.equals("add")){
           return albumService.insert(album);
        }else if (oper.equals("del")){
            albumService.delete(Arrays.asList(id));
        }else {
            return albumService.update(album);
        }
        return null;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map upload(MultipartFile img, String albumId, HttpServletRequest request, HttpSession session){
        if (!"".equals(img.getOriginalFilename())){
            //获取真实路径
            String realPath = session.getServletContext().getRealPath("/frontback/images");
            //创建上传文件夹
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            //防止重名
            String originalFilename =new Date().getTime()+"_"+img.getOriginalFilename();
            //获取协议名
            String scheme = request.getScheme();
            //获取ip
            String ip ="";
            try {
                ip = InetAddress.getLocalHost().toString();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            //获取端口号
            int port = request.getServerPort();
            //获取项目名
            String contextPath = request.getContextPath();
            //新url
            String urlName = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/frontback/images/"+originalFilename;
            try {
                img.transferTo(new File(realPath,originalFilename));
                Album album = new Album();
                album.setImg(urlName);
                album.setId(albumId);
                albumService.update(album);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
