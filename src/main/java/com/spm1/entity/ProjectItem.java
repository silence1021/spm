package com.spm1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("projectItems")
public class ProjectItem {
    @TableId
    private String id;
    private String title;
    @TableField("project_id")
    private String projectId;
    private String content;
    @TableField("image_url")
    private String imageUrl;
    private LocalDateTime date;
}
