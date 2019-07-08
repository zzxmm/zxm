package com.shouzan.gate.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class SessionAccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession httpSession = ctx.getRequest().getSession();
        HttpServletRequest request = ctx.getRequest();
        Object user = getSessionUser(httpSession);
        if(user!=null) {
            ctx.addZuulRequestHeader("UserID",Base64Utils.encodeToString(user.toString().getBytes()));
        }
        return null;
    }

    /**
     * 返回session中的用户信息
     * @param httpSession
     * @return
     */
    private Object getSessionUser(HttpSession httpSession) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContextImpl==null){
            return null;
        }
        Object user = securityContextImpl.getAuthentication().getPrincipal();
        return user;
    }

}
