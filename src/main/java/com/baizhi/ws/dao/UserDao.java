package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Guru;
import com.baizhi.ws.entity.User;
import com.baizhi.ws.entity.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User>{
    Integer queryUserByTime(@Param("sex") String sex,@Param("day") Integer day);

    List<UserDto> queryUserByAddress(String sex);

    void fan(@Param("uid") String uid,@Param("guruId") String guruId);

    List<Guru> selectFanGuru(String uid);
}
