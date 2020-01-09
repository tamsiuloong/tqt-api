package com.coachtam.tqt.config.security;

import com.coachtam.tqt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/1/17.
 *
 * @author coachtam
 * @since 1.0
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.coachtam.tqt.entity.User user = userService.findByUsername(username);
        boolean enabled=true;
        boolean accountNonExpired=true;
        boolean credentialsNonExpired=true;
        boolean accountNonLocked=true;
        if(user==null)
        {
            throw new UsernameNotFoundException("用户名错误");
        }
        if(user.getState()==0)
        {
            enabled = false;
//            throw new UsernameNotFoundException("用户名被停用");
        }
        if(user.getClasses()!=null&&user.getClasses().getClosed())
        {
            accountNonExpired = false;
//            throw new UsernameNotFoundException("班级已经毕业，账号停用");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoleSet().forEach(role ->
            role.getModuleSet().forEach(module ->
                    grantedAuthorities.add(new SimpleGrantedAuthority(module.getName())) )
        );
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetail userDetails = new UserDetail(
                username,
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                grantedAuthorities,user.getId(),user.getUserInfo().getName());


        return userDetails;
    }

}
