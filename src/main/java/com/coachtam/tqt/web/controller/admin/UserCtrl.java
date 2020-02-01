package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.UserEventLog;
import com.coachtam.tqt.event.UserEvent;
import com.coachtam.tqt.interceptor.AuthInterceptor;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.qo.UserQO;
import com.coachtam.tqt.utils.jwt.UserInfo;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.admin.RoleVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.*;

/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 12:23:59
 */
@RequestMapping("/api/user")
@RestController
@Api(value = "用户服务")

public class UserCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @ApiOperation(value = "分页查询")
    @PostMapping("/search")
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, @RequestBody UserQO userQo)
    {
        Page result = userService.page(pageNo,pageSize, userQo);
        return ResultVM.success(result);
    }

    /**
     * 根据班级查询学生列表
     * @param classId
     * @return
     */
    @ApiOperation(value = "根据班级查询学生列表")
    @GetMapping("/stu_list/{classId}")
    public ResultVM<List<User>> stu_list(@PathVariable("classId") String classId)
    {
        List<User> result = userService.findByClassId(classId);
        return ResultVM.success(result);
    }

//    @GetMapping("/{id}")
//    public ResultVM<User> list(@PathVariable("id") String id)
//    {
//        User user = userService.findById(id);
//
//        return ResultVM.success(user);
//    }

    /**
     * 我的资料(用于编辑用户)
     * @return
     */
    @GetMapping("/myinfo")
    @ApiOperation(value = "我的资料(用于编辑用户)")
    public ResultVM<User> myinfo()
    {
        UserInfo user = AuthInterceptor.getCurrUser();
        User dbuser = userService.findByUsername(user.getUsername());
        return ResultVM.success(dbuser);
    }
    /**
     * 我的资料(用于返回权限信息)
     * @return
     */
    @GetMapping("/info")
    @ApiOperation(value = "我的资料(用于返回权限信息)")
    public ResultVM<Map<String,Object>> info(String access_token)
    {
        UserInfo user = AuthInterceptor.getCurrUser();
        User dbuser = userService.findByUsername(user.getUsername());
        Set<String> moduleSet = new HashSet<>();

        dbuser.getRoleSet().forEach(role->role.getModuleSet().forEach(module->moduleSet.add(module.getName())));


        HashMap<String,Object> data = new HashMap<>();
        data.put("name",user.getUsername());
        data.put("user_id","");
        data.put("access",moduleSet.toArray());
        data.put("token",access_token);
        data.put("avator","https://avatars0.githubusercontent.com/u/20942571?s=460&v=4");
        return ResultVM.success(data);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有用户")
    public ResultVM<List<User>> getAll()
    {
        List<User> result = userService.findAll();
        return ResultVM.success(result);
    }
    @ApiOperation(value = "所有老师")
    @GetMapping("/teachers")
    public ResultVM<List<User>> teachers()
    {
        List<User> result = userService.findAllTeachers();
        return ResultVM.success(result);
    }

    @ApiOperation(value = "检查用户名是否可用")
    @GetMapping("/checkUsername/{username}")
    public ResultVM<String> checkUsername(@PathVariable("username")String username)
    {
        User user  = userService.findByUsername(username.toLowerCase());
        ResultVM<String> result= new ResultVM<>();
        if(user==null)
        {
            result.setCode(1);
            result.setDesc("用户名可用");
        }
        else
        {
            result.setCode(0);
            result.setDesc("用户名已经被【"+user.getUserInfo().getName()+"】霸占了");
        }
        return result;
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        UserInfo user = AuthInterceptor.getCurrUser();
        User dbuser  = userService.findByUsername(user.getUsername());
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUsername(), dbuser.getUserInfo().getName(), new Date());
        String content = dbuser.getUserInfo().getName() + "删除用户"+ids;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        userService.deleteByIds(ids);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @ApiOperation(value = "更新用户")
    @PutMapping
    public ResultVM<String> update(@RequestBody User user)
    {
        UserInfo userinfo = AuthInterceptor.getCurrUser();
        User dbuser  = userService.findByUsername(userinfo.getUsername());
        UserEventLog userEventLog = new UserEventLog(dbuser.getId(), dbuser.getUserName(), dbuser.getUserInfo().getName(), new Date());
        String content = dbuser.getUserInfo().getName() + "修改用户:"+user;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        userService.update(user);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @ApiOperation(value = "新增用户")
    @PostMapping
    public ResultVM<String> add(@RequestBody User user)
    {
        UserInfo userinfo = AuthInterceptor.getCurrUser();
        User dbuser  = userService.findByUsername(userinfo.getUsername());
        UserEventLog userEventLog = new UserEventLog(userinfo.getId(), userinfo.getUsername(), dbuser.getUserInfo().getName(), new Date());
        String content = dbuser.getUserInfo().getName() + "新增用户:"+user;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        userService.save(user);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @ApiOperation(value = "更新用户角色")
    @PutMapping("/role")
    public ResultVM<String> role(@RequestBody RoleVM role)
    {
        userService.updateRole(role.getId(),role.getRoleIds());
        return ResultVM.success(null);
    }

    @ApiOperation(value = "注册学生")
    @PostMapping("/register")
    public ResultVM<String> register(@RequestBody User user, @RequestParam("classesId") String classesId)
    {

        user.setClasses(new Classes(classesId));

        Boolean ok = userService.save(user);
        ResultVM<String> result= new ResultVM<>();
        if(ok)
        {
            result.setCode(1);
            result.setDesc("注册成功");
        }
        else
        {
            result.setCode(0);
            result.setDesc("注册失败");
        }
        return result;
    }

    /**
     * 更新我的资料
     * @param user
     * @return
     */
    @ApiOperation(value = "更新个人资料")
    @PutMapping("/updateMyInfo")
    public ResultVM<String> updateMyInfo(@RequestBody User user)
    {


        UserInfo userinfo = AuthInterceptor.getCurrUser();
        User dbuser  = userService.findByUsername(userinfo.getUsername());
        UserEventLog userEventLog = new UserEventLog(userinfo.getId(), userinfo.getUsername(), dbuser.getUserInfo().getName(), new Date());

        String content =userinfo.getUsername() + "更新个人资料:"+user;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        userService.updateMyInfo(user);

        return ResultVM.success(null);
    }
}
