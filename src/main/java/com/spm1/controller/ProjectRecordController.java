package com.spm1.controller;

import com.spm1.entity.Donor;
import com.spm1.entity.Project;
import com.spm1.entity.ProjectRecord;
import com.spm1.service.ProjectRecordService;
import com.spm1.service.ProjectService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ProjectRecord")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ProjectRecordController {
    private final ProjectRecordService projectRecordService;
    private final ProjectService projectService;
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
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("project_id", record.getProjectId()).eq("donor_id", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", projectRecordList);
    }

    @PostMapping("/queryByDonor")
    HttpResponseEntity queryRecordByDonor(@RequestBody Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("donor_id", donor.getId()).list();
        class ProjectInfo{
            Project project;
            Double money;
        }
        Map<String, ProjectInfo> projectInfoMap = new HashMap<>();
        for(ProjectRecord projectRecord : projectRecordList){
            if(projectInfoMap.containsKey(projectRecord.getProjectId()))
                projectInfoMap.get(projectRecord.getProjectId()).money += projectRecord.getMoney();
            else{
                ProjectInfo projectInfo = new ProjectInfo();
                if(projectService.query().eq("id", projectRecord.getProjectId()).list().isEmpty())
                    return HttpResponseEntity.response(false, "查询", null);
                projectInfo.project = projectService.query().eq("id", projectRecord.getProjectId()).list().get(0);
                projectInfo.money = projectRecord.getMoney();
                projectInfoMap.put(projectRecord.getProjectId(), projectInfo);
            }
        }
        return HttpResponseEntity.response(true, "查询", new ArrayList<ProjectInfo>(projectInfoMap.values()));
    }

    @PostMapping("/queryDonorRecord")
    HttpResponseEntity queryRecordDonorRecord(@RequestBody Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("donor_id", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", projectRecordList);
    }
}
