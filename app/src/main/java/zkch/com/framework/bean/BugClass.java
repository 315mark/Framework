package zkch.com.framework.bean;

import android.util.Log;

public class BugClass {

    public String bug() {
        // 这段代码会报空指针异常
        String str = null;
        Log.e("BugClass", "get string length:" + str.length());
        return "This is bug class";
        //        return "我修改了很多资源文件";
    }
}
