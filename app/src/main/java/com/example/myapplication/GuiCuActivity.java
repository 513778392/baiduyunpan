package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.GuCuAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.LogUtil;
import entity.Bibili;
import entity.PageBean;
import entity.TanSuoEntity;
import okhttp.OkHttpData;
import view.AutoLoadListener;

public class GuiCuActivity extends BaseActivity {

    @Bind(R.id.guicu_list)
    GridView guicu_list;
    TanSuoEntity entit = new TanSuoEntity();
    List<Bibili> list = new ArrayList<>();
    GuCuAdapter adapter;
    @OnClick(R.id.fan)
    void setFanhui(){
        finish();
    }

    int pageNumber = 1;
    @Bind(R.id.webview)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_cu);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        OkHttpData.getBibili(pageNumber);

        webView.removeJavascriptInterface("searchBoxJavaBredge");//禁止远程代码执行
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(true);          //支持缩放
        settings.setBuiltInZoomControls(false);  //不启用内置缩放装置
        settings.setJavaScriptEnabled(true);    //启用JS脚


        webView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

       /* pageNumber = pageNumber+1;
        OkHttpData.getBibili(pageNumber);*/

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.fade_out
        );
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final PageBean event) {

        number = event.getTotalPage();

        list.addAll((List<Bibili>)event.getData());
        LogUtil.v("dddd"+list.size());
        if(adapter == null) {
            adapter = new GuCuAdapter(list);
        }
        guicu_list.setAdapter(adapter);
        guicu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                webView.loadUrl(list.get(i).getHref());
               /* Intent intent = new Intent(GuiCuActivity.this,GuiCuShiPinActivity.class);
                intent.putExtra("url",list.get(i).getHref());
                startActivity(intent);*/

            }
        });
     /*  guicu_list.setOnScrollListener(new AbsListView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(AbsListView absListView, int i) {
               if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                   if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                       if(pageNumber >= event.getTotalPage()){
                           pageNumber  = event.getTotalPage();
                       }else {
                           pageNumber = pageNumber + 1;
                           OkHttpData.getBibili(pageNumber);
                       }
                   }
               }
           }

           @Override
           public void onScroll(AbsListView absListView, int i, int i1, int i2) {

           }
       });*/
    }
    int number ;
    AutoLoadListener loadListener = new AutoLoadListener(new AutoLoadListener.AutoLoadCallBack() {
        @Override
        public void execute() {
            if(pageNumber !=1 ){
                if(pageNumber >number){
                    pageNumber =number;
                    AppToast.showToast("已显示最后一条");
                }
            }
            pageNumber = pageNumber+1;
            OkHttpData.getBibili(pageNumber);

        }
    });

}
