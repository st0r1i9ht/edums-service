package com.st0r1i9ht.service.impl;


import com.st0r1i9ht.mapper.DeptMapper;
import com.st0r1i9ht.pojo.Dept;
import com.st0r1i9ht.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        //1.补全基础属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用Mapper接口方法插入数据
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getByID(id);
    }

    @Override
    public void update(Dept dept) {
        //1. 补全基础属性
        dept.setUpdateTime(LocalDateTime.now());

        //2. 调用Mapper接口方法修改部门数据
        deptMapper.update(dept);
    }
}
