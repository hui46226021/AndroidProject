package com.jrfunclibrary.fileupload;

import android.os.Handler;
import android.os.Message;


import com.jereibaselibrary.application.JrApp;
import com.jereibaselibrary.netowrk.cookie.PersistentCookieStore;
import com.jereibaselibrary.tools.JRLogUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import okhttp3.Cookie;


/**
 * Created by zhush on 2016/10/9.
 * E-mail zhush@jerei.com
 */
public class FileUpload {
    private static final String TAG = "uploadFile";

    int TIME_OUT = 30 * 1000; // 超时时间

    String CHARSET = "utf-8"; // 设置编码

    String CONTENT_TYPE = "image/png"; // 内容类型
    String PREFIX = "--", LINE_END = "\r\n";
    String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成

    private FileUploadListener fileUploadListener;

    private int progress;

    private static final int SUCCESS = 1;

    private static final int PROGRESSVALUE = 0;

    private static final int FAIL = 2;
    String result = null;
    int fileSize = 0;

    String RequestURL;


    public FileUpload(String requestURL) {
        RequestURL = requestURL;
    }

    /**
     * Android上传文件到服务端
     *
     * @return 返回响应的内容
     */
    public String uploadFile(final File file, FileUploadListener fileUploadListener) {

        this.fileUploadListener = fileUploadListener;



        fileSize = (int) file.length();
        RequestURL = RequestURL + "?uploadFileName=" + file.getName();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(RequestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(TIME_OUT);
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setDoInput(true); // 允许输入流
                    conn.setDoOutput(true); // 允许输出流
                    conn.setUseCaches(false); // 不允许使用缓存
                    conn.setRequestMethod("POST"); // 请求方式
                    conn.setRequestProperty("Charset", CHARSET); // 设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                    //同步http cookie
                    PersistentCookieStore store = new PersistentCookieStore(JrApp.getContext());
                    for(Cookie cookie:store.getCookies()){
                        conn.setRequestProperty("Cookie",cookie.name()+"="+cookie.value());
                    }


                    if (file != null) {
                        /**
                         * 当文件不为空，把文件包装并且上传
                         */
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                        InputStream is = new FileInputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;

                        int lll = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                            lll = lll + len;
                            //更新进度
                            progress = (int) ((((double) lll) / fileSize) * 100);
                            Message message = new Message();
                            message.what = PROGRESSVALUE;
                            handler.sendMessage(message);
                        }
                        is.close();
                        dos.flush();


                        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                        StringBuilder resultData = new StringBuilder("");
                        //使用缓冲一行行的读入，加速InputStreamReader的速度
                        BufferedReader buffer = new BufferedReader(isr);
                        String inputLine = null;

                        while ((inputLine = buffer.readLine()) != null) {
                            resultData.append(inputLine);
                            resultData.append("\n");
                        }
                        buffer.close();
                        isr.close();
                        result = resultData.toString();
                        JRLogUtils.i(TAG, "result : " + result);



                        /**
                         * 获取响应码 200=成功 当响应成功，获取响应的流
                         */
                        int res = conn.getResponseCode();

                        if (res == 200) {
                            Message message = new Message();
                            message.what = SUCCESS;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = FAIL;
                            handler.sendMessage(message);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return result;
    }


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SUCCESS:
                    fileUploadListener.uploadFileSuccess(result);
                    break;
                case FAIL:
                    fileUploadListener.uploadFileFail(result);
                    break;
                case PROGRESSVALUE:
                    fileUploadListener.fileUploadProgress(progress);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public interface FileUploadListener {
        /**
         * 图片上传进度
         *
         * @param pro
         */
        public void fileUploadProgress(int pro);

        /**
         * 上传图片成功
         */
        public void uploadFileSuccess(String responseStr);

        /**
         * 上传图片失败
         */
        public void uploadFileFail(String resultStr);
    }

    ;

}
