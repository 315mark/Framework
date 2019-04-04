package zkch.com.framework.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zkch.com.framework.MyApp;

public abstract class BaseFragment extends Fragment {

    private View rootView;
    private Unbinder bind;
    protected MyApp mApplication;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, rootView);
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

        if (bind != Unbinder.EMPTY) {
            bind.unbind();
        }
    }
}
