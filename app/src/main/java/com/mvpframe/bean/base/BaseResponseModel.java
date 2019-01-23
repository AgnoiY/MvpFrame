package com.mvpframe.bean.base;

import java.io.Serializable;

/**
 * <功能详细描述>
 * <p>
 * Data：2018/12/18
 *
 * @author yong
 */


public class BaseResponseModel<T> implements Serializable {


    /**
     * 数据对象/成功返回对象
     */
    private T data;
    /**
     * 状态码
     */
    private int code;
    /**
     * 描述信息
     */
    private String msg;
    /**
     * 是否成功
     *
     * @return
     */
    private boolean success = true;

    public T getData() {
        return data;
    }

    public BaseResponseModel<T> setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseResponseModel<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResponseModel<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseResponseModel<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }


    private String loginState;
    private T userInfo;

    public String getLoginState() {
        return loginState;
    }

    public BaseResponseModel<T> setLoginState(String loginState) {
        this.loginState = loginState;
        return this;
    }

    public T getUserInfo() {
        return userInfo;
    }

    public BaseResponseModel<T> setUserInfo(T userInfo) {
        this.userInfo = userInfo;
        return this;
    }
}
