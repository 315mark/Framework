package zkch.com.framework;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 发送异常日志到钉钉群
 */
public class HttpPost {
    private static String url = "https://oapi.dingtalk.com/robot/send?access_token=ac0a5710d9e1a85f00e5d9ea9e17fa3727f224da64c32966f830d25a0d885641";
    private static final String nextLine = "\r\n";
    private static final String twoHyphens = "--";
    //分割线  随便写一个
    private static final String boundary = "wk_file_2519775";

    private static String Filepath= Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * 生成http连接
     * @param method http请求方式
     * @return httpURLConnection
     * @throws IOException 连接生成失败
     */
    private static HttpURLConnection createConnection(String urlPath, String method) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);

        httpURLConnection.setRequestProperty("Charsert", "UTF-8");
        return httpURLConnection;
    }

    /**
     * 多文件上传
     * @param files 文件
     */
    public static void uploadFiles(File[] files){
        for (File file : files){
           // uploadFile(file);
        }
    }


    /**
     * 上传文件
     * @param file 文件
     */
    public static void uploadFile(String file){
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            //获取HTTPURLConnection连接
            connection = createConnection(url, "POST");
            //运行写入默认为false，置为true
            connection.setDoOutput(true);
            //禁用缓存
            connection.setUseCaches(false);
            //设置接收编码
            connection.setRequestProperty("Accept-Charset", "utf-8");
            //开启长连接可以持续传输
            connection.setRequestProperty("Connection", "keep-alive");
            //设置请求参数格式以及boundary分割线
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            //设置接收返回值的格式
            connection.setRequestProperty("Accept", "application/json");
            //开启连接
            connection.connect();

            //获取http写入流
            outputStream = new DataOutputStream(connection.getOutputStream());

            //分隔符头部
            String header = twoHyphens + boundary + nextLine;
            //分隔符参数设置
            header += "Content-Disposition: form-data;name=\"file\";" + "filename=\"" + "\"" + nextLine + nextLine;
            //写入输出流
            outputStream.write(header.getBytes());
            File txtfile = new File(Filepath + File.separator +file);

            //读取文件并写入
            inputStream = new FileInputStream(txtfile);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes))!= -1){
                outputStream.write(bytes, 0, length);
            }
            //文件写入完成后加回车
            outputStream.write(nextLine.getBytes());

            //写入结束分隔符
            String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
            outputStream.write(footer.getBytes());
            outputStream.flush();
            //文件上传完成
            InputStream response = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(response);
            while (reader.read() != -1){
                Log.i("uploadFile: ",new String(bytes, "UTF-8"));
            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Log.i("uploadFile: ",connection.getResponseMessage());
            }else {
                Log.i("uploadFile: ","上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null){
                    outputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
                if (connection != null){
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
