package com.baizhi.ws.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String broadcast;
  private Long count;
  private String description;
  private String status;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.util.Date createDate;
  private String img;

}
