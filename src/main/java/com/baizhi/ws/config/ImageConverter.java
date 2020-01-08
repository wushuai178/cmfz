package com.baizhi.ws.config;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String property = System.getProperty("user.dir");
        value = value.split("/")[value.split("/").length - 1];
        String url = property+"\\src\\main\\webapp\\images\\"+value;
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }
}
