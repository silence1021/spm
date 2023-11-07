package com.spm1.controller;

import com.spm1.entity.*;
import com.spm1.service.*;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/donation")
@Slf4j
@RequiredArgsConstructor
public class DonationController {
    private final DonorService donorService;
    private final ChildRecordService childRecordService;
    private final ProjectRecordService projectRecordService;
    private final ChildService childService;
    private final ProjectService projectService;
    @PostMapping("/project/donation")
    public HttpResponseEntity ProjectDonation(@RequestBody Donor donor, @RequestBody Project project, @RequestBody Double money) {
        if(donorService.query().eq("id", donor.getId()).count() == 0)
            return HttpResponseEntity.response(false, "捐款记录创建", "用户不存在");
        if(projectService.query().eq("id", project.getId()).count() == 0)
            return HttpResponseEntity.response(false, "捐款记录创建", "项目不存在");
        if(money == null || money < 0)
            return HttpResponseEntity.response(false, "捐款记录创建", "捐款数需大于等于0");
        if(project.getStopDate().isAfter(LocalDateTime.now()))
            return HttpResponseEntity.response(false, "捐款记录创建", "项目已截止");

        ProjectRecord projectRecord = new ProjectRecord();
        projectRecord.setProjectId(project.getId());
        projectRecord.setDonateDate(LocalDateTime.now());
        projectRecord.setId(UUID.randomUUID().toString());
        projectRecord.setDonorId(donor.getId());
        projectRecord.setMoney(money);
        project.setCountPeople(project.getCountPeople()+1);
        project.setActualMoney(project.getActualMoney()+money);
        boolean recordSuccess = projectRecordService.save(projectRecord);
        boolean projectSuccess = projectService.updateById(project);
        return HttpResponseEntity.response((recordSuccess && projectSuccess), "捐款记录创建", projectRecord);
    }

    @PostMapping("/child/donation")
    public HttpResponseEntity ChildDonation(@RequestBody Donor donor, @RequestBody Child child, @RequestBody Double money) {
        if(donorService.query().eq("id", donor.getId()).count() == 0)
            return HttpResponseEntity.response(false, "捐款记录创建", "用户不存在");
        if(childService.query().eq("id", child.getId()).count() == 0)
            return HttpResponseEntity.response(false, "捐款记录创建", "儿童对象不存在");
        if(money == null || money < 0)
            return HttpResponseEntity.response(false, "捐款记录创建", "捐款数需大于等于0");

        ChildRecord childRecord = new ChildRecord();
        childRecord.setChildId(child.getId());
        childRecord.setDonateDate(LocalDateTime.now());
        childRecord.setId(UUID.randomUUID().toString());
        childRecord.setDonorId(donor.getId());
        childRecord.setMoney(money);
        child.setCountPeople(child.getCountPeople()+1);
        child.setActualMoney(child.getActualMoney()+money);
        boolean recordSuccess = childRecordService.save(childRecord);
        boolean projectSuccess = childService.updateById(child);
        return HttpResponseEntity.response((recordSuccess && projectSuccess), "捐款记录创建", childRecord);
    }
}
