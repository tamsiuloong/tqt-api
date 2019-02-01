package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.vo.ResultVO;
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
@RequestMapping("/api/message")
@RestController
public class MessageCtrl {



    @GetMapping("/count")
    public Integer count()
    {
        return 3;
    }


}
