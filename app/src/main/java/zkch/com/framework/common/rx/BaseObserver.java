package zkch.com.framework.common.rx;

import android.util.Log;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;
import zkch.com.framework.common.http.ApiException;

public abstract class BaseObserver<M> extends DisposableObserver<M> {

    public abstract void onSuccess(M model);
    public abstract void onFailure(String msg);

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof ApiException) {
            ApiException fault = (ApiException) throwable;
            String info = fault.getErrorInfo();
            Log.w("TAG","err---------"+info);

        }
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            Log.d("TAG", "code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            onFailure(throwable.getMessage());
        }

    }

    @Override
    public void onNext(M model) {
        onSuccess(model);

    }

    @Override
    public void onComplete() {

    }
}
