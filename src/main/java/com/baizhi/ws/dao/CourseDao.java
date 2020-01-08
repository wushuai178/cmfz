package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Course;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CourseDao extends Mapper<Course> {
}
