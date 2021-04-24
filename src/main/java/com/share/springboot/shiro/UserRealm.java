package com.share.springboot.shiro;

import com.share.springboot.entity.UserList;
import com.share.springboot.service.UserListService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**自定义Realm
 * @author 汪宇
 * @date 2020/4/27 11:14
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserListService userListService;
    @Override
    /**
     * 执行授权逻辑
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        return null;
    }

    @Override
    /**
     * 执行认证逻辑
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //编写shiro判断逻辑 判断用户名和密码】、
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //UserList user = userListService.findByNaAndPa(token.getUsername());
        UserList user = userListService.selectByPhone(token.getUsername());
        if (user == null){
            //用户名不存在
            return null;//shiro底层会抛出UnKnowAccountException
        }
        //2.判断密码
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUser_id());
        return new SimpleAuthenticationInfo("",user.getUser_password(),"");
    }
}
