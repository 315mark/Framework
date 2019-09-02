package zkch.com.framework.utils;

import android.widget.Toast;
import zkch.com.framework.MyApp;

public class ToastUtil {

    private static Toast sToast;

    public static void showShort(int id)
    {
        showShort(UiUtil.getString(id));
    }

    public static void showShort(final String text)
    {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void showLong(int id)
    {
        showLong(UiUtil.getString(id));
    }

    public static void showLong(String text)
    {
        show(text, Toast.LENGTH_LONG);
    }

    private static void show(final String text, final int duration)
    {
        if (sToast != null)
        {
            showToast(text, duration);
        }
        else
        {
            if (UiUtil.isRunOnUiThread())
            {
                createToast();
                showToast(text, duration);
            }
            else
            {
                UiUtil.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        createToast();
                        showToast(text, duration);
                    }
                });
            }
        }
    }

    private static void createToast()
    {
        sToast = Toast.makeText(MyApp.getContext(), null, Toast.LENGTH_SHORT);
    }

    private static void showToast(String text, int duration)
    {
        sToast.setDuration(duration);
        sToast.setText(text);
        sToast.show();
    }
}
