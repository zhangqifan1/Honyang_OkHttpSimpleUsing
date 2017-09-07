package com.example.anadministrator.honyang_okhttpsimpleusing;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * GET请求
     */
    private Button mGetRequest;
    /**
     * POST请求
     */
    private Button mPostRequest;
    /**
     * 本地缓存
     */
    private Button mLocalCache;
    /**
     * 下载图片
     */
    private Button mImageDown;
    /**
     * 上传图片
     */
    private Button mImageUp;

    String path = "http://blog.csdn.net/lmj623565791/article/details/49734867/";
    String pathImage = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1504679605&di=4c33eccca43a1188bc00809af1b89081&src=http://file06.16sucai.com/2016/0315/3b3f0fa6fdd82f47e10137dde83fba0e.jpg";

    String PostPath = "http://169.254.53.96:8080/web/LoginServlet";
    private ImageView mImage;

    //OkHttp 使用hongyang依赖库的 简单请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        mGetRequest = (Button) findViewById(R.id.GetRequest);
        mGetRequest.setOnClickListener(this);
        mPostRequest = (Button) findViewById(R.id.PostRequest);
        mPostRequest.setOnClickListener(this);
        mLocalCache = (Button) findViewById(R.id.LocalCache);
        mLocalCache.setOnClickListener(this);
        mImageDown = (Button) findViewById(R.id.ImageDown);
        mImageDown.setOnClickListener(this);
        mImageUp = (Button) findViewById(R.id.ImageUp);
        mImageUp.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.Image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.GetRequest:
                GRequest();
                break;
            case R.id.PostRequest:
                PRequest();
                break;
            case R.id.LocalCache:
                LCache();
                break;
            case R.id.ImageDown:
                IDown();
                break;
            case R.id.ImageUp:
                IUp();
                break;
        }
    }

    private void IUp() {
        OkHttpUtils
                .postFile()
                .url("")
//                .file(file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    private void IDown() {
        OkHttpUtils
                .get()
                .url(pathImage)
                .build()
                .execute(new BitmapCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        mImage.setImageBitmap(response);
                        File file=new File(Environment.getExternalStorageDirectory().getPath(),"A.jpg");
                        if(!file.exists()){
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            response.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));//压缩  保存
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void LCache() {
        OkHttpUtils
                .get()
                .url(path)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getPath(), "b.text") {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        System.out.println("OK");
                    }
                });
    }

    private void PRequest() {
        OkHttpUtils
                .post()
                .url(PostPath)
                .addParams("qq", "10000")
                .addParams("pwd", "abcde")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        System.out.println("00000000000000000000");
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("1111111111111111");
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        System.out.println("222222222222222");
                    }
                });
    }

    private void GRequest() {
        OkHttpUtils
                .get()
                .url(path)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String s = response.toString();
                        System.out.println(s);
                    }
                });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}