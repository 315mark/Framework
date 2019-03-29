package zkch.com.framework;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=this;
        CrashHandler.getInstance().init(this);
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
