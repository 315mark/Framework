package zkch.com.framework.common.Interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class OKHeaderInterceptor implements Interceptor {

    private Map<String,String> headers;

    public OKHeaderInterceptor(Map headers) {
        if (headers!=null){
            this.headers = headers;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request= chain.request();
        Request.Builder builder = request.newBuilder();
        if (headers!=null){
            Set set = headers.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                builder.addHeader(next,headers.get(next));
            }
        }
        request = builder.build();
        return chain.proceed(request);
    }
}
