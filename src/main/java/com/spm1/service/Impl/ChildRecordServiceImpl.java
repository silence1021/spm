package com.spm1.service.Impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.entity.ChildRecord;
import com.spm1.service.ChildRecordService;
import com.spm1.mapper.ChildRecordMapper;
import org.springframework.stereotype.Service;

@Service
public class ChildRecordServiceImpl extends ServiceImpl<ChildRecordMapper, ChildRecord> implements ChildRecordService {
}
