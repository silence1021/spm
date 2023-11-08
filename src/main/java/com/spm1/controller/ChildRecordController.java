package com.spm1.controller;

import com.spm1.entity.Donor;
import com.spm1.entity.ChildRecord;
import com.spm1.service.ChildRecordService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childRecord")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ChildRecordController {
    private final ChildRecordService childRecordService;

    @PostMapping("/add")
    HttpResponseEntity addRecord(@RequestBody ChildRecord record){
        List<ChildRecord> childRecordList = childRecordService.query()
                .eq("id", record.getId()).list();
        if (childRecordList.isEmpty()) {
            childRecordService.save(record);
            return HttpResponseEntity.response(true, "创建", null);
        } else
            return HttpResponseEntity.response(false, "创建", childRecordList);
    }

    @PostMapping("/delete")
    HttpResponseEntity deleteRecordById(@RequestBody ChildRecord record){
        boolean success = childRecordService.removeById(record);
        return HttpResponseEntity.response(success, "删除", null);
    }

    @PostMapping("/modify")
    HttpResponseEntity modifyRecord(@RequestBody ChildRecord record){
        boolean success = childRecordService.updateById(record);
        return HttpResponseEntity.response(success, "修改", null);
    }

    @PostMapping("/query")
    HttpResponseEntity queryRecord(@RequestBody ChildRecord record, @RequestBody Donor donor){
        List<ChildRecord> childRecordList = childRecordService.query().eq("childId", record.getChildId()).eq("donorId", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", childRecordList);
    }

    @PostMapping("/queryByDonor")
    HttpResponseEntity queryRecordByDonor(@RequestBody Donor donor){
        List<ChildRecord> childRecordList = childRecordService.query().eq("donorId", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", childRecordList);
    }

    @GetMapping("/all")
    HttpResponseEntity queryAll(){
        List<ChildRecord> childRecordList = childRecordService.query().list();
        return HttpResponseEntity.response(true, "查询", childRecordList);
    }
}
