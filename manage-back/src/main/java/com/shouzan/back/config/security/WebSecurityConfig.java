package com.shouzan.back.config.security;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-02 12:02
 */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//        .antMatchers("/api/**","/wxwallet/**","/aliWallet/**","/walletTerminal/**","/wcsver/getNewestZip/**","/wcsver/downLoad/**","/taskservice/**", "/*/**" ) // 放开"/api/**",通过oauth2.0来鉴权
//        .permitAll().and().authorizeRequests().antMatchers("/**").authenticated();
//    http.csrf().disable();
//    http.headers().frameOptions().disable();
//    http.httpBasic();
//    // 添加JWT filter
//    //http .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//
//    // 禁用缓存
//    http.headers().cacheControl();
//    http.headers().contentTypeOptions().disable();
//  }
//
//  @Override
//  public void configure(WebSecurity web) throws Exception {
//      //解决静态资源被拦截的问题
//      web.ignoring().antMatchers("/css/**","/imgs/**","/js/**");
//  }
//
//}
