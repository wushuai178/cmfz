package com.baizhi.ws.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  @Id
  private String id;
  private String title;
  private String userId;
  private String type;
  private Date createDate;

}
