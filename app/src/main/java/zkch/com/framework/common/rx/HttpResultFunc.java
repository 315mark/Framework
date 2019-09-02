package zkch.com.framework.common.rx;


import io.reactivex.functions.Function;
import zkch.com.framework.bean.BaseRespond;
import zkch.com.framework.common.http.ApiException;

/**
 * Created by wangwei on 2019/4/1.
 * * 剥离 最终数据
 */

public class HttpResultFunc<T> implements Function<BaseRespond<T>, T> {

    @Override
    public T apply(BaseRespond<T> httpResult) {
        if (httpResult.getCount() == 0) {
            //获取数据失败时，包装一个Fault 抛给上层处理错误
            throw new ApiException(httpResult.getTitle(), httpResult.getCount());
        }
        return httpResult.getSubjects();
    }
}
