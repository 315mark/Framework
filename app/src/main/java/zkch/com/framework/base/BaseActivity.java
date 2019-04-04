package zkch.com.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zkch.com.framework.utils.JumpUtils;
import zkch.com.framework.utils.LogcatHelper;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        bind = ButterKnife.bind(this);
        initView();
    }

    protected abstract int getLayoutResID();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != Unbinder.EMPTY) {
            bind.unbind();//解除绑定
        }
        LogcatHelper.getInstance(this).stop();
    }

    /**
     * 启动目标Activity
     */
    public void launch(Class<?> target) {
        JumpUtils.launch(this, target);
    }

    /**
     * 启动目标Activity, 传入数据
     */
    public void launch(Bundle bundle, Class<?> target) {
        JumpUtils.launch(this, bundle, target);
    }

    /**
     * 启动目标Activity，并关闭自身
     */
    public void launchWithFinishSelf(Class<?> target) {
        JumpUtils.launchWithFinishSelf(this, target);
    }


    /**
     * 启动目标Activity，传入数据并关闭自身
     */
    public void launchWithFinishSelf(Bundle bundle, Class<?> target) {
        JumpUtils.launchWithFinishSelf(this, bundle, target);
    }

    /**
     * 启动目标Activity，关闭时需返回结果
     **/
    public void launchNeedResult(Bundle bundle, Class<?> target, int requestCode) {
        JumpUtils.launchNeedResult(this, bundle, target, requestCode);
    }

    /**
     * 关闭当前界面，回传结果
     **/
    public void finishWithResult(Bundle bundle, Class<?> target, int resultCode) {
        JumpUtils.finishWithResult(this, bundle, target, resultCode);
    }

}
