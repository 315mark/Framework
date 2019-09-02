package zkch.com.framework.common.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import zkch.com.framework.common.http.ApiClient;
import zkch.com.framework.common.rx.ApiStores;

/**
 * 个人博客：http://wuxiaolong.me/
 */
public class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = ApiClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.mvpView = null;
        onUnSubscribe();
    }

    //RxJava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void addSubscription(Observable observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
