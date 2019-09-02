package zkch.com.framework.okhttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseActivity;
import zkch.com.framework.okhttp.bean.DataBean;

public class OkHttpActivity extends BaseActivity {

    @Override
    protected void initMvp() {
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_okhttplist;
    }

    @Override
    protected void initView() {
        //FragmentUtils.add(getSupportFragmentManager(),new OkHttpListFragment(), R.id.okhttp_content);
    }

    @Override
    protected void initDestroy() {

    }

    //解析数据
    private DataBean parsedJson(String response) {
        DataBean dataBean = new DataBean();
        try {
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.optJSONArray("trailers");
            if (jsonArray != null && jsonArray.length() > 0) {
                List<DataBean.ItemData> data = new ArrayList<>();
                dataBean.setTrailers(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (jsonObject != null) {
                        DataBean.ItemData itemData = new DataBean.ItemData();

                        String movieName = jsonObject.optString("movieName");
                        itemData.setMovieName(movieName);

                        String videoTitle = jsonObject.optString("videoTitle");
                        itemData.setVideoTitle(videoTitle);

                        String imgUrl = jsonObject.optString("coverImg");
                        itemData.setCoverImg(imgUrl);

                        String hightUrl = jsonObject.optString("hightUrl");
                        itemData.setHightUrl(hightUrl);
                        //把数据添加到集合
                        data.add(itemData);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;
    }
}
