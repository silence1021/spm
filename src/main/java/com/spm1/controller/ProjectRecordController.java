package com.spm1.controller;

import com.spm1.entity.Donor;
import com.spm1.entity.ProjectRecord;
import com.spm1.service.ProjectRecordService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ProjectRecord")
@Slf4j
@RequiredArgsConstructor
public class ProjectRecordController {
    private final ProjectRecordService projectRecordService;
    @PostMapping("/addRecord")
    HttpResponseEntity addRecord(@RequestBody ProjectRecord record){
        List<ProjectRecord> projectRecordList = projectRecordService.query()
                .eq("id", record.getId()).list();
        if (projectRecordList.isEmpty()) {
            projectRecordService.save(record);
            return HttpResponseEntity.response(true, "创建", null);
        } else
            return HttpResponseEntity.response(false, "创建", projectRecordList);
    }

    @PostMapping("/deleteRecord")
    HttpResponseEntity deleteRecordById(@RequestBody ProjectRecord record){
        boolean success = projectRecordService.removeById(record);
        return HttpResponseEntity.response(success, "删除", null);
    }

    @PostMapping("/modifyRecord")
    HttpResponseEntity modifyRecord(@RequestBody ProjectRecord record){
        boolean success = projectRecordService.updateById(record);
        return HttpResponseEntity.response(success, "修改", null);
    }

    @PostMapping("/queryRecord")
    HttpResponseEntity queryRecord(@RequestBody ProjectRecord record, Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("projectId", record.getProjectId()).eq("donorId", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", projectRecordList);
    }
}
