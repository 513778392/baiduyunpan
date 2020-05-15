package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.BanBenAdapter;
import adapter.XinPianAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.LogUtil;
import data.SharedPreferencesUtils;
import entity.BanBen;
import entity.Douban;
import entity.TuisongChenggong;
import entity.XinPianEntity;
import okhttp.OkHttpData;
import service.BaiTian;

public class XinPianActivity extends BaseActivity {

    @Bind(R.id.xinpain)
    ListView xinpain;
    XinPianEntity entity = new XinPianEntity();
    XinPianAdapter adapter;
    @OnClick(R.id.fan)
    void setFanhui(){
        finish();

      /*  setBanben(new Film());*/
    }

    @Bind(R.id.fanhui)
    TextView fanhui;
    @Bind(R.id.xinpain_relative)
    RelativeLayout xinpain_relative;
    int pageNumber = 1;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_pian);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        OkHttpData.getYuding(pageNumber);
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
                pageNumber = 1;
                OkHttpData.getYuding(pageNumber);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }
    private void setBaitian(){
        if(isBaitian()){
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
            setListBai(xinpain);
            setViewBai(v);
            setTextBai(fanhui);
            xinpain_relative.setBackgroundResource(R.color.beiying);
            setBai(fanhui);
        }else {
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.zitiyanse3));
            swipeRefreshLayout.setColorSchemeResources(R.color.white);
            setHei(fanhui);
            setTextHei(fanhui);
            setViewHei(v);
            setListHei(xinpain);
            setHei1(xinpain_relative);
        }
    }
    @Bind(R.id.v)
    View v;
    List<Douban> douben = new ArrayList<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TuisongChenggong event) {
     // douben.remove(douban);
        StringBuffer strings = new StringBuffer();
        for (int i = 0; i < banBens.size(); i++) {
            if (banBens.get(i).isGouXuan()) {
                    strings.append((i+1)+ ",");
            }
        }
        douban.setuVersion(strings.toString());
        douban.setFlag(true);
        adapter.notifyDataSetChanged();
        bang.dismiss();
    }
    int pos =0;
    int last =0;
    //获取新片数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final XinPianEntity event) {
        swipeRefreshLayout.setRefreshing(false);
        if(pageNumber == 1){
            douben.clear();
        }
        pageNumber = event.getPageNumber();
        douben.addAll(event.getData().getData());
        LogUtil.v(douben.size()+"=============");
        if(adapter == null) {
            adapter = new XinPianAdapter(douben);
        }
        xinpain.setAdapter(adapter);
        xinpain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    setBanben(douben.get(i));
            }
        });
        LogUtil.v(last+"+========"+pos);
        xinpain.setSelectionFromTop(last,pos);
        xinpain.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (douben != null) {
                        if (absListView.getLastVisiblePosition() == absListView.getCount()-1 ) {
                            last =xinpain .getFirstVisiblePosition();
                            View v=xinpain .getChildAt(0);
                            pos = (v==null)?0:v.getTop();
                            pageNumber = event.getPageNumber()+1;
                            LogUtil.v(pageNumber + "========" + event.getData().getTotalPage()+"+=="+last);
                            if (pageNumber <= event.getData().getTotalPage()) {
                                LogUtil.v(pageNumber + "========" + event.getData().getTotalPage());
                                OkHttpData.getYuding(pageNumber);
                            } else {
                                pageNumber = event.getData().getTotalPage();
                            }
                        }
                    }
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


    }
    BanBen banBen = new BanBen();
    BanBenAdapter banBenAdapter;
    List<BanBen> banBens;
    //选择推送版本界面
    Douban douban;
     PopupWindow bang;
    private void setBanben(final Douban film){
        douban = film;
        bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_banben, null);
        bang.setContentView(localView);
        bang.showAtLocation(fanhui, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
       getWindow().setAttributes(lp);
        ListView listView = (ListView) localView.findViewById(R.id.banben_list);
        banBens  = banBen.getList();

        if(!film.getVersion().isEmpty()){
            String s[] = film.getVersion().split(",");
            for (int i = 0; i <s.length ; i++) {
                    if(s[i].equals("1")){
                       banBens.get(0).setImg("1");
                        banBens.get(0).setBanben("枪版（已有）");
                    }else if(s[i].equals("2")){
                        banBens.get(1).setImg("1");
                        banBens.get(1).setBanben("1080P版（已有）");
                     }else if(s[i].equals("3")){
                        banBens.get(2).setImg("1");
                        banBens.get(2).setBanben("蓝光版（已有）");
                    }
            }
        }
        if(!film.getuVersion().isEmpty()){
            String s[] = film.getuVersion().split(",");
            for (int i = 0; i < s.length; i++) {
                if(s[i].equals("1")){
                  banBens.get(0).setGouXuan(true);
                }else if(s[i].equals("2")){
                    banBens.get(1).setGouXuan(true);
                }else if(s[i].equals("3")){
                    banBens.get(2).setGouXuan(true);
                }
            }
        }
        banBenAdapter = new BanBenAdapter(banBens);
       final EditText xingming = (EditText) localView.findViewById(R.id.xingming);
        listView.setAdapter(banBenAdapter);
        LinearLayout ding_linear = (LinearLayout) localView.findViewById(R.id.ding_linear);
        View v = (View) localView.findViewById(R.id.v1);
        View v2 = (View) localView.findViewById(R.id.v2);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);

         if(isBaitian()){
             setViewBai(v); setViewBai(v2);
             ding_linear.setBackgroundResource(R.drawable.paihang3);
             xingming.setBackgroundResource(R.drawable.paihang4);
             setTextBai(xingming);
         }else {
             setViewHei(v);   setViewHei(v2);
             setTextHei(xingming);
             xingming.setBackgroundResource(R.drawable.paihang7);
             ding_linear.setBackgroundResource(R.drawable.paihang6);
         }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(banBens.get(i).getImg()!=null){
                    Intent intent = new Intent(XinPianActivity.this,ChaKanActivity.class);
                    intent.putExtra("film",film);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade);
                }else {
                    banBens.get(i).setGouXuan(!banBens.get(i).isGouXuan());
                    banBenAdapter.notifyDataSetChanged();
                }
            }
        });

        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        if(SharedPreferencesUtils.contains(XinPianActivity.this,"phone")){
            xingming.setText((String)SharedPreferencesUtils.getBean(XinPianActivity.this,"phone"));
        }
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = 0;
                StringBuffer strings = new StringBuffer();
                for (int i = 0; i < banBens.size(); i++) {
                    if (banBens.get(i).isGouXuan()) {
                        j = 1;
                        strings.append(banBens.get(i).getBanben() + "_");
                    }
                }
                if (j == 0) {
                    AppToast.showToast("请选择最少一个版本推送类型");
                } else {
                    if(!xingming.getText().toString().isEmpty()){
                        if(!isMobileNO(xingming.getText().toString())){
                            AppToast.showToast("请输入正确的手机号");
                            return;
                        }
                    }
                    SharedPreferencesUtils.putBean(XinPianActivity.this,"phone",xingming.getText().toString());
                    String s = strings.substring(0, strings.length() - 1);
                    OkHttpData.postUserYuding(xingming.getText().toString(), s, film.getId());

                }
               /* if(isMobileNO(xingming.getText().toString())) {

                }else {
                    AppToast.showToast("请输入正确的手机号码");
                }*/

            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });

    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.fade_out
        );
    }
}
