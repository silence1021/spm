package com.spm1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.entity.Child;
import com.spm1.entity.ChildItem;
import com.spm1.mapper.ChildItemMapper;
import com.spm1.mapper.ChildMapper;
import com.spm1.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChildServiceImpl extends ServiceImpl<ChildMapper, Child> implements ChildService {
    private final ChildMapper childMapper;

    @Autowired
    public ChildServiceImpl(ChildMapper childMapper) {
        this.childMapper = childMapper;
    }

    @Override
    public Child getChildById(String id) {
        return childMapper.selectById(id);
    }

    @Override
    public List<Child> getAllChildren() {
        return childMapper.selectList(null); // passing null to selectList returns all records
    }

    @Override
    public boolean saveChild(Child child) {
        return childMapper.insert(child) > 0;
    }

    @Override
    public boolean updateChild(Child child) {
        return childMapper.updateById(child) > 0;
    }

    @Override
    public boolean deleteChildById(String id) {
        return childMapper.deleteById(id) > 0;
    }
}
