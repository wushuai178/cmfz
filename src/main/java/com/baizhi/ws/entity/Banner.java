package com.baizhi.ws.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baizhi.ws.config.ImageConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Banner implements Serializable {
  @Id
  @ExcelProperty(value = "ID")
  private String id;
  @ExcelProperty(value = "标题")
  private String title;
  @ExcelProperty(value = "图片",converter = ImageConverter.class)
  private String url;
  @ExcelProperty(value = "超链接")
  private String href;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JSONField(format = "yyyy-MM-dd")
  @ExcelProperty(value = "创建时间")
  private java.util.Date createDate;
  @ExcelProperty(value = "描述")
  private String description;
  @ExcelProperty(value = "状态")
  private String status;
}
