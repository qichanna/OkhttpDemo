package com.liqi.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextVIew = (TextView) findViewById(R.id.id_tv_result);
    }

    public void doGet(View view){
        //1.拿到okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

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
            }
        });
    }
}
