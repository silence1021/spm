package com.spm1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("children")
public class Child {
    @TableId
    private String id;
    private String name;
    private Integer age;
    private String title;
    private Double expectedMoney;
    private Double actualMoney;
    private String imageUrl;
    private String description;
    private String content;
    private Integer count_people;

}
