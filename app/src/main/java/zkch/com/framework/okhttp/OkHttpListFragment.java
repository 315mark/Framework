package zkch.com.framework.okhttp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zkch.com.framework.MyApp;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseFragment;

public class OkHttpListFragment extends BaseFragment {

    @BindView(R.id.btn_get_post)
    Button btnGetPost;
    @BindView(R.id.btn_get_okhttputils)
    Button btnGetOkhttputils;
    @BindView(R.id.btn_downloadfile)
    Button btnDownloadfile;
    @BindView(R.id.btn_uploadfile)
    Button btnUploadfile;
    @BindView(R.id.btn_image)
    Button btnImage;
    @BindView(R.id.btn_image_list)
    Button btnImageList;
    @BindView(R.id.tv_result)
    ExpandableTextView tvResult;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    //    ExpandableTextView   下拉扩展TextView 必须按照模式写  否则 NullPointerException

    private OkHttpClient client = new OkHttpClient();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    //获取数据
                    tvResult.setText((String) msg.obj);
                    break;
                case POST:
                    //获取数据
                    tvResult.setText((String) msg.obj);
                    break;
            }
        }
    };

    //mdiatype 须和服务端保持一致
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    /**
     * get请求
     */
    private static final int GET = 1;
    /**
     * post请求
     */
    private static final int POST = 2;


    /**
     * protected  修饰指继承该类可执行方法
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_okhttp_list;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btn_get_post, R.id.btn_get_okhttputils, R.id.btn_downloadfile, R.id.btn_uploadfile, R.id.btn_image, R.id.btn_image_list})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_post://使用原生的okhttp请求网络数据，get和post
                tvResult.setText("");
                getDataFromPost();//点击事件
                break;
            case R.id.btn_get_okhttputils:
//                getDataGetByOkhttpUtils();
                getDataPostByOkhttpUtils();
                break;
            case R.id.btn_downloadfile://下载文件
                downloadFile();
                break;
            case R.id.btn_uploadfile://文件上传
                //代码有问题
                //multiFileUpload();
                break;
            case R.id.btn_image://请求单张图片
                getImage();
                break;
            case R.id.btn_image_list://请求列表中的图片

                FragmentManager mManager = getFragmentManager();
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.addToBackStack(null);//这里将我们的Fragment加入到返回栈
                ft1.replace(R.id.okhttp_content, new OkHttpFragment());
                ft1.commit();
                break;
        }
    }

    /**
     * 使用get请求网络数据
     */
    private void getDataFromGet() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    Log.e("TAG", result);
                    Message msg = Message.obtain();
                    msg.what = GET;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    /**
     * 使用post请求网络数据
     */
    private void getDataFromPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    RequestBody body = RequestBody.create(JSON, "");
                    String result = post("http://api.m.mtime.cn/PageSubArea/TrailerList.api", body);
                    Log.e("TAG", result);
                    Message msg = Message.obtain();
                    msg.what = POST;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * get请求
     *
     * @param url 网络连接
     * @return
     * @throws IOException
     */
    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    /**
     * okhttp3的post请求
     *
     * @return
     * @throws IOException
     */
    private String post(String url, RequestBody body) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 使用okhttp-utils的post请求网络文本数据
     */
    public void getDataPostByOkhttpUtils() {
        String url;
//        url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        Request request = new Request.Builder().url(url).get().build();
        inithttp(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                tvResult.setText(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tvResult.setText(response.body().string());
            }
        });
    }

    /**
     * 使用okhttp-utils下载大文件
     */
    public void downloadFile() {
        String url = "http://vfx.mtime.cn/Video/2016/07/24/mp4/160724055620533327_480.mp4";
        Request request = new Request.Builder().url(url).get().build();
        inithttp(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                //储存下载文件的目录
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, "okhttp-file.mp4");

                try {

                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中更新进度条

                    }
                    fos.flush();
                    //下载完成
                    //  listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    //  listener.onDownloadFailed(e);
                } finally {

                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }

                }

            }
        });
    }


    public interface OnDownloadListener {

        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载异常信息
         */

        void onDownloadFailed(Exception e);
    }


    /**
     * 使用okhttp-utils上传多个或者单个文件
     */
    public void multiFileUpload() {
        String mBaseUrl = "http://192.168.0.165:8080/FileUpload/FileUploadServlet";
        File file = new File(Environment.getExternalStorageDirectory(), "afu.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test.txt");
        if (!file.exists() || !file2.exists()) {
            Toast.makeText(MyApp.getContext(), "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("username", "杨光福");
        params.put("password", "123");

        String url = mBaseUrl;
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, file);
        Request request = new Request.Builder().url(url).post(body).build();
        inithttp(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tvResult.setText(response.body().string());
            }
        });

    }

    public void getImage() {
        tvResult.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";

        Request request = new Request.Builder().url(url).get().build();
        inithttp(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                tvResult.setText("onError:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                ivIcon.setImageBitmap(bitmap);
            }
        });
    }

}
