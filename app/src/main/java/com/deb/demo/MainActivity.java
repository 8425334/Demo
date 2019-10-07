package com.deb.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    List<String> banners;
    Banner banner;
    List<String> urls;

    Handler bannerHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case 0 :
                    Toast.makeText(mContext, "暂无Banner", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    for (MBanner banner : (List<MBanner>)message.obj){
                        banners.add(banner.getBanner_url());
                        urls.add(banner.getDown_url());
                    }
                    banner.setImages(banners).setImageLoader(new GlideImageLoader()).start();
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBanner();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initData();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initBanner(){
        banners = new ArrayList<>();
        urls = new ArrayList<>();
        banner = (Banner) findViewById(R.id.banner);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(mContext, GoWeb.class);
                intent.putExtra("url", urls.get(position));
                startActivity(intent);
            }
        });
    }

    private void initData() throws JSONException, IOException {
        mContext = this;
        String bannerJson = HttpUtil.getBannerJson();
        JSONObject jsonObject = new JSONObject(bannerJson);
        Message message = new Message();
        int code = jsonObject.getInt("code");
        if (code != 200){
            message.what = 0;
            bannerHandle.sendMessage(message);
            return ;
        }
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray bannerArray = dataObject.getJSONArray("images");
        List<MBanner> list = new ArrayList<>();

        for (int i = 0; i < bannerArray.length(); i++){
            JSONObject banner = (JSONObject) bannerArray.get(i);
            String banner_url = banner.getString("banner_url");
            String down_url = banner.getString("down_url");
            list.add(new MBanner(banner_url, down_url));
        }

        message.what = 1;
        message.obj = list;
        bannerHandle.sendMessage(message);
    }
}
