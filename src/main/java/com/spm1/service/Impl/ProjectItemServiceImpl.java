package com.spm1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.service.ProjectItemService;
import com.spm1.mapper.ProjectItemMapper;
import com.spm1.entity.ProjectItem;
import com.spm1.tools.HttpResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ProjectItemServiceImpl extends ServiceImpl<ProjectItemMapper, ProjectItem> implements ProjectItemService {
    private final ProjectItemMapper projectItemMapper;

    @Autowired
    public ProjectItemServiceImpl(ProjectItemMapper projectItemMapper) {
        this.projectItemMapper = projectItemMapper;
    }

    @Override
    public ProjectItem getProjectItemById(String id) {
        return projectItemMapper.selectById(id);
    }

    @Override
    public List<ProjectItem> listProjectItemsByProjectId(String projectId) {
        // You need to implement the method to retrieve items based on projectId
        // This might involve creating a custom query in the mapper
        return projectItemMapper.selectByProjectId(projectId);
    }

    @Override
    @Transactional
    public boolean saveProjectItem(ProjectItem projectItem) {
        return projectItemMapper.insert(projectItem) > 0;
    }

    @Override
    @Transactional
    public boolean updateProjectItem(ProjectItem projectItem) {
        return projectItemMapper.updateById(projectItem) > 0;
    }

    @Override
    @Transactional
    public boolean deleteProjectItemById(String id) {
        return projectItemMapper.deleteById(id) > 0;
    }

}
