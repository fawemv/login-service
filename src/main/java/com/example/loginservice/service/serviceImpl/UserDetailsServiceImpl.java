package com.example.loginservice.service.serviceImpl;


import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.service.IStudentService;
import com.example.loginservice.servicecode.service.ISysuserService;
import io.swagger.models.auth.In;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    ISysuserService sysUserService;

    @Resource
    IStudentService iStudentService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 权限
        List<GrantedAuthority> authority = null;
        Sysuser sysUser = sysUserService.getByUsername(username);

        if (sysUser == null) {
            Long aLong = null;
            try {
                aLong = new Long(username);
            } catch (Exception e) {
                System.out.println("{}不是一个学生账号");
                throw new UsernameNotFoundException("用户名或密码不正确");
            }
            Student stu = iStudentService.getById(aLong);

            if (stu == null)
                throw new UsernameNotFoundException("用户名或密码不正确");

            authority = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_student");
            return new AccountUser(stu.getsId(), stu.getsId() + "", stu.getPassword(), authority);

        } else {
            authority = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        }

        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), authority);
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     *
     * @param
     * @return
     */
    public String getUserAuthority(String username) {

        // 权限
        String authority = null;
        Sysuser sysUser = sysUserService.getByUsername(username);

        if (sysUser == null) {
            Long aLong = null;
            try {
                aLong = new Long(username);
            } catch (Exception e) {
                System.out.println("{}不是一个学生账号");

            }
            Student stu = iStudentService.getById(aLong);

            if (stu == null)
                return null;
            authority = "ROLE_student";
            return authority;

        } else {
            authority = "ROLE_admin";
        }

        return authority;
    }
}
