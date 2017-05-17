package com.liqi.okhttpdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient okHttpClient = new OkHttpClient();
    private TextView mTextVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextVIew = (TextView) findViewById(R.id.id_tv_result);
    }

    public void doPost(View view){
    }

    public void doGet(View view){
        //1.拿到okHttpClient对象
//        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("https://www.baidu.com/").build();

        //3.将Request封装为Call
        Call call = okHttpClient.newCall(request);


        //4.执行call
//        Response response =  call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("onResponse: ");
                final String res = response.body().string();
                L.e(res);

//                InputStream is =  response.body().byteStream();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextVIew.setText(res);
                    }
                });

                //----------------------------------------------

                InputStream is = response.body().byteStream();

                Bitmap bitmap = BitmapFactory.decodeStream(is);

                int len = 0;
                File file = new File(Environment.getExternalStorageDirectory(),"a.jpg");
                byte[] buf = new byte[128];
                FileOutputStream fos = new FileOutputStream(file);

                while ((len = is.read(buf)) != -1){
                    fos.write(buf,0,len);
                }

                fos.flush();
                fos.close();
                is.close();
            }
        });
    }

    public void doPostString(View view){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),"{a:b}");
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://www.baidu.com").post(requestBody).build();

        executeRequest(request);

        //server

        InputStream is = new BufferedInputStream();

        StringBuilder sb = new StringBuilder();

        int len = 0;
        byte[] buf = new byte[1024];

        while ((len = is.read(buf)) != -1){
            sb.append(new String(buf,0,len));
        }

        sb.toString();

    }

    public void doPostFile(View view) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(),"banner2.jpg");
        if(!file.exists()){
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-steam"),file);
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://www.baidu.com").post(requestBody).build();

        executeRequest(request);

        //server

        InputStream is = new BufferedInputStream();

        String dir = "";
        File file = new File(dir,"banner2.jpg");

        FileOutputStream fos = new FileOutputStream(file);

        int len = 0;
        byte[] buf = new byte[1024];

        while ((len = is.read(buf)) != -1){
            fos.write(buf,0,len);
        }

        fos.flush();
        fos.close();
    }
}
