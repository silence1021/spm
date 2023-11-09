package com.spm1.controller;

import com.spm1.entity.Child;
import com.spm1.entity.Donor;
import com.spm1.entity.ChildRecord;
import com.spm1.service.ChildRecordService;
import com.spm1.service.ChildService;
import com.spm1.tools.HttpResponseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/childRecord")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ChildRecordController {
    private final ChildRecordService childRecordService;
    private final ChildService childService;

    @PostMapping("/add")
    @ResponseBody
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
    @ResponseBody
    HttpResponseEntity deleteRecordById(@RequestBody ChildRecord record){
        boolean success = childRecordService.removeById(record);
        return HttpResponseEntity.response(success, "删除", null);
    }

    @PostMapping("/modify")
    @ResponseBody
    HttpResponseEntity modifyRecord(@RequestBody ChildRecord record){
        boolean success = childRecordService.updateById(record);
        return HttpResponseEntity.response(success, "修改", null);
    }

    @PostMapping("/query")
    @ResponseBody
    HttpResponseEntity queryRecord(@RequestBody ChildRecord record, @RequestBody Donor donor){
        List<ChildRecord> childRecordList = childRecordService.query().eq("child_id", record.getChildId()).eq("donor_id", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", childRecordList);
    }

    @PostMapping("/queryByDonor_withMoney")
    @ResponseBody
    HttpResponseEntity queryRecordByDonor(@RequestBody Donor donor){
        List<ChildRecord> childRecordList = childRecordService.query().eq("donor_id", donor.getId()).list();
        log.info("childRecordList: " + childRecordList.size());
        @Data
        @Setter
        @Getter
        class ChildInfo{
            Child child;
            Double money;
        }
        Map<String, ChildInfo> childInfoMap = new HashMap<>();
        for(ChildRecord childRecord : childRecordList){
           if(childInfoMap.containsKey(childRecord.getChildId()))
               childInfoMap.get(childRecord.getChildId()).money += childRecord.getMoney();
           else{
               ChildInfo childInfo = new ChildInfo();
               childInfo.child = childService.getChildById(childRecord.getChildId());
               childInfo.money = childRecord.getMoney();
               childInfoMap.put(childRecord.getChildId(), childInfo);
           }
        }
        return HttpResponseEntity.response(true, "查询", new ArrayList<ChildInfo>(childInfoMap.values()));
    }

    @PostMapping("/queryByDonor")
    @ResponseBody
    HttpResponseEntity queryRecordWithoutMoney(@RequestBody Donor donor){
        List<ChildRecord> childRecordList = childRecordService.query().eq("donor_id", donor.getId()).list();
        Set<Child> childSet = new HashSet<>();
        for(ChildRecord childRecord : childRecordList)
            childSet.add(childService.getChildById(childRecord.getChildId()));
        return HttpResponseEntity.response(true, "查询", new ArrayList<Child>(childSet));
    }

    @PostMapping("/queryDonorRecord")
    @ResponseBody
    HttpResponseEntity queryRecordDonorRecord(@RequestBody Donor donor){
        List<ChildRecord> childRecordList = childRecordService.query().eq("donor_id", donor.getId()).list();
        return HttpResponseEntity.response(true, "查询", childRecordList);
    }

    @GetMapping("/all")
    @ResponseBody
    HttpResponseEntity queryAll(){
        List<ChildRecord> childRecordList = childRecordService.query().list();
        return HttpResponseEntity.response(true, "查询", childRecordList);
    }
}
