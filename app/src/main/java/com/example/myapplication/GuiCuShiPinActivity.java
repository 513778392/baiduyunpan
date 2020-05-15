package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.Bibili;
import entity.PageBean;
import okhttp.OkHttpData;
import service.BaiTian;

public class GuiCuShiPinActivity extends BaseActivity {

    @Bind(R.id.guicu_list)
    RecyclerView guicu_list;
    List<Bibili> list = new ArrayList<>();

    @OnClick(R.id.fan)
    void setFanhui(){
        finish();
    }



    WebView webView;
    GridLayoutManager manager;
    @Bind(R.id.fanhui)
    TextView fanhui;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_cu_shi_pin);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        OkHttpData.getBibili(page);
        webView = new WebView(this);
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
        manager = new GridLayoutManager(this,2);
        guicu_list.setLayoutManager(manager);
        setBaitian();
        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },5000);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                OkHttpData.getBibili(page);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }
    @Bind(R.id.shipin_relative)
    RelativeLayout shipin_relative;
    private void setBaitian(){
        if(isBaitian()){
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
            shipin_relative.setBackgroundResource(R.color.beiying);
            setTextBai(fanhui);
            setBai(fanhui);
            setViewBai(v);
        }else {
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.zitiyanse3));
            swipeRefreshLayout.setColorSchemeResources(R.color.white);
            setHei1(shipin_relative);
            setViewHei(v);
            setHei(fanhui);
            setTextHei(fanhui);
        }
    }
    @Bind(R.id.v)
    View v;

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
    MyAdapter adapter ;
    int page = 1;
    boolean isLoading = false;//用来控制进入getdata()的次数

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final PageBean event) {
        swipeRefreshLayout.setRefreshing(false);
        if(page == 1){
            list.clear();
        }
        list.addAll(event.getData());

        if(adapter == null){
            adapter = new MyAdapter(this,list);
        }
        if(isLoading){
            adapter.notifyItemRangeChanged(adapter.getItemCount() , adapter.getItemCount() );
        }

        isLoading = false;
        guicu_list.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, View v) {
                webView.loadUrl(list.get(position).getHref());
            }
        });
        adapter.setOnRecyclerViewBottomListener(new MyAdapter.OnRecyclerBottomListener() {
            @Override
            public void onRecyclerBottomListener() {
                if (page < event.getTotalPage()) {

                    isLoading = true;
                    page++;
                    OkHttpData.getBibili(page);
                }else {
                    page = event.getTotalPage();

                }

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

}
