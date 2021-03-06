package com.mvpframe.capabilities.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.mvpframe.R;
import com.mvpframe.application.App;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;


/**
 * 错误/异常处理工具
 * <p>
 * Data：2018/12/18
 *
 * @author yong
 */
public class ExceptionEngine {

    ExceptionEngine() {
        throw new IllegalStateException("ExceptionEngine class");
    }

    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpExc = (HttpException) e;
            ex = new ApiException(e, httpExc.code(), App.getAppString(R.string.network_error));//均视为网络错误
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误(交由开发者自己处理)
            ServerException serverExc = (ServerException) e;
            ex = new ApiException(serverExc, serverExc.getCode(), serverExc.getMsg());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR, App.getAppString(R.string.data_parsing_error));
            return ex;
        } else if (e instanceof ConnectException || e instanceof SSLHandshakeException || e instanceof UnknownHostException) {//连接网络错误
            ex = new ApiException(e, CONNECT_ERROR, App.getAppString(R.string.connect_error));
            return ex;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            ex = new ApiException(e, TIME_OUT_ERROR, App.getAppString(R.string.time_out_error));
            return ex;
        } else {  //未知错误
            ex = new ApiException(e, UN_KNOWN_ERROR, App.getAppString(R.string.un_known_error));
            return ex;
        }
    }

}
