package com.spm1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spm1.entity.Child;
import com.spm1.entity.ChildItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChildMapper extends BaseMapper<Child> {


}
