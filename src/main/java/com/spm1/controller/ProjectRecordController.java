package com.spm1.controller;

import com.spm1.entity.*;
import com.spm1.service.ProjectRecordService;
import com.spm1.service.ProjectService;
import com.spm1.tools.HttpResponseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/ProjectRecord")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ProjectRecordController {
    private final ProjectRecordService projectRecordService;
    private final ProjectService projectService;
    @PostMapping("/addRecord")
    @ResponseBody
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
    @ResponseBody
    HttpResponseEntity deleteRecordById(@RequestBody ProjectRecord record){
        boolean success = projectRecordService.removeById(record);
        return HttpResponseEntity.response(success, "删除", null);
    }

    @PostMapping("/modifyRecord")
    @ResponseBody
    HttpResponseEntity modifyRecord(@RequestBody ProjectRecord record){
        boolean success = projectRecordService.updateById(record);
        return HttpResponseEntity.response(success, "修改", null);
    }

    @PostMapping("/queryRecord")
    @ResponseBody
    HttpResponseEntity queryRecord(@RequestBody ProjectRecord record, Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("project_id", record.getProjectId()).eq("donor_id", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", projectRecordList);
    }

    @PostMapping("/queryByDonor_withMoney")
    @ResponseBody
    HttpResponseEntity queryRecordByDonor(@RequestBody Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("donor_id", donor.getId()).list();
        log.info("projectRecordList: " + projectRecordList.size());
        @Data
        @Setter
        @Getter
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
                if(projectService.query().eq("id", projectRecord.getProjectId()) != null)
                    projectInfo.project = projectService.query().eq("id", projectRecord.getProjectId()).list().get(0);
                else
                    return HttpResponseEntity.response(false, "查询", null);
                projectInfo.money = projectRecord.getMoney();
                projectInfoMap.put(projectRecord.getProjectId(), projectInfo);
            }
        }
        return HttpResponseEntity.response(true, "查询", new ArrayList<ProjectInfo>(projectInfoMap.values()));
    }

    @PostMapping("/queryByDonor")
    @ResponseBody
    HttpResponseEntity queryRecordWithoutMoney(@RequestBody Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("donor_id", donor.getId()).list();
        Set<Project> projectSet = new HashSet<>();
        for(ProjectRecord projectRecord : projectRecordList)
            if(projectService.query().eq("id", projectRecord.getProjectId()) != null)
                projectSet.add(projectService.query().eq("id", projectRecord.getProjectId()).list().get(0));
        return HttpResponseEntity.response(true, "查询", new ArrayList<Project>(projectSet));
    }

    @PostMapping("/queryDonorRecord")
    @ResponseBody
    HttpResponseEntity queryRecordDonorRecord(@RequestBody Donor donor){
        List<ProjectRecord> projectRecordList = projectRecordService.query().eq("donor_id", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", projectRecordList);
    }
}
