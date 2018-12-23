package com.mvpframe.view.frameLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.mvpframe.R;
import com.mvpframe.databinding.LayoutEmptyBinding;

/**
 * 加载中布局 可以设置空页面 错误页面 加载中页面 titleView
 * Data：2018/12/18
 *
 * @author yong
 */

public class ViewLoadLayout extends FrameLayout {

    private LayoutEmptyBinding mBinding;

    private View mContentView;

    public ViewLoadLayout(@NonNull Context context) {
        this(context, null);
    }

    public ViewLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this, true);
        mBinding = DataBindingUtil.bind(mView);
    }


    public void hindEmptyAll() {
        showEmptyFra(false);
        setShowText(null);
        setShowImage(0);
    }

    private void showEmptyFra(boolean isShow) {
        mBinding.fraEmpty.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void hindAll() {
        hindEmptyAll();
        showContent(false);
    }

    public void showContent(boolean isShow) {
        if (mContentView != null) {
            mContentView.setVisibility(isShow ? VISIBLE : GONE);
        }
    }

    public void setShowText(String text) {
        showContent(text == null);
        showEmptyFra(text != null);
        mBinding.tvEmpty.setText(text);
        mBinding.tvEmpty.setVisibility(text != null ? VISIBLE : GONE);
    }

    @SuppressLint("ResourceType")
    public void setShowImage(@DrawableRes int img) {
        showEmptyFra(img != 0);
        showContent(img == 0);
        mBinding.imgEmpty.setVisibility(img != 0 ? VISIBLE : GONE);
        if (img <= 0) {
            mBinding.imgEmpty.setImageResource(R.mipmap.ic_launcher);
        } else {
            mBinding.imgEmpty.setImageResource(img);
        }
    }

    /**
     * 添加要显示的View
     *
     * @param contentView
     */
    public void addContentView(View contentView) {
        if (contentView != null) {
            mContentView = contentView;
            addView(contentView, 1);
        }
    }

}
