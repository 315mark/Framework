package zkch.com.framework.mail;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static zkch.com.framework.MyApp.PATH_LOGCAT;

public class SendMailUtil{
    //qq
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    private static final String FROM_ADD = "teprinciple@foxmail.com";
    private static final String FROM_PSW = "lfrlpganzjrwbeci";

//    //163
//    private static final String HOST = "smtp.163.com";
//    private static final String PORT = "465"; //或者465  994
//    private static final String FROM_ADD = "teprinciple@163.com";
//    private static final String FROM_PSW = "teprinciple163";
////    private static final String TO_ADD = "2584770373@qq.com";


    public static void send(final File file, String toAdd){
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,file);
            }
        }).start();
    }


    public static void send(String toAdd){
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    @NonNull
    private static MailInfo creatMail(String toAdd) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject("Hello"); // 邮件主题
        mailInfo.setContent("Android 测试"); // 邮件文本
        return mailInfo;
    }

    public static void sendFileMail() {
        File file = new File(PATH_LOGCAT + "logcat.txt");
        OutputStream os = null;
        FileInputStream inputStream = null;
        String nextLine = "\r\n";
        String twoHyphens = "--";
        //分割线  随便写一个
        String boundary = "wk_file_2519775";
        try {
            //分隔符头部
            String header = twoHyphens + boundary + nextLine;
            //分隔符参数设置
            header += "Content-Disposition: form-data;name=\"file\";" + "filename=\"" + "\"" + nextLine + nextLine;
            //写入输出流
            os.write(header.getBytes());

            //读取文件并写入
            inputStream = new FileInputStream(file);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];

            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                os.write(bytes, 0, length);
            }
            //文件写入完成后加回车
            os.write(nextLine.getBytes());
            String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
            os.write(footer.getBytes());
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
            }
        }
        SendMailUtil.send(file, "发送邮件地址");
    }

}
