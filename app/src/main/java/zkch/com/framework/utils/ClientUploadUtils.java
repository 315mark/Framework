package zkch.com.framework.utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static zkch.com.framework.MyApp.PATH_LOGCAT;

public class ClientUploadUtils {

    public static void postFile() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.指定文件的类型 image/jpg image/png video/mp4 ...mimeType
        MediaType mediaType = MediaType.parse("text/x-markdown;charset=utf-8");
        String url = "https://oapi.dingtalk.com/robot/send?access_token=f643096ff2a22ebb10ccf77f074e9b2051da533d2c19cab045e7a291a307fcea";
        //3.指定要上传的文件对象
        File file = new File(PATH_LOGCAT, "logcat.txt");
        Request request = new Request.Builder()
                //上传文件的时候请求体使用RequestBody.create()获取okhttp3.MediaType contentType
                // 文件的类型,@NotNull java.io.File file上传的文件对象
                .addHeader("Connection", "close")
                .post(RequestBody.create(mediaType, file))
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            }
        });
    }


    static ResponseBody upload(File file) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();

        String url = "https://oapi.dingtalk.com/robot/send?access_token=f643096ff2a22ebb10ccf77f074e9b2051da533d2c19cab045e7a291a307fcea";
        Request request = new Request.Builder()

                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body();
    }
}
