package com.wondersgroup.springbootshirodemo.shiro;

import com.wondersgroup.springbootshirodemo.model.Permissions;
import com.wondersgroup.springbootshirodemo.model.Role;
import com.wondersgroup.springbootshirodemo.model.User;
import com.wondersgroup.springbootshirodemo.service.LoginService;
import com.wondersgroup.springbootshirodemo.utils.TokenGenUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;

@Component
public class CustomAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       /* //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = loginService.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }*/

        MyUsernameToken token = (MyUsernameToken) authenticationToken;
        if (token != null && StringUtils.isNotBlank(token.getToken())) {
            String username = TokenGenUtils.desToken(token.getToken());
            if (StringUtils.isBlank(username)) {
                throw new AuthenticationException("msg:" + "用户令牌无效");
            }
            User user = loginService.getUserByName(username);
            if (user == null) {
                throw new AuthenticationException("msg:" + "用户账号不存在");
            } else {
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
                return simpleAuthenticationInfo;
            }
        } else {
            throw new AuthenticationException("msg:" + "用户令牌不能为空");
        }

    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = loginService.getUserByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        CustomCredentialsMatcher matcher = new CustomCredentialsMatcher();
        setCredentialsMatcher(matcher);
    }

    public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
        @Override
        public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
            MyUsernameToken token = (MyUsernameToken) authcToken;
            if ((token.getSso() != null && token.getSso()) || (token.getIntelli() != null && token.getIntelli())) {
                return true;
            } else {
                SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo) info;
                String vPw = DigestUtils.md5DigestAsHex(String.valueOf(token.getPassword()).getBytes());
                String nPw = simpleInfo.getCredentials().toString();
                return vPw.equals(nPw);
            }
        }
    }

}
