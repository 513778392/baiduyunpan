package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import data.AppToast;
import data.SharedPreferencesUtils;


public class BaseActivity extends FragmentActivity {
    public static final int playRequest = 1;
    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */

    private boolean background = false;

    private static ArrayList<BaseActivity> activityList = new ArrayList<BaseActivity>();
    private FrameLayout group;
    private View statusBarView;

    /**
     * 网络类型
     */
    private int netMobile;
    /**
     */
    private LinearLayout mBaseView;
    private View actionBar;
    @Override
    public void setContentView(int layoutResID) {
        mBaseView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.status_main_layout, null);//状态栏
        LayoutInflater.from(this).inflate(layoutResID, mBaseView);  //把状态栏添加到主视图
        setContentView(mBaseView);


    }

    @Override
    public void setContentView(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.setContentView(view);
        initView();//设置状态栏的高度跟样式

    }
    private void initView() {
        //改变statusbar的颜色
        /*actionBar = mBaseView.findViewById(R.id.action_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            actionBar.setVisibility(ViewGroup.VISIBLE);
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,  //设置StatusBar透明
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int id = 0;
            id = getResources().getIdentifier("status_bar_height", "dimen", //获取状态栏的高度
                    "android");
            if (id > 0) {
                actionBar.getLayoutParams().height = getResources() //设置状态栏的高度
                        .getDimensionPixelOffset(id);
            }
        }*/
     /*   if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=19){
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }*/

    }

    @Override
    protected void onStart() {

        super.onStart();

    }



    public void onDestroy() {
        background = true;
        activityList.remove(this);
        super.onDestroy();

    }




  /*  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initStatus();


    }*/


    private void initStatus() {
        group = new FrameLayout(getApplicationContext());
        // 创建一个statusBarView 占位，填充状态栏的区域，以后设置状态栏背景效果其实是设置这个view的背景。
        group.addView(statusBarView = createStatusBar());
        super.setContentView(group, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 设置activity的window布局从状态栏开始
        setTranslucentStatus(true);
        setStatusBarColorBg(R.color.white);


    }


    /**
     * 初始化时判断有没有网络
     */



    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;
        }
        return false;
    }
    /**
     * 设置状态栏的颜色
     *
     * @param color
     */
    public void setStatusBarColorBg(int color) {
        statusBarView.setBackgroundColor(color);
    }

    public void setTranslucentStatus(boolean isTranslucentStatus) {
        if (isTranslucentStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
            statusBarView.setVisibility(View.VISIBLE);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = ((~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) & localLayoutParams.flags);
            }
            statusBarView.setVisibility(View.GONE);
        }
    }

    /**
     * 创建状态栏填充的 statusBarView
     *
     * @return
     */
    private View createStatusBar() {
        int height = getStatusBarHeight();
        View statusBarView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        statusBarView.setBackgroundResource(R.color.white);
        statusBarView.setLayoutParams(lp);
        return statusBarView;
    }

    /**
     * 获取状态的高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
        return 0;
    }

  /*  public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }*/

    /**是否登录成功*/
    public boolean isDenglu (){
        return (boolean)SharedPreferencesUtils.getBean(this,"user");
    }
  public  boolean isBaitian(){

      return (boolean)SharedPreferencesUtils.getBean(this,App.BAITIAN);
  }
  public  void setBai (View v){
      v.setBackgroundResource(R.color.white);
  }
    public  void setHei (View v){
        v.setBackgroundResource(R.color.zitiyanse3);
    }
    public  void setHei1 (View v){
        v.setBackgroundResource(R.color.heiye);
    }
    void setViewBai(View v){
        v.setBackgroundResource(R.color.beiyingd);
    }
    void setViewHei(View v){
        v.setBackgroundResource(R.color.beiyingdd);
    }

    void setListBai(ListView v){
        v.setDivider(new ColorDrawable(getResources().getColor(R.color.beiyingd)));
        v.setDividerHeight(1);
    }
    void setListHei(ListView v){
        v.setDivider(new ColorDrawable(getResources().getColor(R.color.beiyingdd)));
        v.setDividerHeight(1);
    }
    public void setTextBai(TextView v){
        v.setTextColor(App.app.getResources().getColor(R.color.zitiyanse6));
    }
    public void setTextHei(TextView v){
        v.setTextColor(App.app.getResources().getColor(R.color.white));
    }
   /* @Override
    public Resources getResources() {
        Resources resources = super.getResources();
                Configuration configuration = resources.getConfiguration();
                 configuration.setToDefaults();
                 return resources;
             }*/
    /**
     * @param
     * @描述 通过包名启动其他应用，假如应用已经启动了在后台运行，则会将应用切到前台
     * @作者 tll
     * @时间 2017/2/7 17:40
     */
    public  void startActivityForPackage(Context context, String packName) {
        if(isAvilible(context,packName)) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packName);
            context.startActivity(intent);
        }else {
            if(packName.equals("com.baidu.netdisk")){
                AppToast.showToast("请安装百度云盘");
            }else {
                AppToast.showToast("请安装迅雷");
            }
        }
    }
    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

}
