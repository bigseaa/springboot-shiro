package com.bigsea.service;

import com.bigsea.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User相关interface（伪数据）
 * @author sea
 * @date 2019-06-22
 */
public interface UserService {
    /**
     * 构造伪数据
     */
    List<User> getAllUser();

    /**
     * 根据用户名查找用户
     * @param username
     * @return User
     */
    User findByName(String username);
}
