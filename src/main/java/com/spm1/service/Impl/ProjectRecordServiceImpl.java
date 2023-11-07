package com.spm1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.entity.ProjectRecord;
import com.spm1.mapper.ProjectRecordMapper;
import com.spm1.service.ProjectRecordService;
import org.springframework.stereotype.Service;

@Service
public class ProjectRecordServiceImpl extends ServiceImpl<ProjectRecordMapper, ProjectRecord> implements ProjectRecordService {
}
