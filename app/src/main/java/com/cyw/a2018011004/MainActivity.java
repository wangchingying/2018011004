package com.cyw.a2018011004;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

//在manifests新增一行開啟網路權限<uses-permission android:name="android.permission.INTERNET" />
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click1(View v)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String str_url = "http://rate.bot.com.tw/xrt?Lang=zh-TW";
                URL url = null;
                try {
                    url = new URL(str_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String str;
                    //一行一行讀,在append起來
                    while ((str = br.readLine()) != null)
                    {
                        sb.append(str);
                    }
                    String str1 = sb.toString();
                    Log.d("NET", str1);
                    int index1 = str1.indexOf("日圓 (JPY)");//抓到"日圓 (JPY)"的位置(第幾個字?)
                    int index2 = str1.indexOf("本行現金賣出", index1);//從index1後面抓到""本行現金賣出"的位置
                    int index3 = str1.indexOf("0.266", index2);//從index2後面抓到"0.266"的位置
                    Log.d("NET", "index1:" + index1 + "index2:" + index2 + "index3:" + index3);
                    String data1 = str1.substring(index2+56, index2+61);//從index2後面抓56~61個字,就抓到目標,但若目標會改變長度,就必須判斷HTML > <的符號
                    Log.d("NET", data1);
                    br.close();
                    isr.close();
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

}
