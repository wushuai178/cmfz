package com.baizhi.ws.controller;

import com.baizhi.ws.entity.Chapter;
import com.baizhi.ws.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("queryChapterByPage")
    @ResponseBody
    public Map queryChapterByPage(String albumId,int rows,int page){

        Map map = chapterService.queryChapterByPage(albumId, page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(Chapter chapter,String oper,String albumId,String[] id){
        HashMap map = new HashMap();
        if (oper.equals("add")){
            chapter.setAlbumId(albumId);
            chapterService.insert(chapter);
            map.put("chapterId",chapter.getId());
        }else if (oper.equals("del")){
            chapterService.delete(Arrays.asList(id));
        }else {
            if (chapter.getUrl().equals("")){
                chapter.setUrl(null);
            }
            chapterService.update(chapter);
            map.put("chapterId",chapter.getId());
        }
        return map;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map upload(MultipartFile url, String chapterId, HttpServletRequest request, HttpSession session){
        if (!"".equals(url.getOriginalFilename())){
            //获取真实路径
            String realPath = session.getServletContext().getRealPath("/sound/");
            //创建上传文件夹
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            //防止重名
            String originalFilename =new Date().getTime()+"_"+url.getOriginalFilename();
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
            String urlName = scheme+"://"+ip.split("/")[1]+":"+port+contextPath+"/sound/"+originalFilename;
            try {
                url.transferTo(new File(realPath,originalFilename));
                Double size = Double.valueOf((double)url.getSize()/1024/1024);
                Chapter chapter = new Chapter();
                chapter.setUrl(urlName);
                chapter.setSize(size);
                chapter.setId(chapterId);
                //计算音频时长
                AudioFile read = AudioFileIO.read(new File(realPath, originalFilename));
                //音频解析对象
                MP3AudioHeader audioHeader = (MP3AudioHeader)read.getAudioHeader();
                int trackLength = audioHeader.getTrackLength();
                String time = trackLength/60+"分"+trackLength%60+"秒";
                chapter.setTime(time);
                chapterService.update(chapter);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotReadException e) {
                e.printStackTrace();
            } catch (ReadOnlyFileException e) {
                e.printStackTrace();
            } catch (TagException e) {
                e.printStackTrace();
            } catch (InvalidAudioFrameException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("download")
    public void download(String url, HttpSession session, HttpServletResponse response) throws IOException {
        //获取文件名
        String[] split = url.split("/");
        String name = split[split.length-1];
        String realPath = session.getServletContext().getRealPath("/sound");
        File file = new File(realPath,name);
        response.setHeader("Content-Disposition","attachment;filename="+name);
        FileUtils.copyFile(file,response.getOutputStream());
    }
}
