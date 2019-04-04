package zkch.com.framework.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static zkch.com.framework.MyApp.PATH_LOGCAT;

/**
 * Created by yk on 2018/4/17
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    // 系统默认的UncaughtException处理类  
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例  
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象  
    private Context mContext;
    // 用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<>();

    // 用于格式化日期,作为日志文件名的一部分  
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private String nameString="fastpinAFC";


    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序  
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        final String filename= saveCrashInfo2File(ex);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                // 保存日志文件
                File file=new File(PATH_LOGCAT);
                File[] files=file.listFiles();
                if (files != null ){
                    HttpPost.uploadFiles(files);
                }
                Looper.loop();
            }
        }.start();

       return true;
    }


    /**
     * 保存错误信息到文件中
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public String saveCrashInfo2File(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        
        sb.append(result);
        try {
            String time = formatter.format(new Date());
            fileName = nameString + "-" + time  +".log";
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File dir = new File(PATH_LOGCAT);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(PATH_LOGCAT + File.separator + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return fileName;
    }
    

    /**
     * 文件删除 
     * @param autoClearDay 文件保存天数 
     */
    /*public void autoClear(final int autoClearDay) {
        FileUtil.delete(Filepath, new FilenameFilter() {
            @Override
            public boolean accept(File file, String filename) {
                String s = FileUtil.getFileNameWithoutExtension(filename);
                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                String date = "crash-" + DateUtil.getOtherDay(day);
                return date.compareTo(s) >= 0;
            }
        });
    }*/

} 
