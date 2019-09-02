package zkch.com.framework.bugly;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;
import zkch.com.framework.R;
import zkch.com.framework.bean.LoadBugClass;
import zkch.com.framework.common.base.BaseActivity;
import zkch.com.framework.utils.SnackbarUtil;
import zkch.com.framework.utils.ToastUtil;


public class TinkerActivity extends BaseActivity {

    @BindView(R.id.tvCurrentVersion)
    TextView tvCurrentVersion;
    @BindView(R.id.btnShowToast)
    Button btnShowToast;
    @BindView(R.id.btnLoadPatch)
    Button btnLoadPatch;
    @BindView(R.id.btnLoadLibrary)
    Button btnLoadLibrary;
    @BindView(R.id.btnDownloadPatch)
    Button btnDownloadPatch;
    @BindView(R.id.btnPatchDownloaded)
    Button btnPatchDownloaded;
    @BindView(R.id.btnCheckUpgrade)
    Button btnCheckUpgrade;
    @BindView(R.id.btnKillSelf)
    Button btnKillSelf;

    @Override
    protected void initMvp() {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void initView() {
        tvCurrentVersion.setText("当前版本：" + getCurrentVersion(this));
    }


    @Override
    protected void initDestroy() {

    }

    @OnClick({R.id.btnShowToast, R.id.btnLoadPatch, R.id.btnLoadLibrary, R.id.btnDownloadPatch, R.id.btnPatchDownloaded, R.id.btnCheckUpgrade, R.id.btnKillSelf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnShowToast:
                //测试热更新功能
                SnackbarUtil.ShortSnackbar(view, LoadBugClass.getBugString(),SnackbarUtil.Info).show();
                break;

            case R.id.btnLoadPatch:
                //杀死进程
                Process.killProcess(Process.myPid());
                break;

            case R.id.btnLoadLibrary:
                //加载本地补丁
                Beta.applyTinkerPatch(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
                break;

            case R.id.btnDownloadPatch:
                //加载本地so库测试
                SnackbarUtil.ShortSnackbar(view,"失败",SnackbarUtil.Alert).setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.showShort("重试");
                    }
                })
                .setActionTextColor(Color.parseColor("#ffffff")).show();
                break;

            case R.id.btnPatchDownloaded:
                Beta.downloadPatch();
                break;

            case R.id.btnCheckUpgrade:
                Beta.applyDownloadedPatch();
                break;

            case R.id.btnKillSelf:
                Beta.checkUpgrade();
                break;
        }
    }


    /** 获取当前版本 */
    private String getCurrentVersion(Context context) {
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(this.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            return versionName + "." + versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
