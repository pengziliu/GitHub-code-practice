package com.example.demo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserExcelModel  extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "用户名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "手机号", index = 2)
    private String mobile;

    @ExcelProperty(value = "性别", index = 3)
    private String sex;


    public UserExcelModel(String name, Integer age, String mobile, String sex) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.sex = sex;
    }
    public UserExcelModel(){}
}
