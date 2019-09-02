package zkch.com.framework.common.rx;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zkch.com.framework.bean.MainModel;
import zkch.com.framework.bean.BaseRespond;
import zkch.com.framework.bean.MovieSubject;
import zkch.com.framework.bean.Subject;
import zkch.com.framework.okhttp.bean.DataBean;

/**
 * https://github.com/WuXiaolong/
 */
public interface ApiStores {
    //baseUrl
    String baseUrl = "https://api.douban.com/v2/movie/";

    @GET("top250")
    Observable<MovieSubject> getTopMovie(@Query("start") int start, @Query("count") int count);

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<BaseRespond<List<Subject>>> getTop250(@Query("start") int start, @Query("count") int count);

    //baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxJava(@Path("cityId") String cityId);

    String BaseUrl ="http://api.m.mtime.cn/";

    @POST("PageSubArea/TrailerList.api")
    Observable<DataBean> getTrailerList();

}
