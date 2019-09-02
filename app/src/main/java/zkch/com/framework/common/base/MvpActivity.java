package zkch.com.framework.common.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import zkch.com.framework.R;

/**
 * 个人博客：http://wuxiaolong.me/
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    protected abstract P createPresenter();

    protected abstract String setTitleText();

    @Override
    protected void initView() {
        initToolBarAsHome(setTitleText());
    }

    @Override
    protected void initMvp() {
        mvpPresenter =createPresenter();
    }

    @Override
    protected void initDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    public void initToolBarAsHome(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
}
