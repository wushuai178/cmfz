package com.baizhi.ws.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.ws.entity.Banner;
import com.baizhi.ws.entity.BannerDto;
import com.baizhi.ws.entity.BannerListener;
import com.baizhi.ws.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/queryBannerByPage")
    @ResponseBody
    public BannerDto queryBannerByPage(int rows,int page){
        return bannerService.queryByPage(page,rows);
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request){
        if (!(url.getOriginalFilename()==null||url.getOriginalFilename().equals(""))){
            //真实路径
            String realPath = session.getServletContext().getRealPath("/images");
            //判断该文件夹是否存在
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            //防止重名
            String name = new Date().getTime()+"_"+url.getOriginalFilename();
            //获取协议名
            String scheme = request.getScheme();
            //ip
            String ip = "";
            try {
                ip = InetAddress.getLocalHost().toString();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            //获取端口号
            int port = request.getServerPort();
            //项目名
            String contextPath = request.getContextPath();

            String urlName = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/images/"+name;

            try {
                //文件上传
                url.transferTo(new File(realPath,name));
                //数据库url更新
                Banner banner = new Banner();
                banner.setUrl(urlName);
                banner.setId(bannerId);
                bannerService.updateUrl(banner);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper,Banner banner,String[] id){
        if (oper.equals("add")){
            bannerService.insert(banner);
            HashMap hashMap = new HashMap();
            hashMap.put("bannerId", banner.getId());
            return hashMap;
        }else if (oper.equals("edit")){
            if (banner.getUrl().equals("")){
                banner.setUrl(null);
            }
            bannerService.update(banner);
            HashMap hashMap = new HashMap();
            hashMap.put("bannerId", banner.getId());
            return hashMap;
        }else {
            bannerService.deleteMany(Arrays.asList(id));
        }
        return null;
    }

    @RequestMapping("outputController")
    public void outputController(HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerService.queryAll();
        String name = new Date().getTime()+".xls";
        response.setHeader("Content-Disposition","attachment;filename="+name);
        EasyExcel.write(response.getOutputStream(),Banner.class).sheet().doWrite(banners);
    }

    @RequestMapping("importController")
    public void importController(MultipartFile file,HttpSession session) throws IOException {
        String realPath = session.getServletContext().getRealPath("/upload/excle/");
        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdirs();
        }
        String name =  new Date().getTime()+"_"+file.getOriginalFilename();
        file.transferTo(new File(realPath,name));
        String url = realPath+name;
        System.out.println(url);
        EasyExcel.read(url,Banner.class,new BannerListener()).sheet().doRead();
    }
}
