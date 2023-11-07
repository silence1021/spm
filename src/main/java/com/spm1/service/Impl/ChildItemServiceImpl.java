package com.spm1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spm1.entity.ChildItem;
import com.spm1.entity.ProjectItem;
import com.spm1.mapper.ChildItemMapper;
import com.spm1.mapper.ProjectItemMapper;
import com.spm1.service.ChildItemService;
import com.spm1.service.ProjectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChildItemServiceImpl extends ServiceImpl<ChildItemMapper, ChildItem> implements ChildItemService {


    private final ChildItemMapper childItemMapper;

    @Autowired
    public ChildItemServiceImpl(ChildItemMapper childItemMapper) {
        this.childItemMapper = childItemMapper;
    }

    @Override
    public ChildItem getChildItemById(String id) {
        return childItemMapper.selectById(id);
    }

    @Override
    public List<ChildItem> listChildItemsByChildId(String childId) {
        // You need to implement the method to retrieve items based on childId
        // This might involve creating a custom query in the mapper
        return childItemMapper.selectByChildId(childId);
    }

    @Override
    @Transactional
    public boolean saveChildItem(ChildItem childItem) {
        return childItemMapper.insert(childItem) > 0;
    }

    @Override
    @Transactional
    public boolean updateChildItem(ChildItem childItem) {
        return childItemMapper.updateById(childItem) > 0;
    }

    @Override
    @Transactional
    public boolean deleteChildItemById(String id) {
        return childItemMapper.deleteById(id) > 0;
    }
}
