package com.coachtam.tqt.viewmodel.student.user;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.UserInfo;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.ModelMapperSingle;
import lombok.Data;
import org.modelmapper.ModelMapper;



@Data
public class UserResponseVM  {

    protected static ModelMapper modelMapper = ModelMapperSingle.Instance();

    private String id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    private Integer role;

    private Integer sex;

    private String birthDay;

    private String phone;

    private String lastActiveTime;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private Integer userLevel;

    public static UserResponseVM from(User user) {
        UserResponseVM vm = new UserResponseVM();
        UserInfo userInfo = user.getUserInfo();
        if(userInfo!=null)
        {
            vm.setRealName(user.getUserInfo().getName());
            vm.setPhone(user.getUserInfo().getTelephone());
            vm.setId(user.getId());
            vm.setSex(Integer.parseInt(userInfo.getGender()));
            vm.setStatus(user.getState());
            vm.setBirthDay(DateTimeUtil.dateFormat(user.getUserInfo().getBirthday()));
            vm.setLastActiveTime(DateTimeUtil.dateFormat(user.getUserInfo().getCreateTime()));
            vm.setCreateTime(DateTimeUtil.dateFormat(user.getUserInfo().getCreateTime()));
            vm.setModifyTime(DateTimeUtil.dateFormat(user.getUserInfo().getUpdateTime()));
        }


        return vm;
    }
}
