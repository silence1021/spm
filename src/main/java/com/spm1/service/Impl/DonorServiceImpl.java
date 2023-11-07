package com.spm1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.service.DonorService;
import com.spm1.mapper.DonorMapper;
import com.spm1.entity.Donor;
import org.springframework.stereotype.Service;

@Service
public class DonorServiceImpl extends ServiceImpl<DonorMapper, Donor> implements DonorService {
}
