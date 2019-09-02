package zkch.com.framework.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import zkch.com.framework.R;

//Lottie 动画
public class RefreshLottieHeader extends LinearLayout implements RefreshHeader {

    LottieAnimationView lottieView;

    public RefreshLottieHeader(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = Inflater.inflate(R.layout.loading_lottie, this);
        lottieView = view.findViewById(R.id.loading_lottie);
    }

    //animation  lottie文件名
    public void setAnimationViewJson(String animation) {
        lottieView.setAnimation(animation);
    }

    //anim the Animation
    public void setAnimationViewJson(Animation animation) {
        lottieView.setAnimation(animation);
    }

    /**
     * 开始动画
     *
     * @param refreshLayout RefreshLayout
     * @param height        HeaderHeight or FooterHeight
     * @param extendHeight  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
        lottieView.playAnimation();
    }

    /**
     * 动画结束
     *
     * @param refreshLayout RefreshLayout
     * @param success       数据是否成功刷新或加载
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        lottieView.cancelAnimation();
        return 0;
    }

    /**
     * 获取实体视图
     *
     * @return 实体视图
     */
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    /**
     * 获取变换方式 {@link SpinnerStyle} 必须返回 非空
     *
     * @return 变换方式
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    /**
     * 设置主题颜色
     *
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Override
    public void setPrimaryColors(int... colors) {

    }

    /**
     * 尺寸定义完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     *
     * @param kernel       RefreshKernel
     * @param height       HeaderHeight or FooterHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    /**
     * 手指拖动下拉（会连续多次调用）
     *
     * @param percent      下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+extendHeight) / footerHeight )
     * @param offset       下拉的像素偏移量  0 - offset - (footerHeight+extendHeight)
     * @param height       高度 HeaderHeight or FooterHeight
     * @param extendHeight 扩展高度  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    /**
     * 手指释放之后的持续动画（会连续多次调用）
     *
     * @param percent      下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+extendHeight) / footerHeight )
     * @param offset       下拉的像素偏移量  0 - offset - (footerHeight+extendHeight)
     * @param height       高度 HeaderHeight or FooterHeight
     * @param extendHeight 扩展高度  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    /**
     * 释放时刻（调用一次，将会触发加载）
     *
     * @param refreshLayout RefreshLayout
     * @param height        高度 HeaderHeight or FooterHeight
     * @param extendHeight  扩展高度  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    /**
     * 水平方向的拖动
     *
     * @param percentX  下拉时，手指水平坐标对屏幕的占比（0 - percentX - 1）
     * @param offsetX   下拉时，手指水平坐标对屏幕的偏移（0 - offsetX - LayoutWidth）
     * @param offsetMax 最大的偏移量
     */
    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    /**
     * 是否支持水平方向的拖动（将会影响到onHorizontalDrag的调用）
     *
     * @return 水平拖动需要消耗更多的时间和资源，所以如果不支持请返回false
     */
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    /**
     * 状态改变事件 {@link RefreshState}
     *
     * @param refreshLayout RefreshLayout
     * @param oldState      改变之前的状态
     * @param newState      改变之后的状态
     */
    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }
}
