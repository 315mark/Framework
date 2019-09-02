package zkch.com.framework.json;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseFragment;
import zkch.com.framework.json.bean.ShopInfo;

public class GsonFragment extends BaseFragment {

    @BindView(R.id.toJson)
    Button toJson;
    @BindView(R.id.javaList)
    Button javaList;
    @BindView(R.id.javaToList)
    Button javaToList;
    @BindView(R.id.javaToArray)
    Button javaToArray;
    @BindView(R.id.view_introduction)
    ExpandableTextView viewIntroduction;
    @BindView(R.id.tv_introduction)
    ReadMoreTextView tvIntroduction;
    @BindView(R.id.jump)
    Button jump;

    public GsonFragment() {
    }


    /**
     * protected  修饰指继承该类可执行方法
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_json;
    }

    @Override
    protected void init() {
        Log.i(getClass().getName(), "init:   初始化界面");
    }

    @OnClick({R.id.toJson, R.id.javaList, R.id.javaToList, R.id.javaToArray,R.id.jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toJson:
                //将JSON对象转换成Java对象
                jsonToJavaObjectByGson();
                return;

            case R.id.javaList:
                //将JSON数组转换成Java集合
                jsonToJavaListByGson();
                return;

            case R.id.javaToList:
                //将java对象转化成JSON对象
                javaToJsonObjectByGson();
                return;

            case R.id.javaToArray:
                //将java集合转化成JSON数组
                javaToJsonArrayByGson();
                return;

            case R.id.jump:
                Log.i(getClass().getName(), "onClick: 执行到此");
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.view_content, new NativePraseFragment())
                        .commit();
                return;
            default:
                break;
        }
    }

    // （4）将Java对象的List转换为json字符串[]
    private void javaToJsonArrayByGson() {

        // 1 获取或创建Java对象
        List<ShopInfo> shops = new ArrayList<>();
        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 251.0, "longxia");

        shops.add(baoyu);
        shops.add(longxia);

        // 2 生成JSON数据
        Gson gson = new Gson();
        String json = gson.toJson(shops);

        // 3 展示数据
        viewIntroduction.setText(shops.toString());
        tvIntroduction.setText(json);
    }

    // （3）将Java对象转换为json字符串{}
    private void javaToJsonObjectByGson() {

        // 1 获取或创建Java对象
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");

        // 2 生成JSON数据
        Gson gson = new Gson();

        String json = gson.toJson(shopInfo);

        // 3 展示数据

        viewIntroduction.setText(shopInfo.toString());
        tvIntroduction.setText(json);
    }

    // （2）将json格式的字符串[]转换为Java对象的List
    private void jsonToJavaListByGson() {

        // 1 获取或创建JSON数据
        String json = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f1.jpg\",\n" +
                "        \"name\": \"大虾1\",\n" +
                "        \"price\": 12.3\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f2.jpg\",\n" +
                "        \"name\": \"大虾2\",\n" +
                "        \"price\": 12.5\n" +
                "    }\n" +
                "]";

        // 2 解析JSON数据
        Gson gson = new Gson();

        List<ShopInfo> shops = gson.fromJson(json, new TypeToken<List<ShopInfo>>() {
        }.getType());

        // 3 展示数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(shops.toString());
    }

    // (1）将json格式的字符串{}转换为Java对象
    private void jsonToJavaObjectByGson() {

        // 1 获取或创建JSON数据
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";

        // 2 解析JSON数据
        Gson gson = new Gson();

        ShopInfo shopInfo = gson.fromJson(json, ShopInfo.class);

        // 3 展示数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(shopInfo.toString());
    }
}
