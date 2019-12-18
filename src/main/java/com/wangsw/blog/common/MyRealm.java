package com.wangsw.blog.common;

import com.wangsw.blog.dao.TUserMapper;
import com.wangsw.blog.po.TUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm{

	@Autowired
	private TUserMapper tUserMapper;

	/**
	 * 用来返回用户权限的方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}

	/**
	 * 用于判断登录信息的方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
	        String userName =  (String)authenticationToken.getPrincipal();
	        TUser user = tUserMapper.selectByUserName(userName);
	        if(null == user){
	        	throw new UnknownAccountException("账号或密码不正确");
	        }
        	SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER, user); // 将当前用户存入session
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(),getName());
			return authcInfo;
	}

}
