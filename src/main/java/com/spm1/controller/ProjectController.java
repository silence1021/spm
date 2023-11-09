package com.spm1.controller;

import com.spm1.entity.Project;
import com.spm1.entity.ProjectRecord;
import com.spm1.entity.Donor;
import com.spm1.entity.Project;
import com.spm1.service.ProjectService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/project")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/add")
    public HttpResponseEntity createProject(@RequestBody Project project) {
        String uuid = UUID.randomUUID().toString();
        project.setId(uuid);
        projectService.save(project);
        return HttpResponseEntity.response(true, "项目创建", project);
    }

    @GetMapping("/search")
    public HttpResponseEntity getProjectById(@RequestBody String id) {
        return HttpResponseEntity.response(true, "获取项目对象", projectService.getById(id));
    }

    @GetMapping("/all")
    public HttpResponseEntity getAllProjects() {
        return HttpResponseEntity.response(true, "获取所有项目", projectService.list());
    }

    @PutMapping("/update")
    public HttpResponseEntity updateProject(@RequestBody Project project) {
        projectService.updateById(project);
        return HttpResponseEntity.response(true, "更新项目", project);
    }

    @DeleteMapping("/delete")
    public HttpResponseEntity deleteProject(@RequestBody String id) {
        projectService.removeById(id);
        return HttpResponseEntity.response(true, "删除项目", null);
    }
}
