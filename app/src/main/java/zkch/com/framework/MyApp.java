package zkch.com.framework;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import java.io.File;

import zkch.com.framework.utils.LogcatHelper;

public class MyApp extends Application {
    private static Context sContext;
    /**日志文件保存路径*/
    public static String PATH_LOGCAT ;

    private String Filepath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "logcat";

    //如不继承MultiDexApplication 或 不在Manifest.xml指定Application为MultiDexApplication 执行如下操作
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//解决65536问题 先onCreate执行
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=this;
        //CrashHandler.getInstance().init(this);
        PATH_LOGCAT = sContext.getFilesDir().getAbsolutePath() + File.separator + "logcat";
        //在应用中start LogcatHelper
        LogcatHelper.getInstance(this).start();
    }

    public static Context getContext()
    {
        return sContext;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        //在应用中stop LogcatHelper
        LogcatHelper.getInstance(this).stop();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
