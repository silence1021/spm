package com.spm1.controller;

import com.spm1.entity.ChildItem;
import com.spm1.service.ChildItemService;
import com.spm1.service.Impl.FileService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/childItem")
@Slf4j
@RequiredArgsConstructor
public class ChildItemController {
    private final ChildItemService childItemService;
    private final FileService fileService;
    private final String imagePath = "/childItem/images/";

    // Retrieve a single ChildItem by ID
    @GetMapping("/query")
    public HttpResponseEntity getChildItemById(@RequestParam String id) {
        ChildItem childItem = childItemService.getChildItemById(id);
        return HttpResponseEntity.response(true, "获取儿童成长记录", childItem);
    }

    // Retrieve all ChildItems associated with a given childId
    @GetMapping("/query-child")
    public HttpResponseEntity getChildItemsByChildId(@RequestParam String childId) {
        List<ChildItem> childItems = childItemService.listChildItemsByChildId(childId);
        return HttpResponseEntity.response(true, "获取儿童成长记录", childItems);
    }

    // Create a new ChildItem
    @PostMapping("/add")
    public HttpResponseEntity createChildItem(@RequestBody ChildItem childItem, @RequestParam MultipartFile image) {
        String imageUrl = fileService.imageUpload(image, childItem.getId(), imagePath);
        childItem.setImageUrl(imageUrl);
        boolean isSaved = childItemService.saveChildItem(childItem);
        if (isSaved)
            return HttpResponseEntity.response(true, "创建儿童成长记录", childItem);
        else
            return HttpResponseEntity.response(false, "创建儿童成长记录", null);
    }

    // Update an existing ChildItem
    @PutMapping("/update")
    public HttpResponseEntity updateChildItem(@RequestBody ChildItem updatedChildItem, @RequestParam String id, @RequestParam MultipartFile image) {
        String imageUrl = fileService.imageUpload(image, updatedChildItem.getId(), imagePath);
        updatedChildItem.setImageUrl(imageUrl);
        ChildItem existingChildItem = childItemService.getChildItemById(id);
        if (existingChildItem == null) {
            return HttpResponseEntity.response(false, "更新儿童成长记录", null);
        }
        boolean isUpdated = childItemService.updateChildItem(updatedChildItem);
        if (isUpdated) {
            return HttpResponseEntity.response(true, "更新儿童成长记录", updatedChildItem);
        } else {
            return HttpResponseEntity.response(false, "更新儿童成长记录", null);
        }
    }

    // Delete a ChildItem
    @DeleteMapping("/delete")
    public HttpResponseEntity deleteChildItem(@RequestBody String id) {
        boolean isDeleted = childItemService.deleteChildItemById(id);
        if (isDeleted) {
            return HttpResponseEntity.response(true, "删除儿童成长记录", null);
        } else {
            return HttpResponseEntity.response(false, "删除儿童成长记录", null);
        }
    }
}
