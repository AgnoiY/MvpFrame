package com.mvpframe.ui.base.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvpframe.app.MyApplication;
import com.mvpframe.biz.BasePresenter;
import com.mvpframe.biz.IMvpView;
import com.mvpframe.ui.base.PresentationLayerFuncHelper;
import com.mvpframe.ui.base.delegate.FragmentMvpDelegate;
import com.mvpframe.ui.base.delegate.FragmentMvpDelegateImpl;
import com.mvpframe.ui.base.interfaces.CreateInit;
import com.mvpframe.ui.base.interfaces.PresentationLayerFunc;
import com.mvpframe.ui.base.interfaces.PublishActivityCallBack;
import com.mvpframe.util.Tools;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.app.Activity.RESULT_OK;


/**
 * * 备注:
 * 1.XXFragment 继承 MvpFragment,当页面存在 Presenter 时，具体 Fragment 需要调用 setPresenter(P... presenter)
 * 2.支持一个 Fragment 存在多个 Presenter
 *
 * @param <T>
 * @param <V>
 * @param <P> Data：2018/12/18
 * @author yong
 */

public abstract class BaseFragment<T, V extends IMvpView, P extends BasePresenter<V>> extends RxFragment
        implements CreateInit.CreateInitFragment<V, P>, PublishActivityCallBack, PresentationLayerFunc,
        IMvpView<T>, View.OnClickListener {

    protected FragmentMvpDelegate mvpDelegate;

    /**
     * 获取 Presenter 数组
     */
    public abstract P[] getPresenterArray();

    private PresentationLayerFuncHelper presentationLayerFuncHelper;

    public final String TAG = this.getClass().getSimpleName();

    /**
     * Activity对象
     */
    protected static Activity mActivity;

    /**
     * 此方法可以得到上下文对象
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        getMvpDelegate().onCreate(savedInstanceState);
    }

    /**
     * 返回一个需要展示的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater);
        return view;
    }

    /**
     * 当Activity初始化之后可以在这里进行一些数据的初始化操作
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
        presentationLayerFuncHelper = new PresentationLayerFuncHelper(mActivity);
        EventBus.getDefault().register(this);

        initListeners();
        initData();

        MyApplication.mApplication.currentActivityName = this.getClass().getName();
    }
    /**
     * 链接Presenter
     *
     * @return
     */
    @Override
    public P[] createPresenter() {
        return getPresenterArray();
    }
    /**
     * 注入View
     *
     * @return
     */
    @Override
    public V[] createView() {
        V[] views = null;
        P[] pArray = createPresenter();
        if (pArray != null) {
            views = (V[]) new IMvpView[pArray.length];
            for (int i = 0; i < pArray.length; i++) {
                views[i] = (V) this;
            }
        }
        return views;
    }
    /**
     * 关联Activity的生命周期
     *
     * @return
     */
    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl(this, this);
        }
        return mvpDelegate;
    }
    /**
     * 事件线
     *
     * @param eventModel
     */
    @Subscribe
    public void onEventMainThread(T eventModel) {

    }
    /**
     * 网络请求的错误信息，已在请求中处理提示Toast
     * 如果有特殊处理需重写
     *
     * @param action 区分不同事件
     * @param code   错误码
     * @param msg    错误信息
     */
    @Override
    public void onError(String action, int code, String msg) {

    }
    /**
     * 配合DataBinding点击事件监听
     * 添加防止重复点击
     * 有点击事件只需重写
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (Tools.isDoubleClick()) return;
    }

    @Override
    public void startActivity(Class<?> openClass, Bundle bundle) {
        Intent intent = new Intent(mActivity, openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openActivityForResult(Class<?> openClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mActivity, openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void setResultOk(Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) ;
        intent.putExtras(bundle);
        mActivity.setResult(RESULT_OK, intent);
    }

    @Override
    public void showToast(String msg) {
        presentationLayerFuncHelper.showToast(msg);
    }

    @Override
    public void showSoftKeyboard(View focusView) {
        presentationLayerFuncHelper.showSoftKeyboard(focusView);
    }

    @Override
    public void hideSoftKeyboard() {
        presentationLayerFuncHelper.hideSoftKeyboard();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }
}
