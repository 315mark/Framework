package zkch.com.framework.okhttp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zkch.com.framework.MyApp;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseFragment;
import zkch.com.framework.okhttp.adapter.OkHttpListAdapter;
import zkch.com.framework.okhttp.bean.DataBean;
import zkch.com.framework.utils.JsonUtil;
import zkch.com.framework.weight.RefreshLottieHeader;

public class OkHttpFragment extends BaseFragment {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refresh;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    /**
     * protected  修饰指继承该类可执行方法
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_okhttp;
    }

    RefreshLottieHeader mRefreshHeader;
    OkHttpListAdapter adapter;
    private String url;
    List<DataBean.ItemData> dataList ;

    @Override
    protected void init() {
        //初始化RecylerView
        //1,加载空布局文件，便于第五步适配器在没有数据的时候加载
        View emptyView = View.inflate(MyApp.getContext(), R.layout.empty_view, null);
        recycleView.setLayoutManager(new LinearLayoutManager(mApplication));
        adapter =new OkHttpListAdapter();
        recycleView.setAdapter(adapter);
        //5，给recyclerView设置空布局
        adapter.setEmptyView(emptyView);
        mRefreshHeader =new RefreshLottieHeader(MyApp.getContext());
        initRecyleView();
        //初始化的时候默认没有数据，显示空的布局
        getData(1);
        inithttp();
    }

    private void inithttp() {
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        Request request = new Request.Builder().url(url).get().build();
        inithttp(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressbar.post(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                    }
                });
                tvNodata.setText(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tvNodata.setVisibility(View.GONE);
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CacheUtils.putString(MyApp.getContext(),url,result);
                processData(result);
            }
        });
    }
    private void initRecyleView() {
        dataList =new ArrayList<>();
        refresh.setRefreshHeader(mRefreshHeader);
        //设置 Header 为 贝塞尔雷达 样式
//       refresh.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式\
//        refresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        //下拉刷新监听
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                //下拉刷新,一般添加调用接口获取数据的方法
                getData(2);
                refreshLayout.finishRefresh();
            }
        });
        //加载更多监听
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                getData(3);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
    }

    /**
     * 获取数据的方法
     * 该方法纯属展示各种效果，实际应用时候请自己根据需求做判断即可
     * @param mode 模式：1为刚开始进来加载数据 空数据 2为下拉刷新 3为上拉加载
     */
    private void getData(int mode) {
        //添加临时数据，一般直接从接口获取
        switch (mode) {
            case 1:
                break;
            case 2:
                //更新数据  具体操作不知道
                break;
            case 3:
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 解析和显示数据
     * @param Json
     */
    private void processData(String Json) {
//        DataBean dataBean = parsedJson(Json);
//        final List<DataBean.ItemData> trailers = dataBean.getTrailers();
        final List<DataBean.ItemData> trailers = JsonUtil.fromJson(Json, DataBean.class).getTrailers();
        Log.i("TAG", "parsedJson: " +trailers);
        if (trailers != null && trailers.size() > 0) {
            //显示适配器
            recycleView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.addData(trailers);
                    progressbar.setVisibility(View.GONE);
                }
            },2000);

        }else {
            tvNodata.setVisibility(View.VISIBLE);
        }
    }
}
