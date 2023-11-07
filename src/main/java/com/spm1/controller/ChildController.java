package com.spm1.controller;

import com.spm1.entity.Child;
import com.spm1.tools.HttpResponseEntity;
import com.spm1.service.ChildService;
import com.spm1.service.Impl.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/child")
@Slf4j
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;
    private final FileService fileService;
    private final String imagePath = "/child/images/";

    // Get a child by ID
    @GetMapping("/search")
    public HttpResponseEntity getChildById( @RequestBody String id) {
        Child child = childService.getChildById(id);
        if (child != null)
            return HttpResponseEntity.response(true, "获取儿童", child);
        else
            return HttpResponseEntity.response(false, "获取儿童", null);
    }

    // Get all children
    @GetMapping("/all")
    public HttpResponseEntity getAllChildren() {
        List<Child> children = childService.getAllChildren();
        return HttpResponseEntity.response(true, "获取所有儿童", children);
    }

    // Create a new child
    @PostMapping("add")
    public HttpResponseEntity createChild(@RequestBody Child child, @RequestParam MultipartFile image) {
        String imageUrl = fileService.imageUpload(image, child.getId(), imagePath);
        child.setImageUrl(imageUrl);
        String uuid = UUID.randomUUID().toString();
        child.setId(uuid);
        boolean isChildCreated = childService.saveChild(child);
        if (isChildCreated)
            return HttpResponseEntity.response(true, "创建儿童", child);
        else
            return HttpResponseEntity.response(false, "创建儿童", null);
    }

    // Update an existing child
    @PutMapping("/update")
    public HttpResponseEntity updateChild(@RequestBody Child child) {
        boolean isChildUpdated = childService.updateChild(child);
        if (isChildUpdated)
            return HttpResponseEntity.response(true, "更新儿童", child);
        else
            return HttpResponseEntity.response(false, "更新儿童", null);
    }

    // Delete a child
    @DeleteMapping("/delete")
    public HttpResponseEntity deleteChildById(@RequestBody String id) {
        boolean isChildDeleted = childService.deleteChildById(id);
        if (isChildDeleted)
            return HttpResponseEntity.response(true, "删除儿童", null);
        else
            return HttpResponseEntity.response(false, "删除儿童", null);
    }
}
