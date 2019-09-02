package zkch.com.framework.common.mvp;

import android.util.Log;
import com.google.gson.Gson;
import java.util.List;
import io.reactivex.Observable;
import zkch.com.framework.bean.MainModel;
import zkch.com.framework.bean.MovieSubject;
import zkch.com.framework.bean.Subject;
import zkch.com.framework.common.base.BasePresenter;
import zkch.com.framework.common.rx.BaseObserver;
import zkch.com.framework.common.rx.HttpResultFunc;

/**
 * github:https://github.com/WuXiaolong/
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String cityId) {
        mvpView.showLoading();
        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId),
                new BaseObserver<MainModel>() {
                    @Override
                    public void onSuccess(MainModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                });
    }

    //统一处理数据
    public void loadDataRetrofitRxjava(String cityId) {
        mvpView.showLoading();
        Observable observable = apiStores.getTop250(0, 10).map(new HttpResultFunc<List<Subject>>());
        addSubscription(observable, new BaseObserver<List<Subject>>() {

            @Override
            public void onSuccess(List<Subject> model) {
                Log.w("TAG", "----suc-" + new Gson().toJson(model));
                mvpView.getDataSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getDataFail(msg);
            }

        });
    }

    //没做任何处理
    public void load(String cityId) {
        mvpView.showLoading();
        Observable observable = apiStores.getTopMovie(0, 10);
        addSubscription(observable, new BaseObserver<MovieSubject>() {
            @Override
            public void onSuccess(MovieSubject model) {
                Log.w("TAG", "----suc-" + new Gson().toJson(model));
                mvpView.getData(model);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getDataFail(msg);
            }

        });
    }

}
