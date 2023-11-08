package com.spm1.controller;

import com.spm1.entity.ProjectItem;
import com.spm1.service.ProjectItemService;
import com.spm1.service.Impl.FileService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/projectItem")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ProjectItemController {
    private final ProjectItemService ProjectItemService;
    private final FileService fileService;
    private final String imagePath = "/ProjectItem/images/";

    // Retrieve a single ProjectItem by ID
    @GetMapping("/query")
    public HttpResponseEntity getProjectItemById(@RequestParam String id) {
        ProjectItem ProjectItem = ProjectItemService.getProjectItemById(id);
        return HttpResponseEntity.response(true, "获取项目进展记录", ProjectItem);
    }

    // Retrieve all ProjectItems associated with a given ProjectId
    @GetMapping("/query-project")
    public HttpResponseEntity getProjectItemsByProjectId(@RequestParam String ProjectId) {
        List<ProjectItem> ProjectItems = ProjectItemService.listProjectItemsByProjectId(ProjectId);
        return HttpResponseEntity.response(true, "获取项目进展记录", ProjectItems);
    }

    // Create a new ProjectItem
    @PostMapping("/add")
    public HttpResponseEntity createProjectItem(@RequestBody ProjectItem ProjectItem, @RequestParam MultipartFile image) {
        String imageUrl = fileService.imageUpload(image, ProjectItem.getId(), imagePath);
        ProjectItem.setImageUrl(imageUrl);
        boolean isSaved = ProjectItemService.saveProjectItem(ProjectItem);
        if (isSaved)
            return HttpResponseEntity.response(true, "创建项目进展记录", ProjectItem);
        else
            return HttpResponseEntity.response(false, "创建项目进展记录", null);
    }

    // Update an existing ProjectItem
    @PutMapping("/update")
    public HttpResponseEntity updateProjectItem(@RequestBody ProjectItem updatedProjectItem, @RequestParam String id, @RequestParam MultipartFile image) {
        String imageUrl = fileService.imageUpload(image, updatedProjectItem.getId(), imagePath);
        updatedProjectItem.setImageUrl(imageUrl);
        ProjectItem existingProjectItem = ProjectItemService.getProjectItemById(id);
        if (existingProjectItem == null) {
            return HttpResponseEntity.response(false, "更新项目进展记录", null);
        }
        boolean isUpdated = ProjectItemService.updateProjectItem(updatedProjectItem);
        if (isUpdated) {
            return HttpResponseEntity.response(true, "更新项目进展记录", updatedProjectItem);
        } else {
            return HttpResponseEntity.response(false, "更新项目进展记录", null);
        }
    }

    // Delete a ProjectItem
    @DeleteMapping("/delete")
    public HttpResponseEntity deleteProjectItem(@RequestBody String id) {
        boolean isDeleted = ProjectItemService.deleteProjectItemById(id);
        if (isDeleted) {
            return HttpResponseEntity.response(true, "删除项目进展记录", null);
        } else {
            return HttpResponseEntity.response(false, "删除项目进展记录", null);
        }
    }
}
