package zkch.com.framework.bugly;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import butterknife.BindView;
import butterknife.OnClick;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseActivity;

public class UpgradeActivity extends BaseActivity {

    @BindView(R.id.btnCheckUpdate)
    Button btnCheckUpdate;
    @BindView(R.id.btnLoadUpdateInfo)
    Button btnLoadUpdateInfo;
    @BindView(R.id.tvUpgradeInfo)
    TextView tvUpgradeInfo;

    @Override
    protected void initMvp() {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_upgrade;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDestroy() {

    }

    @OnClick({R.id.btnCheckUpdate, R.id.btnLoadUpdateInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCheckUpdate:
                Beta.checkUpgrade();
                break;
            case R.id.btnLoadUpdateInfo:
                LoadUpdateInfo();
                break;
        }
    }

    private void LoadUpdateInfo() {
        if (tvUpgradeInfo == null) {
            return;
        }
        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            tvUpgradeInfo.setText("无升级信息");
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("id: ").append(upgradeInfo.id).append("\n");
        info.append("标题: ").append(upgradeInfo.title).append("\n");
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n");
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n");
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n");
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n");
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n");
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n");
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n");
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType);

        tvUpgradeInfo.setText(info);

    }
}
