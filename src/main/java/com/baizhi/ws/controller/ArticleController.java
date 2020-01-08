package com.baizhi.ws.controller;

import com.baizhi.ws.entity.Article;
import com.baizhi.ws.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("queryAllByPage")
    @ResponseBody
    public Map queryAllByPage(int page,int rows){
        Map map = articleService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("remove")
    public String remove(String id){
        articleService.delete(id);
        return "redirect:/jsp/main.jsp";
    }


    @RequestMapping("addArticle")
    @ResponseBody
    public String addArticle(Article article, MultipartFile imgageAdd, HttpSession session, HttpServletRequest request){
        System.out.println("content"+article.getContent());
        article.setId(UUID.randomUUID().toString());
        article.setPublishDate(new Date());
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/articleCover/");
        //创建上传文件夹
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        //防止重名
        String originalFilename =new Date().getTime()+"_"+imgageAdd.getOriginalFilename();
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
        String urlName = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/upload/articleCover/"+originalFilename;
        try {
            imgageAdd.transferTo(new File(realPath,originalFilename));
            article.setImg(urlName);
            article.setId(article.getId());
            articleService.insert(article);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("updateArticle")
    @ResponseBody
    public String updateArticle(Article article, MultipartFile imgageEdit, HttpSession session, HttpServletRequest request){
        if ("".equals(article.getContent())){
            article.setContent(null);
        }
        if (imgageEdit!=null&&!"".equals(imgageEdit.getOriginalFilename())){
            //获取真实路径
            String realPath = session.getServletContext().getRealPath("/upload/articleCover/");
            //创建上传文件夹
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            //防止重名
            String originalFilename =new Date().getTime()+"_"+imgageEdit.getOriginalFilename();
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
            String urlName = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/upload/articleCover/"+originalFilename;
            try {
                imgageEdit.transferTo(new File(realPath,originalFilename));
                article.setImg(urlName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        articleService.update(article);
        return "ok";
    }

    @RequestMapping("queryArticleById")
    @ResponseBody
    public Article queryArticleById(String id){
        return articleService.queryById(id);
    }

    @RequestMapping("uploadImage")
    @ResponseBody
    public Map uploadImage(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        HashMap map = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImages/");
        //创建上传文件夹
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        //防止重名
        String originalFilename =new Date().getTime()+"_"+imgFile.getOriginalFilename();
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
        String urlName = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/upload/articleImages/"+originalFilename;
        try {
            imgFile.transferTo(new File(realPath,originalFilename));
            map.put("error",0);
            map.put("url",urlName);
        } catch (IOException e) {
            map.put("error",1);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("showImage")
    @ResponseBody
    public Map showImage(HttpServletRequest request,HttpSession session){
        HashMap map = new HashMap();
        map.put("current_url",request.getContextPath()+"/upload/articleImages/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImages/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        map.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap mapFile = new HashMap();
            mapFile.put("is_dir",false);
            mapFile.put("has_file",false);
            mapFile.put("filesize",file1.length());
            mapFile.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            mapFile.put("filetype",extension);
            mapFile.put("filename",name);
            String time = name.split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            mapFile.put("datetime",format);
            arrayList.add(mapFile);
        }
        map.put("file_list",arrayList);
        return map;
    }
}
