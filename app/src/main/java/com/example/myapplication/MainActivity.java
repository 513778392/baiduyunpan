package com.example.myapplication;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import data.JavaScript;
import data.LogUtil;
import data.SharedPreferencesUtils;
import data.YongHuBiao;
import entity.Douban;
import entity.TuiSongXiaoXi;
import fragment.TanSuoFragment;
import fragment.WenJianFragment;
import fragment.WoDeFragment;
import jiguang.ExampleUtil;
import jiguang.LocalBroadcastManager;
import okhttp.OkHttpData;
import service.AppService;
import service.BaiTian;
import view.MLImageView;
import yunpandata.BaiDuData;
import yunpandata.ListData;
import yunpandata.YunDengLu;


public class MainActivity extends BaseActivity {
    @Bind(R.id.wode)
    ImageView wode;
    @Bind(R.id.tansuo)
    ImageView tansuo;
    @Bind(R.id.wenjian)
    ImageView wenjian;
    int TANSUO = 1;
    int WENJIAN = 2;
    int WODE = 3;
    int i = 0;
    @OnClick({R.id.tansuo_relative, R.id.wenjian_relative, R.id.wode_relative})
    public void onViewClicked(View v){
        switch (v.getId()){
            case R.id.tansuo_relative:
                showLeft(TANSUO);

                break;
            case R.id.wenjian_relative:
                showLeft(WENJIAN);

                break;
            case R.id.wode_relative:
                showLeft(WODE);

                break;
        }
    }
    TanSuoFragment tanSuoFragment ;
    WenJianFragment wenJianFragment;
    WoDeFragment woDeFragment;
    @Bind(R.id.main_relative)
    RelativeLayout main_relative;
    @Bind(R.id.qidong)
    RelativeLayout qidong;
    public static  int screenWidth = 0;
    boolean isBound;
    public static  int screenHight = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // TextView  tv = (TextView) findViewById(R.id.tv);
        EventBus.getDefault().register(this);
        SharedPreferencesUtils.putBean(this,"user",false);
        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        screenWidth = displayMetrics1.widthPixels;
        screenHight = displayMetrics1.heightPixels;
        isTodayFirstLogin();
        mWeb = (WebView) findViewById(R.id.webview);


//        qidong.addView(mWeb);
        setWeb();
      //  mWeb.loadUrl(JavaScript.getYunpandata("/"));

        registerMessageReceiver();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, AppService.class);
                isBound =  bindService(intent, serviceConnection,  Context.BIND_AUTO_CREATE);
                qidong.setVisibility(View.GONE);
            }
        },1000);

       setBaitian();
        if(!SharedPreferencesUtils.contains(this, OkHttpData.TOKEN)){
            OkHttpData.postUser();
        }
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        if(wenJianFragment == null){
            wenJianFragment = new WenJianFragment();
            transaction.add(R.id.frame, wenJianFragment);
        }
        // transaction.replace(R.id.frame,tanSuoFragment);
        transaction.commit();
        i = WENJIAN;
        wode.setImageResource(R.mipmap.wode_2);
        tansuo.setImageResource(R.mipmap.tansuo_2);
        wenjian.setImageResource(R.mipmap.wj_1);
    }
    private void showLeft( int type){
        i = type;
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        if (tanSuoFragment == null) {
            tanSuoFragment = new TanSuoFragment();
            transaction.add(R.id.frame, tanSuoFragment);

            tanSuoFragment.setFan(new TanSuoFragment.FanHui() {
                @Override
                public void setfanhui() {
                    showLeft(WENJIAN);
                }
            });
        }
        if(wenJianFragment == null){
            wenJianFragment = new WenJianFragment();
            transaction.add(R.id.frame, wenJianFragment);
        }
        if(woDeFragment == null){
            woDeFragment = new WoDeFragment();
            transaction.add(R.id.frame, woDeFragment);
            woDeFragment.setYunpa(new WoDeFragment.BaiduYunpa() {
                @Override
                public void getYunpan() {
                    showLeft(WENJIAN);
                }
            });
        }
        switch (type){
            case 1:
                wode.setImageResource(R.mipmap.wode_2);
                tansuo.setImageResource(R.mipmap.tansuo_1);
                wenjian.setImageResource(R.mipmap.wj_2);

                transaction.hide(wenJianFragment);
                transaction.hide(woDeFragment);
                transaction.show(tanSuoFragment);
               // transaction.replace(R.id.frame,tanSuoFragment);
                transaction.commitAllowingStateLoss();
               // startActivity(new Intent(MainActivity.this,YiDongActivity.class));
                break;
            case 2:
                wode.setImageResource(R.mipmap.wode_2);
                tansuo.setImageResource(R.mipmap.tansuo_2);
                wenjian.setImageResource(R.mipmap.wj_1);
                transaction.hide(woDeFragment);
                transaction.hide(tanSuoFragment);
                transaction.show(wenJianFragment);
               // transaction.replace(R.id.frame,wenJianFragment);
                transaction.commitAllowingStateLoss();

                break;
            case 3:

                wode.setImageResource(R.mipmap.wode_1);
                tansuo.setImageResource(R.mipmap.tansuo_2);
                wenjian.setImageResource(R.mipmap.wj_2);
                transaction.hide(tanSuoFragment);
                transaction.hide(wenJianFragment);
                transaction.show(woDeFragment);
               // transaction.replace(R.id.frame,woDeFragment);
                transaction.commitAllowingStateLoss();
                isTodayFirstLogin();

                break;
        }
    }
    /**
     * 判断是否是当日第一次登陆
     */
    @Bind(R.id.hongbao)
    ImageView hongbao;
    private void isTodayFirstLogin() {
        //取
        if(SharedPreferencesUtils.contains(this,"LastLoginTime")) {
            String lastTime = (String) SharedPreferencesUtils.getBean(this, "LastLoginTime");
            // Toast.makeText(MainActivity.this, "value="+date, Toast.LENGTH_SHORT).show();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
            String todayTime = df.format(new Date());// 获取当前的日期

            if (lastTime.equals(todayTime)) { //如果两个时间段相等
                hongbao.setVisibility(View.GONE);
            } else {
                YongHuBiao.clear(this);
                hongbao.setVisibility(View.VISIBLE);
                SharedPreferencesUtils.putBean(this, "LastLoginTime",todayTime);
            }
        }else {
            SharedPreferencesUtils.putBean(this, "LastLoginTime","2018-1-01");
            isTodayFirstLogin();
        }
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            // 建立连接
            // 获取服务的操作对象
            AppService.MyBinder binder = (AppService.MyBinder)service;
            binder.getService();// 获取到的Service即MyService
            }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 连接断开
            }
        };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(i == 2){
                wenJianFragment.onKeyDown(keyCode,event);
            }else {
                /*i = 2;
                showLeft(WENJIAN);*/
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return false;
    }
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        JPushInterface.init(getApplicationContext());//初始化 JPush
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                //setCostomMsg(showMsg.toString());
            }
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        EventBus.getDefault().unregister(this);
        if(isBound) {
            unbindService(serviceConnection);// 解除绑定，断开连接
            isBound = false;
        }
        super.onDestroy();
    }
    @Bind(R.id.button)
    LinearLayout button;
    @Bind(R.id.view)
    View view;
   void setBaitian(){
       if(isBaitian()){
           setBai(button);
           setViewBai(view);
       }else {
           setHei(button);
           setViewHei(view);
       }
       qidong.setBackgroundResource(R.mipmap.heiye);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TuiSongXiaoXi event) {
        for (int i = 0; i < event.getData().size(); i++) {
            setYudingtongzhi(event.getData().get(i));
        }
    }
    private void setYudingtongzhi(final Douban douban){
        final PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_yudingtongzhi, null);
        bang.setContentView(localView);
        bang.showAtLocation(wode, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        LinearLayout ding_linear = (LinearLayout) localView.findViewById(R.id.bangding_linear);
        View v = (View) localView.findViewById(R.id.v1);
        View v2 = (View) localView.findViewById(R.id.v2);
        MLImageView images = (MLImageView) localView.findViewById(R.id.images);
        images.setFocusable(false);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);
        TextView name = (TextView) localView.findViewById(R.id.name);
        TextView pinfen = (TextView) localView.findViewById(R.id.pinfen);
        TextView shijian = (TextView) localView.findViewById(R.id.shijian);
        TextView time = (TextView) localView.findViewById(R.id.time);
        if(!douban.getPingfen().isEmpty()) {
            pinfen.setText("豆瓣评分：" + douban.getPingfen());
        }else {
            pinfen.setText("");
        }
        String s [] = douban.getVersion().split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <s.length ; i++) {
            if(s[i].equals("1")){
                sb.append("枪版 ");
            }else if(s[i].equals("2")){
                sb.append("1080P版 ");
            }else if(s[i].equals("3")){
                sb.append("蓝光版 ");
            }
        }
        time.setText(sb.toString());
        name.setText(douban.getTitle());

        if(isBaitian()){
            setTextBai(queding);
            setTextBai(quxiao);
            setViewBai(v); setViewBai(v2);
            ding_linear.setBackgroundResource(R.drawable.paihang3);
            Picasso
                    .with(this)
                    .load(douban.getImagePath())
                    .placeholder(R.mipmap.img_moren1)//图片加载中显示
                    .into(images);
        }else {
            setTextHei(queding);
            setTextHei(quxiao);
            setViewHei(v);   setViewHei(v2);
            Picasso
                    .with(this)
                    .load(douban.getImagePath())
                    .placeholder(R.mipmap.img_moren1_yejian)//图片加载中显示
                    .into(images);
            ding_linear.setBackgroundResource(R.drawable.paihang6);
        }

        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ChaKanActivity.class);
                intent.putExtra("film",douban);
                startActivity(intent);

            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YunDengLu event) {
        page = event.getPage();

        try {
            String encode = URLEncoder.encode(event.getMulu(),"utf-8");
            mWeb.loadUrl(JavaScript.getYunpandata(encode,event.getPage()));
        }catch (Exception e){

        }

    }
    int page=1;

    WebView mWeb;
    int denglu;
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
                Document document = Jsoup.parse(html);
                Elements e = document.select("pre");
                for (int i = 0; i < e.size(); i++) {
                    LogUtil.v(e.get(i).html());
                    setListData(e.get(i).html());
                }

            //LogUtil.v(html);
        }
    }
    private void setListData(String s){
        try {
            JSONObject jbb = new JSONObject(s);
            String guid_info = jbb.getString("guid_info");
            String errno = jbb.getString("errno");
            String request_id = jbb.getString("request_id");
            String guid = jbb.getString("guid");
            LogUtil.v(errno+"=========erron");
            if(errno.equals("0")){
                List<ListData> listDatas = (List<ListData>) JavaScript.getObjectList(jbb.getString("list"), ListData.class);
                    BaiDuData data = new BaiDuData();
                    data.setList(listDatas);
                    LogUtil.v(listDatas.size() + "----------");
                    data.setGuid(Integer.valueOf(guid));
                    EventBus.getDefault().post(data);

            }


        }catch (Exception e){

        }
    }
    private void setWeb(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            mWeb.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }else{
            try {
                Class<?> clazz = mWeb.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(mWeb.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mWeb.removeJavascriptInterface("searchBoxJavaBredge");//禁止远程代码执行
        final WebSettings settings = mWeb.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(true);          //支持缩放
        settings.setUserAgentString("电脑");
        settings.setBuiltInZoomControls(false);  //不启用内置缩放装置
        settings.setJavaScriptEnabled(true);    //启用JS脚
        mWeb.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        // 打印结果
        try{
            mWeb.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                            + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");

                }

                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                    return super.shouldOverrideUrlLoading(webView, s);
                }
            });
        }catch (Exception e){
        }


    }



}