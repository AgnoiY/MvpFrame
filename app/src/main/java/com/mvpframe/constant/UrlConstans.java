package com.mvpframe.constant;

import com.mvpframe.BuildConfig;

/**
 * <网络请求url地址>
 * Data：2018/12/18
 *
 * @author yong
 */
public class UrlConstans {

    /**
     * 服务器地址
     */
    private static String DEF_TEST_SERVER = "http://183.129.178.44:8320/klApp/";//测试环境
    private static String DEF_RELEASE_SERVER = "http://183.129.178.44:8320/klApp/";//测试环境

    public static final String BASESERVER = BuildConfig.DEBUG ? DEF_TEST_SERVER : DEF_RELEASE_SERVER;

    /**
     * 用户登陆
     */
//    public static final String LOGIN = "login.do";
    public static final String LOGIN = "user/loginByUserNamePassword";
    public static final String selector = "selector/storeType";
}
