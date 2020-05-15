package com.example.myapplication;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.DaKaiAdapter;
import adapter.TanSuoAdapter2;
import adapter.WenJianAdapter;
import adapter.YiDongAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.LogUtil;
import entity.BanBen;
import entity.BaoCunChengGong;
import entity.BaoCunEntity;
import entity.Douban;
import entity.SearchJieguo;
import entity.SearchJson;
import entity.WenJian;
import entity.ZhongZiEntity;
import entity.ZhuanCun;
import entity.ZhuanCunEntity;
import entity.ZhuanCunLuJing;
import entity.ZhuanCunLuJingEntity;
import okhttp.OkHttpData;
import service.BaiTian;

public class ChaKanActivity extends BaseActivity {
    public static String LEIXING = "探索";
    @Bind(R.id.chakan_list)
    ListView chakan_list;
    String WENJIAN = "文件夹";
    Douban title;
    @Bind(R.id.fanhui)
    TextView fanhui;
    @Bind(R.id.fan)
    TextView jiaocheng;
    @OnClick(R.id.fanhui)
    void setFan(){
        finish();

    }
    @OnClick(R.id.fan)
    void jianjie(){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("https://jingyan.baidu.com/article/e8cdb32b1eb0d337042bad60.html?qq-pf-to=pcqq.c2c");
        intent.setData(content_url);
        startActivity(intent);
    }
    @Bind(R.id.fan_relative)
    LinearLayout fan_relative;
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_kan);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        title =(Douban) getIntent().getSerializableExtra("film");
        LogUtil.v(title.getHref());
        OkHttpData.getHref(title.getHref());
        setBaitian();
    }
    @Bind(R.id.v)
    View v;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.cha_re)
    RelativeLayout cha_re;
    private void setBaitian(){
        if(isBaitian()){
            setTextBai(fanhui);
            setTextBai(jiaocheng);
            setViewBai(v);
            setViewBai(v1);
            setBai(cha_re);
            setListBai(chakan_list);
            setBai(fan_relative);
        }else {
            setHei1(cha_re);
            setTextHei(fanhui);
            setTextHei(jiaocheng);
            setViewHei(v);
            setViewHei(v1);
            setHei(fan_relative);
            setListHei(chakan_list);
        }
    }

    TanSuoAdapter2 tanSuoAdapter1;
    List<SearchJson> lists = new ArrayList<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchJieguo event) {
        lists.clear();
        lists.addAll(event.getData());
     if(tanSuoAdapter1 == null){
         tanSuoAdapter1 = new TanSuoAdapter2(lists);
     }
        chakan_list.setAdapter(tanSuoAdapter1);
        tanSuoAdapter1.setOnClick(new TanSuoAdapter2.OnItemClick() {
            @Override
            public void onItemClick(View v, int position, View view) {
                switch (v.getId()) {
                    case R.id.fen:
                        StringBuffer sb = new StringBuffer();

                        sb.append(lists.get(position).getMagnet()+"\n" +
                                title.getTitle()+"\n"+
                                "探索云盘搜索\n"+
                                "tansuo233.com \n");

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                        intent.setType("text/plain");
                        //设置分享列表的标题，并且每次都显示分享列表
                        startActivity(Intent.createChooser(intent, "分享到"));
                        break;
                    case R.id.zhuan:
                      /*  setZhuancun();
                        ZhuanCun entity = new ZhuanCun();
                        entity.setGeshi("文件列表");
                        entity.setType(LEIXING);
                        entity.setUrl(lists.get(position).getShareUrl());
                        entity.setPwd("");
                        LogUtil.v(lists.get(position).getShareUrl());
                        EventBus.getDefault().post(entity);*/
                        // 从API11开始android推荐使用android.content.ClipboardManager
                        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                        setDakai(lists.get(position));
                        break;
                    case R.id.linear_1:
                       /* if (lists.get(position).getIsDir()) {
                            OkHttpData.getZhongzi(lists.get(position).getId() + "", WENJIAN);
                        } else {
                            setZhuancun();
                            ZhuanCun entity1 = new ZhuanCun();
                            entity1.setGeshi("文件列表");
                            entity1.setType(LEIXING);
                            entity1.setUrl(lists.get(position).getShareUrl());
                            entity1.setPwd("");
                            LogUtil.v(lists.get(position).getShareUrl());
                            EventBus.getDefault().post(entity1);
                        }*/
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
        if(tanSuoAdapter1 != null) {
            tanSuoAdapter1.notifyDataSetChanged();
        }

    }

    List<WenJian> wenJien = new ArrayList<>();
    List<String> wenJien1;
    //获取需要转存的目录文件列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZhuanCunEntity event) {
        if(event.getType().equals(LEIXING)) {
            wenJien.clear();
            wenJien.addAll(event.getData());
            if (adapter == null) {
                adapter = new WenJianAdapter(wenJien);
            }
            yidong_list.setAdapter(adapter);
            adapter.setOnClick(new WenJianAdapter.OnClick() {
                @Override
                public void setOnclick(View v, int position, View view) {
                    switch (v.getId()) {
                        case R.id.wenjian_relative:
                            if (wenJien.get(position).isXuan()) {
                                wenJien.get(position).setXuan(false);
                            } else {
                                wenJien.get(position).setXuan(true);
                            }
                            break;
                        case R.id.wenjian_linear:
                            if(wenJien.get(position).isdir()) {
                                mulu = wenJien.get(position).getPath();
                                ZhuanCun zhuanCun = new ZhuanCun();
                                zhuanCun.setType(LEIXING);
                                zhuanCun.setUrl(mulu);
                                zhuanCun.setGeshi("子目录");
                                EventBus.getDefault().post(zhuanCun);
                            }else {
                                AppToast.showToast("已经是文件了");
                            }
                            break;
                        default:
                            break;
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
    String mulu;
    WenJianAdapter adapter ;
    ListView yidong_list;
    PopupWindow bang;
    private void setZhuancun(){
        bang= new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_zhuancun, null);
        bang.setContentView(localView);
        bang.showAtLocation(chakan_list, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
      /*  WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);*/
        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
              /*  WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);*/
            }
        });


        yidong_list = (ListView) localView.findViewById(R.id.yidong_list);


        LinearLayout zhuancun_linear = (LinearLayout) localView.findViewById(R.id.zhuancun_linear);
        LinearLayout zhuan_lin=(LinearLayout) localView.findViewById(R.id.zhuan_lin);
        zhuancun_lujing = (TextView) localView.findViewById(R.id.zhuancun_lujing);
        LinearLayout baocun_linear = (LinearLayout)localView.findViewById(R.id.baocun_linear);
        View v1 = (View) localView.findViewById(R.id.v1);
        View v3 = (View) localView.findViewById(R.id.v3);
        View v2 = (View) localView.findViewById(R.id.v2);
        TextView queding = (TextView)localView.findViewById(R.id.queding);
        TextView quxiao = (TextView)localView.findViewById(R.id.quxiao);
        TextView lujing = (TextView) localView.findViewById(R.id.lujing);


        if(isBaitian()){
            setTextBai(queding);
            setTextBai(quxiao);
            setViewBai(v1);
            setViewBai(v2);
            setViewBai(v3);
            setTextBai(zhuancun_lujing);
            setBai(zhuan_lin);
            setBai(baocun_linear);
            setListBai(yidong_list);
            setBai(zhuancun_linear);
        }else {
            setTextHei(queding);
            setTextHei(quxiao);
            setHei(baocun_linear);
            setHei(zhuancun_linear);
            setTextHei(zhuancun_lujing);
            setViewHei(v1);
            setViewHei(v2);
            setViewHei(v3);
            setListHei(yidong_list);
            setHei1(zhuan_lin);
        }


        zhuancun_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZhuancunLujing();

            }
        });

        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wenJien1 = new ArrayList<String>();
                for (int i = 0; i < wenJien.size(); i++) {
                    if(wenJien.get(i).isXuan()){
                        wenJien1.add(wenJien.get(i).getPath());
                    }
                }
                Gson g = new Gson();
                String s = g.toJson(wenJien1);
                BaoCunEntity baoCunEntity = new BaoCunEntity();
                baoCunEntity.setMulu(zhuancunlu);
                baoCunEntity.setWenjianlujing(s);
                baoCunEntity.setType(LEIXING);
                EventBus.getDefault().post(baoCunEntity);

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
    public void onEventMainThread(ZhongZiEntity event) {
        setZhongzi(event.getData());
    }

    private void setZhongzi(final List<SearchJson> data){
        final PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_zhongziwenjian, null);
        bang.setContentView(localView);
        ListView zhongzi_list = (ListView) localView.findViewById(R.id.zhongzi_list);

        bang.showAtLocation(chakan_list, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        localView.findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
        LinearLayout zhongzi_lin = (LinearLayout) localView.findViewById(R.id.zhongzi_lin);
        TextView fan = (TextView) localView.findViewById(R.id.fanhui);
        View v = (View) localView.findViewById(R.id.v);

        if(isBaitian()){
            setViewBai(v);
            setBai(fan);
            setBai(zhongzi_lin);
            setListBai(zhongzi_list);
            setTextBai(fan);
        }else {
            setTextHei(fan);
            setViewHei(v);
            setHei(fan);
            setHei1(zhongzi_lin);
            setListHei(zhongzi_list);
        }

        TanSuoAdapter2 tansuo = new TanSuoAdapter2(data);
        zhongzi_list.setAdapter(tansuo);
        tansuo.setOnClick(new TanSuoAdapter2.OnItemClick() {
            @Override
            public void onItemClick(View v, int position, View view) {
                switch (v.getId()) {
                    case R.id.fen:

                        break;
                    case R.id.zhuan:
                        setZhuancun();
                        ZhuanCun entity = new ZhuanCun();
                        entity.setGeshi("文件列表");
                        entity.setType(LEIXING);
                        entity.setUrl(data.get(position).getShareUrl());
                        entity.setPwd("");
                        LogUtil.v(data.get(position).getShareUrl());
                        EventBus.getDefault().post(entity);
                        break;
                    case R.id.tousuo1_linear:
                        if (lists.get(position).getIsDir()) {
                            OkHttpData.getZhongzi(data.get(position).getId() + "", WENJIAN);
                        } else {
                            AppToast.showToast("当前已是文件");
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    TextView zhuancun_lujing;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaoCunChengGong event) {
        if(event.getType().equals(LEIXING)){
            for (int i = 0; i < wenJien.size(); i++) {
                if(wenJien.get(i).isXuan()){
                  // OkHttpData.postZhuan(wenJien.get(i).getFs_id());
                }
            }

            bang.dismiss();
        }
    }
    private void setZhuancunLujing(){
        final PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_yidong, null);
        bang.setContentView(localView);
        bang.showAtLocation(chakan_list, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        LinearLayout yidong_lin =(LinearLayout) localView.findViewById(R.id.yidong_lin);
        LinearLayout yidong_linear1 = (LinearLayout) localView.findViewById(R.id.yidong_linear1);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView queding = (TextView)localView.findViewById(R.id.queding);
        TextView quxiao = (TextView)localView.findViewById(R.id.quxiao);
        lujing  = (ListView) localView.findViewById(R.id.yidong_list);
        if(isBaitian()){
            setTextBai(queding);
            setBai(yidong_linear1);
            setTextBai(quxiao);
            setViewBai(v);
            setViewBai(v1);
            setBai(yidong_lin);
            setListBai(lujing);
        }else {
            setHei(yidong_linear1);
            setTextHei(queding);
            setTextHei(quxiao);
            setViewHei(v);
            setViewHei(v1);
            setListHei(lujing);
            setHei1(yidong_lin);
        }
      /*  WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);*/
        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

              /*  WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);*/
            }
        });
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lujing ="";
                if(zhuancunlu.length()>20) {
                    lujing = zhuancunlu.substring(zhuancunlu.length() - 15, zhuancunlu.length() - 1);
                }else {
                    lujing = zhuancunlu;
                }
                zhuancun_lujing.setText(lujing);
                bang.dismiss();
            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });

        ZhuanCunLuJing zhuanCunLuJing = new ZhuanCunLuJing();
        zhuanCunLuJing.setPath("/");
        zhuanCunLuJing.setType(LEIXING);
        EventBus.getDefault().post(zhuanCunLuJing);

    }
    ListView lujing;
    String zhuancunlu ="/";
    YiDongAdapter yiDongAdapter;
    List<WenJian> listLuJing = new ArrayList<>();


    //获取转存目录列表位置
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZhuanCunLuJingEntity event) {
        if (event.getType().equals(LEIXING)) {
            listLuJing.clear();
            listLuJing.addAll(event.getData());
            if (yiDongAdapter == null) {
                yiDongAdapter = new YiDongAdapter(listLuJing);
            }
            lujing.setAdapter(yiDongAdapter);
            lujing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ZhuanCunLuJing zhuanCunLuJing = new ZhuanCunLuJing();
                    zhuanCunLuJing.setPath(listLuJing.get(i).getPath());
                    zhuancunlu = listLuJing.get(i).getPath();
                    zhuanCunLuJing.setType(LEIXING);
                    EventBus.getDefault().post(zhuanCunLuJing);
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    private void setDakai(final SearchJson data){
        final PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_dakai, null);
        bang.setContentView(localView);
        bang.showAtLocation(fanhui, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        ListView listView = (ListView) localView.findViewById(R.id.banben_list);
        BanBen b= new BanBen();
        DaKaiAdapter banBenAdapter = new DaKaiAdapter(b.getData());

        listView.setAdapter(banBenAdapter);
        LinearLayout ding_linear = (LinearLayout) localView.findViewById(R.id.ding_linear);
        View v = (View) localView.findViewById(R.id.v1);


        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);

        if(isBaitian()){
            setViewBai(v);
            ding_linear.setBackgroundResource(R.drawable.paihang3);

        }else {
            setViewHei(v);
            ding_linear.setBackgroundResource(R.drawable.paihang6);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(data.getMagnet());

                switch (i){
                    case 0:
                        startActivityForPackage(ChaKanActivity.this,"com.xunlei.downloadprovider");
                        break;
                    case 1:
                        startActivityForPackage(ChaKanActivity.this,"com.baidu.netdisk");



                        break;
                    case 2:
                        AppToast.showToast("已复制");
                        break;
                }
                bang.dismiss();
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

        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });

    }

}
