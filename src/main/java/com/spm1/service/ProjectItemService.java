package com.spm1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spm1.entity.ProjectItem;
import com.spm1.tools.HttpResponseEntity;

import java.util.List;


public interface ProjectItemService extends IService<ProjectItem> {
    ProjectItem getProjectItemById(String id);
    List<ProjectItem> listProjectItemsByProjectId(String projectId);
    boolean saveProjectItem(ProjectItem projectItem);
    boolean updateProjectItem(ProjectItem projectItem);
    boolean deleteProjectItemById(String id);
}
