package com.spm1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spm1.entity.ProjectItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectItemMapper extends BaseMapper<ProjectItem> {
    List<ProjectItem> selectByProjectId(String id);
}
