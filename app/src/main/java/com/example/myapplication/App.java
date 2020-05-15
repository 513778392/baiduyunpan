package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;
import data.AppToast;
import data.DataUtile;
import data.LogUtil;
import data.SharedPreferencesUtils;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class App extends Application {
    public static App app = null;
    public static Typeface typeFace;
    public  boolean isheiye;

    public static String BAITIAN="isbaitian";
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //setTypeface();
        isheiye = DataUtile.isInDate(new Date(),"07:00:00","18:00:00");
        LogUtil.v(isheiye+"=======================");
        SharedPreferencesUtils.putBean(this,BAITIAN, isheiye);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        AppToast.init(this);
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
     /*   CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        Bugly.init(this, "74f7064c51", false, strategy);*/


    }
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    private void setTypeface(){
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        try
        {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, typeFace);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }


}
