package com.coachtam.tqt.web.controller.student;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.UserEventLog;
import com.coachtam.tqt.event.UserEvent;
import com.coachtam.tqt.interceptor.AuthInterceptor;
import com.coachtam.tqt.service.UserEventLogService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.ModelMapperSingle;
import com.coachtam.tqt.viewmodel.student.base.RestResponse;
import com.coachtam.tqt.viewmodel.student.user.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author coachtam
 */
@RestController("StudentUserController")
@RequestMapping(value = "/api/student/user")
@AllArgsConstructor
public class UserController{

    private final UserService userService;
    private final UserEventLogService userEventLogService;
//    private final MessageService messageService;
//    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher eventPublisher;


    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();


    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = userService.findByUsername(AuthInterceptor.getCurrUser().getUsername());
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }


//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public RestResponse register(@RequestBody @Valid UserRegisterVM model) {
//        User existUser = userService.findByUsername(model.getUserName());
//        if (null != existUser) {
//            return new RestResponse<>(2, "用户已存在");
//        }
//        User user = modelMapper.map(model, User.class);
//        String encodePwd = authenticationService.pwdEncode(model.getPassword());
//        user.setUserUuid(UUID.randomUUID().toString());
//        user.setPassword(encodePwd);
//        user.setRole(RoleEnum.STUDENT.getCode());
//        user.setStatus(UserStatusEnum.Enable.getCode());
//        user.setLastActiveTime(new Date());
//        user.setCreateTime(new Date());
//        user.setDeleted(false);
//        userService.insertByFilter(user);
//        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
//        userEventLog.setContent("欢迎 " + user.getUserName() + " 注册来到学之思考试系统");
//        eventPublisher.publishEvent(new UserEvent(userEventLog));
//        return RestResponse.ok();
//    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
        if (StringUtils.isBlank(model.getBirthDay())) {
            model.setBirthDay(null);
        }
        User user = userService.findByUsername(AuthInterceptor.getCurrUser().getUsername());
        modelMapper.map(model, user);
        user.getUserInfo().setUpdateTime(new Date());
        userService.update(user);
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getUserInfo().getName(), new Date());
        userEventLog.setContent(user.getUserName() + " 更新了个人资料");
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok();
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public RestResponse<List<UserEventLogVM>> log() {
        User user = userService.findByUsername(AuthInterceptor.getCurrUser().getUsername());
        List<UserEventLog> userEventLogs = userEventLogService.getUserEventLogByUserId(user.getId());
        List<UserEventLogVM> userEventLogVMS = userEventLogs.stream().map(d -> {
            UserEventLogVM vm = modelMapper.map(d, UserEventLogVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(d.getCreateTime()));
            return vm;
        }).collect(Collectors.toList());
        return RestResponse.ok(userEventLogVMS);
    }

//    @RequestMapping(value = "/message/page", method = RequestMethod.POST)
//    public RestResponse<PageInfo<MessageResponseVM>> messagePageList(@RequestBody MessageRequestVM messageRequestVM) {
//        messageRequestVM.setReceiveUserId(getCurrentUser().getId());
//        PageInfo<MessageUser> messageUserPageInfo = messageService.studentPage(messageRequestVM);
//        List<Integer> ids = messageUserPageInfo.getList().stream().map(d -> d.getMessageId()).collect(Collectors.toList());
//        List<Message> messages = ids.size() != 0 ? messageService.selectMessageByIds(ids) : null;
//        PageInfo<MessageResponseVM> page = PageInfoHelper.copyMap(messageUserPageInfo, e -> {
//            MessageResponseVM vm = modelMapper.map(e, MessageResponseVM.class);
//            messages.stream().filter(d -> e.getMessageId().equals(d.getId())).findFirst().ifPresent(message -> {
//                vm.setTitle(message.getTitle());
//                vm.setContent(message.getContent());
//                vm.setSendUserName(message.getSendUserName());
//            });
//            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
//            return vm;
//        });
//        return RestResponse.ok(page);
//    }
//
//    @RequestMapping(value = "/message/unreadCount", method = RequestMethod.POST)
//    public RestResponse unReadCount() {
//        Integer count = messageService.unReadCount(getCurrentUser().getId());
//        return RestResponse.ok(count);
//    }
//
//    @RequestMapping(value = "/message/read/{id}", method = RequestMethod.POST)
//    public RestResponse read(@PathVariable Integer id) {
//        messageService.read(id);
//        return RestResponse.ok();
//    }

}
