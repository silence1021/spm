package com.spm1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spm1.entity.Child;

import java.util.List;

public interface ChildService extends IService<Child> {
    Child getChildById(String id);
    List<Child> getAllChildren();
    boolean saveChild(Child child);
    boolean updateChild(Child child);
    boolean deleteChildById(String id);

}
