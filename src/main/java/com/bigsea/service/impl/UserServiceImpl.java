package com.bigsea.service.impl;

import com.bigsea.entity.Privilege;
import com.bigsea.entity.Role;
import com.bigsea.entity.User;
import com.bigsea.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关实现类（伪数据）
 * @author sea
 * @date 2019-06-22
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 构造伪数据
     */
    @Override
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>(5);
        User user = new User();
        user.setUsername("root");
        String password = "123456";
        password = String.valueOf(new Md5Hash(password, "root", 3));
        user.setPassword(password);

        Role role = new Role();
        List<Privilege> privilegeList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Privilege privilege = new Privilege();
            privilege.setId(String.valueOf(i+1));
            privilege.setCode("code" + i);
            privilege.setName("name" + i);
            privilegeList.add(privilege);
        }
        role.setId("1");
        role.setName("管理员");
        role.setPrivilegeList(privilegeList);
        user.setRole(role);

        userList.add(user);
        for(int i = 1; i < 5; i++) {
            User u = new User();
            u.setUsername("test" + i);
            u.setPassword("1234" + i);
            userList.add(u);
        }
        return userList;
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return User
     */
    @Override
    public User findByName(String username) {
        List<User> userList = getAllUser();
        for(User user : userList) {
            String uname = user.getUsername();
            if(username.equals(uname)) {
                return user;
            }
        }
        return null;
    }
}
