package com.baizhi.ws.controller;

import com.baizhi.ws.dao.*;
import com.baizhi.ws.entity.*;
import com.baizhi.ws.util.SendCodeUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("inter")
public class InterfaceController {
    @Autowired
    UserDao userDao;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    CounterDao counterDao;
    @Autowired
    GuruDao guruDao;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("userLogin")
    public Map userLogin(String phone,String password){
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        User user1 = userDao.selectOne(user);
        HashMap map = new HashMap();
        if (user1!=null){
            map.put("status",200);
            map.put("user",user1);
        }else {
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    @RequestMapping("userInformation")
    public Map userInformation(User user){
        HashMap map = new HashMap();
        try {
            userDao.updateByPrimaryKeySelective(user);
            User user1 = userDao.selectOne(user);
            map.put("status",200);
            map.put("user",user1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    @RequestMapping("OnePage")
    public Map OnePage(String type,String sub_type,String uid){
        HashMap map = new HashMap();
        if (type.equals("all")){
            try {
                List<Banner> banners = bannerDao.queryBannerByTime();
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                List<Article> articles = articleDao.queryArticleByFan(uid);
                map.put("status",200);
                map.put("head",banners);
                map.put("albums",albums);
                map.put("articles",articles);
            }catch (Exception e){
                e.printStackTrace();
                map.put("status",-200);
                map.put("message","error");
            }
        }else if (type.equals("album")){
            try {
                List<Album> albums = albumDao.selectAll();
                map.put("status",200);
                map.put("albums",albums);
            }catch (Exception e){
                map.put("status",-200);
                map.put("message","error");
            }
        }else if (type.equals("article")){
            if (sub_type.equals("ssyj")){
                List<Article> articles = articleDao.queryArticleByFan(uid);
                map.put("status",200);
                map.put("articles",articles);
            }else {
                List<Article> articles = articleDao.selectAll();
                map.put("status",200);
                map.put("articles",articles);
            }
        }
        return map;
    }

    @RequestMapping("articleDetail")
    public Map articleDetail(String id){
        Article article = articleDao.selectByPrimaryKey(id);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("article",article);
        return map;
    }

    @RequestMapping("albumDetail")
    public Map albumDetail(String id){
        Album album = albumDao.selectByPrimaryKey(id);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("album",album);
        return map;
    }

    @RequestMapping("showAllCourse")
    public Map showAllCourse(String uid,String id){
        Course course = new Course();
        course.setId(id);
        course.setUserId(uid);
        List<Course> courses = courseDao.select(course);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("courses",courses);
        return map;
    }

    @RequestMapping("addCourse")
    public Map addCourse(String uid,String title){
        Course course = new Course();
        course.setTitle(title);
        course.setUserId(uid);
        course.setId(UUID.randomUUID().toString());
        course.setCreateDate(new Date());
        courseDao.insertSelective(course);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("course",course);
        return map;
    }

    @RequestMapping("deleteCourse")
    public Map deleteCourse(String id){
        HashMap map = new HashMap();
        try {
            Course course = courseDao.selectByPrimaryKey(id);
            courseDao.deleteByPrimaryKey(id);
            map.put("status",200);
            map.put("course",course);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    @RequestMapping("showCounter")
    public Map showCounter(String uid,String cid){
        Counter counter = new Counter();
        counter.setCourseId(cid);
        counter.setUserId(uid);
        List<Counter> counters = counterDao.select(counter);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("counters",counters);
        return map;
    }

    @RequestMapping("addCounter")
    public Map addCounter(String uid,String cid,String title){
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setCourseId(cid);
        counter.setTitle(title);
        counter.setId(UUID.randomUUID().toString());
        counter.setCreateDate(new Date());
        counter.setCount(0);
        counterDao.insertSelective(counter);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("counter",counter);
        return map;
    }

    @RequestMapping("deleteCounter")
    public Map deleteCounter(String id){
        Counter counter = counterDao.selectByPrimaryKey(id);
        counterDao.deleteByPrimaryKey(id);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("counter",counter);
        return map;
    }

    @RequestMapping("updateCounter")
    public Map updateCounter(String id,int count){
        Counter counter = new Counter();
        counter.setCount(count);
        counter.setId(id);
        counterDao.updateByPrimaryKeySelective(counter);
        Counter counter1 = counterDao.selectByPrimaryKey(id);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("counter",counter1);
        return map;
    }

    @RequestMapping("updateUserInfo")
    public Map updateUserInfo(User user){
        userDao.updateByPrimaryKeySelective(user);
        User user1 = userDao.selectByPrimaryKey(user.getId());
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("user",user1);
        return map;
    }

    @RequestMapping("queryUsers")
    public Map queryUsers(String uid){
        List<User> users = userDao.selectAll();
        HashSet<User> user  = new HashSet<User>();
        while (user.size()<5){
            Random random = new Random();
            int index = random.nextInt(users.size() - 1);
            User user1 = users.get(index);
            if (!user1.getId().equals(uid)){
                user.add(users.get(index));
            }
        }
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("users",user);
        return map;
    }

    @RequestMapping("showGuru")
    public Map showGuru(){
        List<Guru> gurus = guruDao.selectAll();
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("gurus",gurus);
        return map;
    }

    @RequestMapping("addFan")
    public Map addFan(String uid,String guruId){
        userDao.fan(uid,guruId);
        List<Guru> gurus = userDao.selectFanGuru(uid);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("gurus",gurus);
        return map;
    }

    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        String code = UUID.randomUUID().toString().substring(0, 4);
        SendCodeUtil.sendCode(phone,code);
        System.out.println(code);
        stringRedisTemplate.opsForValue().set("code",code);
        stringRedisTemplate.expire("code",2, TimeUnit.MINUTES);
        HashMap map = new HashMap();
        map.put("status",200);
        map.put("message","发送成功！");
        return map;
    }

    @RequestMapping("checkCode")
    public Map checkCode(String code){
        HashMap map = new HashMap();
        String code1 = stringRedisTemplate.opsForValue().get("code");
        if (code1.equals(code)){
            map.put("status",200);
            map.put("message","验证成功");
        }else {
            map.put("status",-200);
            map.put("message","验证失败");
        }
        return map;
    }
}
