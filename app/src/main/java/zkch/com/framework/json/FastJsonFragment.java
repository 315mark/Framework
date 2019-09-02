package zkch.com.framework.json;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.alibaba.fastjson.JSON;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseFragment;
import zkch.com.framework.json.bean.ShopInfo;

public class FastJsonFragment extends BaseFragment {

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

    public FastJsonFragment() {
    }

    @OnClick({R.id.toJson, R.id.javaList, R.id.javaToList, R.id.javaToArray,R.id.jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toJson:
                //将JSON对象转换成Java对象
                jsonToJavaObjectByFastJson();
                return;

            case R.id.javaList:
                //将JSON数组转换成Java集合
                jsonToJavaListByFastJson();
                return;

            case R.id.javaToList:
                //将java对象转化成JSON对象
                javaToJsonObjectByFastJson();
                return;

            case R.id.javaToArray:
                //将java集合转化成JSON数组
                javaToJsonArrayByFastJson();
                return;

            case R.id.jump:
                Log.i(getClass().getName(), "onClick: 执行到此");
                GsonFragment f2 = new GsonFragment();
                FragmentManager mManager = getFragmentManager();
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.addToBackStack(null);//这里将我们的Fragment加入到返回栈
                ft1.replace(R.id.view_content,f2);
                ft1.commit();

//                getFragmentManager().beginTransaction()
//                        .addToBackStack(null)
//                        .replace(R.id.view_content, new GsonFragment())
//                        .commit();
                return;
            default:
                break;
        }
    }

    // （4）将Java对象的List转换为json字符串[]
    private void javaToJsonArrayByFastJson() {

        // 1 创建一个Java集合
        List<ShopInfo> shops = new ArrayList<>();

        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 251.0, "longxia");

        shops.add(baoyu);
        shops.add(longxia);

        // 2 生成JSON数据
        String json = JSON.toJSONString(shops);

        // 3 显示JSON数据
        viewIntroduction.setText(shops.toString());
        tvIntroduction.setText(json);

    }

    // （3）将Java对象转换为json字符串{}
    private void javaToJsonObjectByFastJson() {

        // 1 创建一个Java对象
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");

        // 2 生成JSON数据
        String json = JSON.toJSONString(shopInfo);

        // 3 显示数据
        viewIntroduction.setText(shopInfo.toString());
        tvIntroduction.setText(json);
    }


    // （2）将json格式的字符串[]转换为Java对象的List
    private void jsonToJavaListByFastJson() {

        // 1 获取或创建json数据
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
        List<ShopInfo> shopInfos = JSON.parseArray(json, ShopInfo.class);

        // 3 显示数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(shopInfos.toString());
    }

    // （1）将json格式的字符串{}转换为Java对象
    private void jsonToJavaObjectByFastJson() {

        // 1 获取或创建json数据
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";

        // 2 解析JSON数据
        ShopInfo shopInfo = JSON.parseObject(json, ShopInfo.class);

        // 3 显示数据
        viewIntroduction.setText(json);
        tvIntroduction.setText(shopInfo.toString());
    }
}
