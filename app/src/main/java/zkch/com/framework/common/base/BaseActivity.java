package zkch.com.framework.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import org.xutils.common.util.LogUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import zkch.com.framework.R;
import zkch.com.framework.common.http.ApiClient;
import zkch.com.framework.common.rx.ApiStores;
import zkch.com.framework.utils.JumpUtils;
import zkch.com.framework.utils.LogcatHelper;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder bind;
    public Activity mActivity;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initMvp();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        mActivity = this;
        bind = ButterKnife.bind(this);
        initView();

    }

    protected abstract void initMvp();

    protected abstract int getLayoutResID();

    protected abstract void initView();

    protected abstract void initDestroy();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
        if (bind != Unbinder.EMPTY) {
            bind.unbind();//解除绑定
        }
        LogcatHelper.getInstance(this).stop();
        initDestroy();
    }

    public void onUnsubscribe() {
        LogUtil.d("onUnSubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    public ApiStores apiStores() {
        return ApiClient.retrofit().create(ApiStores.class);
    }

    /**
     * 启动目标Activity，并关闭自身
     */
    public void launchWithFinishSelf(Class<?> target) {
        JumpUtils.launchWithFinishSelf(this, target);
    }


    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
