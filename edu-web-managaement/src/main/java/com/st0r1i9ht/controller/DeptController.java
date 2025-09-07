package com.st0r1i9ht.controller;

import com.st0r1i9ht.pojo.Dept;
import com.st0r1i9ht.pojo.Result;
import com.st0r1i9ht.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping注解位置
//1.类上(可选) 2.方法上
//一个完整的请求路径=类上+方法上
@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门列表
     * 接口文档要求返回值为{"code":1, "msg":"success", "data":null}这种格式 所以返回封装对象Result
     * 类注解@RestController中包含的@ResponseBody会将返回的对象和字符串转换为json
     *
     */
    //@RequsetMapping不会指定请求方式, 任何请求都可以访问
    //method 指定请求方式 使用RequestMapping指定请求方式过于繁琐 改用@GetMapping指定请求方式为GET
    //@RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    //@PostMapping @DeleteMapping @PutMapping
    public Result list() {
        System.out.println("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * Controller层负责获取请求 调用Service接口 返回数据
     * Service层负责处理业务逻辑 调用Mapper接口
     * Mapper层负责数据库操作
     */

    //Controller接收请求参数:DELETE /depts?id=8
    /*
     * 方式一:通过原始的HttpServletRequest对象获取请求参数
     * 代码繁琐 需要手动进行类型转换
     * 不推荐
     */
//    @DeleteMapping("/depts") //接口文档明确请求方式为DELETE, 所以使用@DeleteMapping 接口文档明确请求路径/depts
//    public Result delete(HttpServletRequest request) {
//        String idStr = request.getParameter("id");//使用HttpServletRequest获取HTTP请求协议参数 通过这种方式获取的参数都是String类型
//        int id = Integer.parseInt(idStr);//将String类型的id转换成int类型
//        System.out.println("根据ID删除部门:" + id);//调用Service,暂时不调用,用输出代替
//        return Result.success();//删除不需要向前端返回数据,调用无参构造器
//    }

    /**
     * 方式二:通过Spring提供的@RequestParam注解, 将请求参数绑定给方法形参
     * 一旦声明了@RequestParam, 该参数在请求时必须传递, 如果不传递将会报错
     * 注解中有以下代码 默认为true
     *     boolean required() default true;
     * 如果参数可选传递, 通过@RequestParam(value = "id", required = false)将required设置为false
     * 不传递参数将不会报错 而是会返回null
     * 这种方式仍有一种简化方式
     */
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId) { //@RequestParam会获取请求参数id并绑定给方法形参deptId
//        System.out.println("根据ID删除部门:" + deptId);
//        return Result.success();
//    }

    /**
     * 方式三:如果请求参数名与形参变量名相同,直接定义方法形参即可接收(省略@RequestParam)
     * 不一致会返回null
     * 推荐 仅需保证前端请求参数名和服务端形参变量名相同即可
     */
    @DeleteMapping
    public Result delete(Integer id) {
        System.out.println("根据ID删除部门:" + id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增部门
     * 接收JSON格式参数, 通常会使用一个实体对象进行接收
     * 使用@RequestBody注解方法形参, 保持JSON数据键名和方法形参对象属性名相同
     * 该注解会将JSON数据转换并封装为该对象的属性
     */
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        System.out.println("新增部门:" + dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 编辑部门: 查询回显->修改部门
     * 接收请求参数(路径参数) GET /depts/{...}
     * 通过URL直接传递参数, 使用{...} 来标识路径参数, 使用@PathVariable(...)获取路径参数绑定给方法形参
     */
//    @GetMapping("/depts/{id}")
//    public Result getInfo(@PathVariable("id") Integer deptID) {
//        System.out.println("根据ID查询部门:" + deptID);
//        return Result.success();
//    }

    /**
     * 如果形参名称与路径参数名一致可以省略@PathVariable的value属性值可以省略
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        System.out.println("根据ID查询部门:" + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        System.out.println("修改部门:" + dept);
        deptService.update(dept);
        return Result.success();
    }

}