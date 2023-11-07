package com.spm1.service;

import com.spm1.entity.ChildItem;

import java.util.List;

public interface ChildItemService {

    ChildItem getChildItemById(String id);
    List<ChildItem> listChildItemsByChildId(String childId);
    boolean saveChildItem(ChildItem childItem);
    boolean updateChildItem(ChildItem childItem);
    boolean deleteChildItemById(String id);
}
