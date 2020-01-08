package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.UserDao;
import com.baizhi.ws.entity.User;
import com.baizhi.ws.entity.UserDto;
import com.baizhi.ws.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryUserByPage(int page, int rows) {
        HashMap map = new HashMap();
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("rows",users);
        int count = userDao.selectCount(null);
        map.put("records",count);
        map.put("page",page);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        return map;
    }

    @Override
    public Map insertUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setRegistDate(new Date());
        userDao.insertSelective(user);
        HashMap map = new HashMap();
        map.put("userId",user.getId());
        return map;
    }

    @Override
    public Map updateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
        HashMap map = new HashMap();
        map.put("userId",user.getId());
        return map;
    }

    @Override
    public Integer queryUserByTime(String sex, Integer day) {
        return userDao.queryUserByTime(sex,day);
    }

    @Override
    public List<UserDto> queryUserByAddress(String sex) {
        return userDao.queryUserByAddress(sex);
    }
}
