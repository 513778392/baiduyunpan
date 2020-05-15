package service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.myapplication.App;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import data.DataUtile;
import data.LogUtil;
import data.SharedPreferencesUtils;
import okhttp.OkHttpData;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class AppService extends Service {

    public static final String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();
        handler.postDelayed(runnabl,time);
        Log.d(TAG, "onCreate() executed");

    }
    int time = 60000;
    Handler handler = new Handler();
    Runnable runnabl = new Runnable(){
        @Override
        public void run() {
            isheiye = (boolean) SharedPreferencesUtils.getBean(App.app,App.BAITIAN);
            isIsheiye = DataUtile.isInDate(new Date(),"07:00:00","18:00:00");
            OkHttpData.getCheck();
            LogUtil.v(isheiye+"============"+isIsheiye);
            if(isheiye != isIsheiye){
                SharedPreferencesUtils.putBean(App.app,App.BAITIAN,isIsheiye);
                BaiTian baitian = new BaiTian();
                baitian.setBaitian(isIsheiye);
                EventBus.getDefault().post(baitian);
            }
            handler.postDelayed(this,time);
        }


    };

    boolean isheiye,isIsheiye;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    // IBinder是远程对象的基本接口，是为高性能而设计的轻量级远程调用机制的核心部分。但它不仅用于远程
    // 调用，也用于进程内调用。这个接口定义了与远程对象交互的协议。
    // 不要直接实现这个接口，而应该从Binder派生。
    // Binder类已实现了IBinder接口
  public   class MyBinder extends Binder {
        /** * 获取Service的方法 * @return 返回PlayerService */
        public AppService getService(){
            return AppService.this;
        }
    }


}
