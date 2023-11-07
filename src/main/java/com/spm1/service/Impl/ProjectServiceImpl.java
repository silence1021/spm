package com.spm1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.entity.Project;
import com.spm1.mapper.ProjectMapper;
import com.spm1.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
}
