package com.baizhi.ws.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  private String id;
  private String phone;
  private String password;
  private String salt;
  private String status;
  private String photo;
  private String name;
  private String nickName;
  private String sex;
  private String sign;
  private String location;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JSONField(format = "yyyy-MM-dd")
  private Date registDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JSONField(format = "yyyy-MM-dd")
  private Date lastLogin;

}
