package zkch.com.framework.json;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseFragment;
import zkch.com.framework.json.bean.DataInfo;
import zkch.com.framework.json.bean.FilmInfo;
import zkch.com.framework.json.bean.ShopInfo;

public class NativePraseFragment extends BaseFragment {

    @BindView(R.id.toJson)
    Button toJson;
    @BindView(R.id.javaList)
    Button javaList;
    @BindView(R.id.javaToList)
    Button javaToList;
    @BindView(R.id.javaToArray)
    Button javaToArray;
    @BindView(R.id.jump)
    Button jump;
    @BindView(R.id.view_introduction)
    ExpandableTextView viewIntroduction;
    @BindView(R.id.tv_introduction)
    ReadMoreTextView tvIntroduction;


    /**
     * protected  修饰指继承该类可执行方法
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_json;
    }

    @Override
    protected void init() {

        javaToList.setText("复杂JSON数据解析");
        javaToArray.setText("特殊JSON数据解析");
    }

    public NativePraseFragment() {
    }

    @OnClick({R.id.toJson, R.id.javaList, R.id.javaToList, R.id.javaToArray,R.id.jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toJson:
                //将JSON对象转换成Java对象
                jsonToJavaObjectByNative();
                return;

            case R.id.javaList:
                //将JSON数组转换成Java集合
                jsonToJavaListByNative();
                return;

            case R.id.javaToList:
                //将java对象转化成JSON对象
                jsonToJavaOfComplex();
                return;

            case R.id.javaToArray:
                //将java集合转化成JSON数组
                jsonToJavaOfSpecial();
                return;

            case R.id.jump:
                Log.i(getClass().getName(), "onClick: 执行到此");
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.view_content, new FastJsonFragment())
                        .commit();
                return;
            default:
                break;
        }
    }

    // (4)特殊json数据解析
    private void jsonToJavaOfSpecial() {

        // 1 获取或创建JSON数据
        String json = "{" +
                "    code: 0," +
                "    list: {" +
                "        0: {" +
                "            aid: 6008965," +
                "            author: 哔哩哔哩番剧," +
                "            coins: 170," +
                "            copyright: Copy," +
                "            create: 2016-08-25 21:34" +
                "        }," +
                "        1: {" +
                "            aid: 6008938," +
                "            author: 哔哩哔哩番剧," +
                "            coins: 404," +
                "            copyright: Copy," +
                "            create: 2016-08-25 21:33" +
                "        }" +
                "    }" +
                "}";

        // 创建封装的Java对象
        FilmInfo filmInfo = new FilmInfo();

        // 2 解析json
        try {
            JSONObject jsonObject = new JSONObject(json);

            // 第一层解析
            int code = jsonObject.optInt("code");
            JSONObject list = jsonObject.optJSONObject("list");

            // 第一层封装
            filmInfo.setCode(code);
            List<FilmInfo.FilmBean> lists = new ArrayList<>();
            filmInfo.setList(lists);

            // 第二层解析
            for (int i = 0; i < list.length(); i++) {
                JSONObject jsonObject1 = list.optJSONObject(i + "");

                if(jsonObject1 != null) {
                    String aid = jsonObject1.optString("aid");

                    String author = jsonObject1.optString("author");

                    int coins = jsonObject1.optInt("coins");

                    String copyright = jsonObject1.optString("copyright");

                    String create = jsonObject1.optString("create");

                    // 第二层数据封装
                    FilmInfo.FilmBean filmBean = new FilmInfo.FilmBean();
                    filmBean.setAid(aid);
                    filmBean.setAuthor(author);
                    filmBean.setCoins(coins);
                    filmBean.setCopyright(copyright);
                    filmBean.setCreate(create);

                    lists.add(filmBean);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 3 显示JSON数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(filmInfo.toString());
    }

    // (3)复杂json数据解析
    private void jsonToJavaOfComplex() {

        // 1 获取或创建JSON数据
        String json = "{ data: " +
                "{ count: 5, items: " +
                "[ { id: 45, title: 坚果 }, { id: 132, title: 炒货 }, { id: 166, title: 蜜饯 }, { id: 195, title: 果脯 }, { id: 196, title: 礼盒 } ] " +
                "}," +
                " rs_code: 1000, rs_msg: success}";

        // 封装Java对象
        DataInfo dataInfo = new DataInfo();

        // 2 解析json
        try {
            JSONObject jsonObject = new JSONObject(json);

            // 第一层解析
            JSONObject data = jsonObject.optJSONObject("data");
            String rs_code = jsonObject.optString("rs_code");
            String rs_msg = jsonObject.optString("rs_msg");

            // 第一层封装
            dataInfo.setRs_code(rs_code);
            dataInfo.setRs_msg(rs_msg);
            DataInfo.DataBean dataBean = new DataInfo.DataBean();
            dataInfo.setData(dataBean);

            // 第二层解析
            int count = data.optInt("count");
            JSONArray items = data.optJSONArray("items");

            // 第二层数据的封装
            dataBean.setCount(count);

            List<DataInfo.DataBean.ItemsBean> itemsBean = new ArrayList<>();
            dataBean.setItems(itemsBean);

            // 第三层解析
            for (int i = 0; i < items.length(); i++) {
                JSONObject jsonObject1 = items.optJSONObject(i);

                if (jsonObject1 != null) {
                    int id = jsonObject1.optInt("id");

                    String title = jsonObject1.optString("title");

                    // 第三层数据的封装
                    DataInfo.DataBean.ItemsBean bean = new DataInfo.DataBean.ItemsBean();
                    bean.setId(id);
                    bean.setTitle(title);

                    itemsBean.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 3 显示JSON数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(dataInfo.toString());
    }


    // (2)将json格式的字符串[]转换为Java对象的List
    private void jsonToJavaListByNative() {

        // 1 获取或创建JSON数据
        String json = "[" +
                "    {" +
                "        id: 1," +
                "        imagePath: http://192.168.10.165:8080/f1.jpg," +
                "        name: 大虾1," +
                "        price: 12.3" +
                "    }," +
                "    {" +
                "        id: 2," +
                "        imagePath: http://192.168.10.165:8080/f2.jpg," +
                "        name: 大虾2," +
                "        price: 12.5" +
                "    }" +
                "]";

        List<ShopInfo> shops = new ArrayList<>();

        // 2 解析json
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject != null) {
                    int id = jsonObject.optInt("id");

                    String name = jsonObject.optString("name");

                    double price = jsonObject.optDouble("price");

                    String imagePath = jsonObject.optString("imagePath");

                    // 封装Java对象
                    ShopInfo shopInfo = new ShopInfo(id, name, price, imagePath);

                    shops.add(shopInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 3 显示JSON数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(shops.toString());
    }


    // (1)将json格式的字符串{}转换为Java对象
    private void jsonToJavaObjectByNative() {

        // 1 获取或创建JSON数据
        String json = "{" +
                "id:2, name:大虾, " +
                "price:12.3, " +
                "imagePath:http://192.168.10.165:8080/L05_Server/images/f1.jpg" +
                "}";

        ShopInfo shopInfo = null;

        // 2 解析json
        try {
            JSONObject jsonObject = new JSONObject(json);
//            int id = jsonObject.getInt("id");
            int id1 = jsonObject.optInt("id");

            String name = jsonObject.optString("name");

            double price = jsonObject.optDouble("price");

            String imagePath = jsonObject.optString("imagePath");

            // 封装Java对象
            shopInfo = new ShopInfo(id1, name, price, imagePath);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 3 显示JSON数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(shopInfo.toString());
    }
}
