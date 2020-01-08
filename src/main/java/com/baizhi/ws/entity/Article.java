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
public class Article {
  @Id
  private String id;
  private String title;
  private String img;
  private String content;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date publishDate;
  private String status;
  private String guruId;

}
