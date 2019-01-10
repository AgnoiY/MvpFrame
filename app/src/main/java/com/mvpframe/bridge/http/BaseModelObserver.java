package com.mvpframe.bridge.http;

import android.content.Context;

import com.google.gson.Gson;
import com.mvpframe.bean.base.BaseResponseModel;
import com.mvpframe.capabilities.http.observer.HttpObserver;
import com.mvpframe.util.LogUtil;
import com.mvpframe.view.dialog.UITipDialog;

import java.util.List;


/**
 * 根据业务进一步封装
 * <p>
 * Data：2018/12/18
 *
 * @author yong
 */
public abstract class BaseModelObserver<T> extends HttpObserver<T> {

    private Context context;

    public BaseModelObserver(Context context) {
        super();
        this.context = context;
    }

    public BaseModelObserver(Context context, boolean isDialog, boolean isCabcelble) {
        super(context, isDialog, isCabcelble);
        this.context = context;
    }

    private BaseResponseModel response;

    @Override
    public T onConvert(String data) {
        /**
         * 接口响应数据格式如@Response
         * 根据业务封装:
         * 1. response.isSuccess() (code==0) 业务逻辑成功回调convert()=>onSuccess()，否则失败回调onError()
         * 2.统一处理接口逻辑 例如:code==101 token过期等等
         */
        T t = null;

        response = (BaseResponseModel) new Gson().fromJson(data, getTypeClass());
        int code = response.getCode();
        String msg = response.getMsg();

        switch (code) {
            case 0://成功
                if (response.isSuccess()) {//与服务器约定成功逻辑
                    t = (T) response.getData();
                    if (t == null || t instanceof String) {
                        t = (T) response;
                    }
                } else {
                    onError(getTag(), code, "与服务器约定错误");
                }
                break;
            case 101://token过期，跳转登录页面重新登录(示例)
                break;
            default://统一为错误处理
                onError(getTag(), code, msg);
                break;
        }
        return t;
    }


    /**
     * 网络请求的错误信息
     * 如果有特殊处理需重写
     *
     * @param action 区分不同事件
     * @param code   错误码
     * @param desc   错误信息
     */
    public void onError(String action, int code, String desc) {
        UITipDialog.showFall(context, desc);
    }

    /**
     * 取消回调
     * 如果有特殊处理需重写
     */
    public void onCancel() {
        LogUtil.e("请求取消了");
    }

    @Override
    public void onSuccess(String action, List<T> value) {

    }

    /**
     * 业务逻辑是否成功
     *
     * @return
     */
    @Override
    public boolean isBusinessOk() {
        return response.isSuccess();
    }


}
