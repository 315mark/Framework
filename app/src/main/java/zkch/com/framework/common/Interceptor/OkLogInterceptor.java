package zkch.com.framework.common.Interceptor;

import android.util.Log;
import java.io.IOException;
import java.util.Set;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OkLogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        HttpUrl url = request.url();
        Log.d("本次请求", "url:" + url.toString() + "=====method=====" + method);
        Log.d("本次json", "json" + request.toString());

        Headers headers = request.headers();
        Set<String> names = headers.names();
        for (String next : names) {
            String value = headers.get(next);
            Log.d("=============", next + ":" + value);
        }
        return chain.proceed(request);
    }
}
