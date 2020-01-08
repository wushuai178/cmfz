package com.baizhi.ws.service;

import com.baizhi.ws.entity.User;
import com.baizhi.ws.entity.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map queryUserByPage(int page,int rows);

    Map insertUser(User user);

    Map updateUser(User user);

    Integer queryUserByTime(String sex,Integer day);

    List<UserDto> queryUserByAddress(String sex);
}
