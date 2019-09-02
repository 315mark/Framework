package zkch.com.framework.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import zkch.com.framework.MyApp;
import zkch.com.framework.R;

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    private View rootView;
    private Unbinder bind;
    protected MyApp mApplication;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mApplication = (MyApp) getActivity().getApplication();
        init();
    }

    /**
     * protected  修饰指继承该类可执行方法
     */
    protected abstract int getLayoutId();
    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        if (bind != Unbinder.EMPTY) {
            bind.unbind();
        }
    }

    private CompositeDisposable mCompositeDisposable;

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void addSubscription(DisposableObserver observer) {
//        if (mCompositeDisposable == null) {
        mCompositeDisposable = new CompositeDisposable();
//        }
        mCompositeDisposable.add(observer);
    }

    public Toolbar initToolBar(View view, String title) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
        return toolbar;
    }

    public void inithttp(Request request,Callback callback) {
        new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build().newCall(request).enqueue(callback);
    }

}
