    记录框架使用指南:
    1.SmartRefresh结合Lottie实现一行代码切换下拉刷新动画

    Lottie 详解   ----->>>>>>>
    Airbnb 开源的一款动画库，该库的优势是不需要程序员自己写 Anim
    而是将这些工作交给设计师处理，最后拿到到只是一个 Json 文件，
    通过 Lottie 加载 Json 文件就能展示出动画效果。
    Json 文件需要放在 main 目录的 assets 中,然后就可以在布局或者代码中直接使用

    <com.airbnb.lottie.LottieAnimationView
            android:id="@+id/loading_lottie"
            android:layout_width="wrap_content"
            android:layout_height="100dp"
            android:layout_gravity="center_horizontal"
            app:lottie_fileName="lottie.json"/>

    //开始播放动画
    mAnimationView.playAnimation();
    //取消播放
    mAnimationView.cancelAnimation();

    SmartRefresh结合Lottie 使用须自定义RefreshHeader 类添加以下方法:

    //初始化View
     private void initView(Context context) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.loading_lottie, this);
            mAnimationView = (LottieAnimationView) view.findViewById(R.id.loading_lottie);

        }
        //开始刷新
        @Override
        public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
            mAnimationView.playAnimation();
        }
        //结束刷新
        @Override
        public int onFinish(RefreshLayout layout, boolean success) {
            mAnimationView.cancelAnimation();
            return 0;
        }

    在自定义 SmartRefresh 中提供 Lottie 动画切换的方法
    public void setAnimationViewJson(String animName){
            mAnimationView.setAnimation(animName);
    }

    public void setAnimationViewJson(Animation anim){
            mAnimationView.setAnimation(anim);
    }

    一行代码切换刷新动画
    mRefreshLottieHeader.setAnimationViewJson("lottie.json");

    二 ---->>>RxJava  OkHttp  Retrofit 封装
    
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'//导入retrofit
    //下面两个是RxJava 和 RxAndroid
    implementation "com.squareup.okhttp3:logging-interceptor:3.9.1"
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
    implementation 'io.reactivex.rxjava2:rxjava:2.1.11'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'//转换器，请求结果转换成Model
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'//配合Rxjava 使用
    
 Retrofit  负责请求数据和请求结果  使用接口的方式呈现
 okHttp  负责请求过程  rxJava负责异步以及各种线程切换


三. 腾讯Bugly 热更新 注意问题
    1. tinker-support.gradle  baseApkDir 文件夹 以后每次生成的base


四. 讯飞语音合成播报
    1.登陆讯飞官网 注册应用 生成appid 下载sdk


五. 微信 支付宝接入
    1. 微信语音合成  --->>>

