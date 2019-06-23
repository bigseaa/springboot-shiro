package com.bigsea.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户控制层
 * @author sea
 * @date 2019-06-22
 */
@RequestMapping(value = "/user")
@Controller
public class UserController {

    /**
     * 个人主页
     * return String
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public @ResponseBody String home() {
        return "访问主页成功";
    }

    /**
     * 添加
     * return String
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String add() {
        return "添加成功";
    }

    /**
     * 查询
     * return String
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public @ResponseBody String query() {
        return "查询成功";
    }

    /**
     * 更新
     * return String
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update() {
        return "更新成功";
    }

    /**
     * 删除
     * return String
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody String del() {
        return "删除成功";
    }

    /**
     * 错误跳转地址
     * @param code String
     * @return String
     */
    @RequestMapping(value="/autherror", method = RequestMethod.GET)
    public @ResponseBody String authError(String code) {
        if ("1".equals(code)) {
            return "登录页面";
        } else if ("2".equals(code)) {
            return "未授权";
        }
        return "";
    }

    /**
     * 1.传统登录
     *      前端发送登录请求-->接口部分获取用户名密码-->程序员在接口部分手动控制
     * 2.shiro登录
     *      前端发送登录请求-->接口部分获取用户名密码-->通过subject.login-->realm域的认证方法
     */
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return string
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String login(String username, String password) {
        // 构造登录令牌
        try {
            /**
             * 密码加密：
             *     shiro提供md5加密
             *     Md5Hash：
             *     参数一：加密的内容（也就是密码明文）
             *               123456
             *     参数二：盐（加密的混淆字符串），因为MD5加密后相同的字符串生成的加密后的密文是一样的字符串，
             *              所以要加上混淆字符串
             *     参数三：加密次数
             */
            // 1.加密
            password = String.valueOf(new Md5Hash(password, username, 3));
            // 2.登录令牌
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            // 3.获取subject
            Subject subject = SecurityUtils.getSubject();
            // 4.调用subject进行登录
            subject.login(usernamePasswordToken);
            return "login success";
        } catch (Exception e) {
            return "login fail";
        }
    }
}
