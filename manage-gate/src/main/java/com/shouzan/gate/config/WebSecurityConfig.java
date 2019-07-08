package com.shouzan.gate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.cors.CorsUtils;


/**
 * @Description: (这里用一句话描述这个方法的作用)
 * @param
 * @[param]
 * @return
 * @author:  bin.yang
 * @date:  2018/10/17 下午2:56
 */

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${loginOut}")
	private String loginOut;

	@Override
    public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				// 拦截
				.antMatcher("/**")
				// 请求授权
				.authorizeRequests()
				//将Preflight不做拦截
		        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				// 放行
				.antMatchers("/login**","/logoutMsg","/**/*.css", "/*/*.png","/","/back/websiteCustomer/**",
						"/*/*.jpg", "/**/*.js","/**/*.json", "/**/*.html","/login/**","/back/websiteText/**")
				// 无条件允许访问
				.permitAll()
				// 访问所有
				.anyRequest()
				// 允许认证的用户进行访问
				.authenticated()
		        // 允许跨域
				.and().cors();
		// 退出
		http.logout().logoutUrl("/logout").logoutSuccessUrl(loginOut);
    }
}
