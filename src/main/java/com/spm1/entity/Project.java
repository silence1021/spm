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
    @TableField("start_date")
    private LocalDateTime startDate;
    @TableField("stop_date")
    private LocalDateTime stopDate;
    @TableField("expected_money")
    private Double expectedMoney;
    @TableField("actual_money")
    private Double actualMoney;
    @TableField("count_people")
    private Integer countPeople;
    private String description;
    private String content;
    @TableField("image_url")
    private String imageUrl;
}
