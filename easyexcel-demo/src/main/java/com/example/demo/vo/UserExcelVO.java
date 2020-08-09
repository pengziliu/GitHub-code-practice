package com.example.demo.vo;

import com.example.demo.model.UserExcelModel;
import lombok.Data;

import java.util.List;

@Data
public class UserExcelVO {
    /**
     * 成功列表
     */
    private List<UserExcelModel> success;
    /**
     * 失败列表
     */
    private List<UserExcelModel> fail;

}
