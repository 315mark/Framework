package zkch.com.framework.json;

import android.widget.FrameLayout;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseActivity;

public class FastJsonActivity extends BaseActivity {

    @BindView(R.id.view_content)
    FrameLayout viewContent;

    @Override
    protected void initMvp() {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_gson;
    }

    @Override
    protected void initView() {
        //FragmentUtils.add(getSupportFragmentManager(),new FastJsonFragment(), R.id.view_content);

    }

    @Override
    protected void initDestroy() {

    }

}
