package com.spm1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("projects")
public class Project {
    @TableId
    private String id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private Double expectedMoney;
    private Double actualMoney;
    private Integer countPeople;
    private String description;
    private String content;
    private String imageUrl;
}
