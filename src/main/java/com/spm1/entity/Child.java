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
    @TableField("expected_money")
    private Double expectedMoney;
    @TableField("actual_money")
    private Double actualMoney;
    @TableField("image_url")
    private String imageUrl;
    private String description;
    private String content;
    @TableField("count_people")
    private Integer countPeople;

}
