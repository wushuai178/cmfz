package com.baizhi.ws;

import com.baizhi.ws.dao.UserDao;
import com.baizhi.ws.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmfzApplicationTests {

    @Autowired
    UserDao userDao;
    @Test
    void contextLoads() {
    }
    @Test
    public void Test1(){
        User user = new User();
        user.setPhone("12311112222");
        user.setPassword("123456");
        System.out.println(user);
        User user1 = userDao.selectOne(user);
        System.out.println(user1);
    }

}
