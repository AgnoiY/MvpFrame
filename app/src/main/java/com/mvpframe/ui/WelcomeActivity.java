package com.mvpframe.ui;

import android.view.View;

import com.mvpframe.R;
import com.mvpframe.databinding.ActivityWelcomeBinding;
import com.mvpframe.presenter.base.BasePresenter;
import com.mvpframe.presenter.base.IMvpView;
import com.mvpframe.ui.base.activity.BaseLoadActivity;
import com.mvpframe.util.DownTime;

import static com.mvpframe.constant.Constants.logd;

/**
 * <欢迎页>
 * <p>
 * Data：2019/01/17
 *
 * @author yong
 */
public class WelcomeActivity extends BaseLoadActivity<Object, ActivityWelcomeBinding> {

    /**
     * 等待时间
     */
    private long delayMillis = 3;

    private DownTime downTime;

    @Override
    public BasePresenter<IMvpView<Object>>[] getPresenterArray() {
        return new BasePresenter[0];
    }

    @Override
    protected void onResume() {
        super.onResume();
        waitLoadMain();
    }

    @Override
    public void initData() {
        setShowStatusBar(false);
    }

    @Override
    public void initListeners() {
        mLoadBinding.btvDownTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btv_down_time) {
            skipLoadMain();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * 等待加载主界面或引导页
     */
    private void waitLoadMain() {

        log("等待时间：" + delayMillis + "s", logd);

        postDelayed(delayMillis);

        mLoadBinding.btvDownTime.setText(delayMillis + "s");
        downTime = new DownTime(mLoadBinding.btvDownTime, delayMillis, true);
    }

    @Override
    public void nextStep(Long l, Object... delay) {
        super.nextStep(l);
        skipLoadMain();
    }

    /**
     * 跳转到主界面或引导页
     */
    private void skipLoadMain() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && App.getApp().needWait(this)) {
//            startActivity(LoadResActivity.class, null);
//        } else
        startActivity(MainActivity.class, null);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (downTime != null) {
            downTime.cancel();
            downTime = null;
        }
    }
}
