package com.bigsea.realm;

import com.bigsea.entity.Privilege;
import com.bigsea.entity.Role;
import com.bigsea.entity.User;
import com.bigsea.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义realm
 * @author sea
 * @date 2019-06-22
 */
public class CustomRealm extends AuthorizingRealm {
    public void setName(String name) {
        super.setName("customRealm");
    }

    @Autowired
    private UserService userService;
    /**
     * 授权方法
     *     操作的时候，判断用户是否具有相应的权限
     *          先认证 -- 安全数据
     *          再授权 -- 根据安全数据获取用户具有的所有操作权限
     * @param principalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.获取已认证的用户数据
        User user = (User) principalCollection.getPrimaryPrincipal(); // 得到唯一的安全数据
        // 2.根据用户获取用户的权限信息（所有角色，所有权限）
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> privilegeSet = new HashSet<>();    // 所有权限
        Set<String> roleSet = new HashSet<>();         // 所有角色
        Role role = user.getRole();
        roleSet.add(role.getName());
        for (Privilege privilege : role.getPrivilegeList()) {
            privilegeSet.add(privilege.getCode());
        }
        simpleAuthorizationInfo.setStringPermissions(privilegeSet);
        simpleAuthorizationInfo.setRoles(roleSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *   参数：传递的用户名密码
     * @param authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.获取登录的用户名和密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        // 2.根据用户名查询数据库
        User user = userService.findByName(username);
        // 3.判断用户是否存在并且密码是否一致
        if (user != null && user.getPassword().equals(password)) {
            // 4.如果一致返回安全数据
            // 构造方法：安全数据，密码，realm域名
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return simpleAuthenticationInfo;
        }
        // 5.如果不一致，返回null（抛出异常）
        return null;
    }
}
