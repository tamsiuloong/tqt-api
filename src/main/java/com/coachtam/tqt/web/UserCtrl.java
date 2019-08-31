package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.to.UserForm;
import com.coachtam.tqt.vo.ResultVO;
import com.coachtam.tqt.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 12:23:59
 */
@RequestMapping("/api/user")
@RestController
public class UserCtrl {

    @Autowired
    private UserService userService;

    @PostMapping("/search")
    public ResultVO<Page> list(Integer pageNo, Integer pageSize, @RequestBody UserForm userForm)
    {
        Page result = userService.page(pageNo,pageSize,userForm);
        return ResultVO.success(result);
    }

    /**
     * 根据班级查询学生列表
     * @param classId
     * @return
     */
    @GetMapping("/stu_list/{classId}")
    public ResultVO<List<User>> stu_list(@PathVariable("classId") String classId)
    {
        List<User> result = userService.findByClassId(classId);
        return ResultVO.success(result);
    }

//    @GetMapping("/{id}")
//    public ResultVO<User> list(@PathVariable("id") String id)
//    {
//        User user = userService.findById(id);
//
//        return ResultVO.success(user);
//    }

    /**
     * 我的资料(用于编辑用户)
     * @return
     */
    @GetMapping("/myinfo")
    public ResultVO<User> myinfo()
    {
        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbuser = userService.findByUsername(user.getUsername());

        return ResultVO.success(dbuser);
    }
    /**
     * 我的资料(用于返回权限信息)
     * @return
     */
    @GetMapping("/info")
    public ResultVO<Map<String,Object>> info(String access_token)
    {
//        name: 'admin',
//                user_id: '2',
//            access: ['admin'],
//        token: 'admin',
//                avator: 'https://avatars0.githubusercontent.com/u/20942571?s=460&v=4'
        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HashMap<String,Object> data = new HashMap<>();
        data.put("name",user.getUsername());
        data.put("user_id","");
        data.put("access",user.getAuthorities().stream().map(a->a.getAuthority()).toArray());
        data.put("token",access_token);
        data.put("avator","https://avatars0.githubusercontent.com/u/20942571?s=460&v=4");
        return ResultVO.success(data);
    }

    @GetMapping("/all")
    public ResultVO<List<User>> getAll()
    {
        List<User> result = userService.findAll();
        return ResultVO.success(result);
    }

    @GetMapping("/teachers")
    public ResultVO<List<User>> teachers()
    {
        List<User> result = userService.findAllTeachers();
        return ResultVO.success(result);
    }

    @GetMapping("/checkUsername/{username}")
    public ResultVO<String> checkUsername(@PathVariable("username")String username)
    {
        User user  = userService.findByUsername(username.toLowerCase());
        ResultVO<String> result= new ResultVO<>();
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

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        userService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody User user)
    {
        userService.update(user);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody User user)
    {
        userService.save(user);
        return ResultVO.success(null);
    }

    @PutMapping("/role")
    public ResultVO<String> role(@RequestBody RoleVO role)
    {
        userService.updateRole(role.getId(),role.getRoleIds());
        return ResultVO.success(null);
    }

    @PostMapping("/register")
    public ResultVO<String> register(@RequestBody User user,@RequestParam("classesId") String classesId)
    {

        user.setClasses(new Classes(classesId));

        Boolean ok = userService.save(user);
        ResultVO<String> result= new ResultVO<>();
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
    @PutMapping("/updateMyInfo")
    public ResultVO<String> updateMyInfo(@RequestBody User user)
    {
        userService.updateMyInfo(user);

        return ResultVO.success(null);
    }
}
