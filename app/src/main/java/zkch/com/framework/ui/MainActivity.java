package zkch.com.framework.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import zkch.com.framework.R;
import zkch.com.framework.bean.MovieSubject;
import zkch.com.framework.bean.Subject;
import zkch.com.framework.bean.MainModel;
import zkch.com.framework.common.base.MvpActivity;
import zkch.com.framework.common.mvp.MainPresenter;
import zkch.com.framework.common.mvp.MainView;
import zkch.com.framework.common.rx.BaseObserver;

/**
 * 由Activity/Fragment实现View里方法，包含一个Presenter的引用
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button0)
    Button button0;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected String setTitleText() {
        return getString(R.string.title);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_mvp_main;
    }


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void getDataSuccess(List<Subject> model) {

    }

    @Override
    public void getDataSuccess(MainModel model) {
        //接口成功回调
        dataSuccess(model);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getData(MovieSubject model) {

    }

    /*@OnClick({R.id.button2,R.id.button2})
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.button0 :
                loadDataByRetrofitRxJava();
                break;

            case R.id.button2 :
                //请求接口
                mvpPresenter.loadDataByRetrofitRxjava("101310222");
                break;

            default:
                break;
        }
    }*/

    //全国+国外主要城市代码http://mobile.weather.com.cn/js/citylist.xml
    private void loadDataByRetrofitRxJava() {
        addSubscription(
                apiStores().loadDataByRetrofitRxJava("101220602"),
                new BaseObserver<MainModel>() {

                    @Override
                    public void onSuccess(MainModel model) {
                        dataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                    }

                });
    }

    private void dataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        text.setText(showData);
    }

}
