package com.baizhi.ws.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Counter {
  @Id
  private String id;
  private String title;
  private Integer count;
  private Date createDate;
  private String userId;
  private String courseId;
}
