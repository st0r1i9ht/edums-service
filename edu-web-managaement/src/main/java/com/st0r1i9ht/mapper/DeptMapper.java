package com.st0r1i9ht.mapper;

import com.st0r1i9ht.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有数据
     *
     * @return
     */
    //属性名和列名不一致时无法正确封装
    //解决方式一:
//    @Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })
    //方式二: 起别名
//    @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc")
    //方式三: 如果字段名与属性名符合驼峰命名规则, mybatis会自动通过驼峰命名规则映射 create_time与createTime对应 在springboot配置文件中配置
    //    #开启驼峰命名映射开关
    //    map-underscore-to-camel-case: true
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);

    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getByID(Integer id);

    @Update("update dept set name=#{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}