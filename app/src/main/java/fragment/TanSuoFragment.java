package fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.TanSuoActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.TanSuoAdapter;
import adapter.WenJianAdapter;
import adapter.YiDongAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.LogUtil;
import entity.BaoCunChengGong;
import entity.BaoCunEntity;
import entity.DengLu;
import entity.WenJian;
import entity.Zhuan;
import entity.ZhuanCun;
import entity.ZhuanCunEntity;
import entity.ZhuanCunLuJing;
import entity.ZhuanCunLuJingEntity;
import entity.ZhuancunList;
import okhttp.OkHttpData;
import service.BaiTian;

/**
 * Created by Administrator on 2017/8/17 0017.
 * 转存排行榜
 */

public class TanSuoFragment extends MyFragment {

    public static String LEIXING = "新片";
    TextView text;
    @Bind(R.id.tansuo_list)
    ListView tansuo_list;

    TanSuoAdapter tanSuoAdapter;
    @Bind(R.id.tansuo_linear)
    LinearLayout tansuo_linear;
    @OnClick(R.id.tansuo_linear)
    void setTansuo_linear(){
       // tiaoZhuan.getTiaozhuan();
        startActivity(new Intent(getActivity(),TanSuoActivity.class));
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade);
    }
    public interface TiaoZhuan{
        void getTiaozhuan();
    }
    TiaoZhuan tiaoZhuan;
    public void setTiaoZhuan(TiaoZhuan tiaoZhuan){
        this.tiaoZhuan = tiaoZhuan;
    }
    @Bind(R.id.shuru)
    TextView shuru;
    @Bind(R.id.sou)
    TextView sou;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tansuo_layout,container,false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);

        setBaitian();
        OkHttpData.getZhuancunList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OkHttpData.getZhuancunList();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        swipeRefreshLayout.setRefreshing(true);
    return view;
    }
    ZhuancunList zhuancun;
    List<ZhuancunList> zhuanlist = new ArrayList<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final Zhuan event) {
        swipeRefreshLayout.setRefreshing(false);
        zhuanlist.clear();
        zhuanlist.addAll(event.getData());
        if(tanSuoAdapter == null) {
            tanSuoAdapter = new TanSuoAdapter(zhuanlist);
        }

        tansuo_list.setAdapter(tanSuoAdapter);
        tanSuoAdapter.setOnClick(new TanSuoAdapter.OnItemClick() {
            @Override
            public void onItemClick(View v, int position, View view) {
                if(isDenglu()) {
                    zhuancun = event.getData().get(position);
                    setZhuancun();
                    ZhuanCun entity = new ZhuanCun();
                    entity.setGeshi("文件列表");
                    entity.setUrl(event.getData().get(position).getUrl());
                    entity.setPwd("");
                    entity.setType(LEIXING);
                    EventBus.getDefault().post(entity);

                }else {

                    AppToast.showToast("请先登录");
                    fan.setfanhui();
                }
            }
        });
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                tansuo_list.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <zhuanlist.size() ; i++) {
                            if(zhuanlist.get(i).getLife()<=0){
                                zhuanlist.remove(zhuanlist.get(i));
                            }
                        }
                        tanSuoAdapter.notifyDataSetChanged();
                    }
                });

            }
        }, 1000, 1000);
    }

    Timer timer;

    @Override
    public void onDestroyView() {
        if(timer != null) {
            timer.cancel();
        }
        super.onDestroyView();
    }

    public interface FanHui{
        void setfanhui();
    }
    @Bind(R.id.tansuo_li)
    LinearLayout tansuo_li;
    FanHui fan;
    public void setFan(FanHui fan){
        this.fan = fan;
    }
    @Bind(R.id.view1)
    View v1;
    void setBaitian(){
        if(isBaitian()){
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.white));
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
            setListBai(tansuo_list);
            setViewBai(v);
            setViewBai(v1);
            tansuo_li.setBackgroundResource(R.color.beiying);
            setBai(tansuo_linear);
        }else {
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.beiyingd));
            swipeRefreshLayout.setColorSchemeResources(R.color.white);
            setListHei(tansuo_list);
            setHei1(tansuo_li);
            setViewHei(v);
            setViewHei(v1);
            setHei(tansuo_linear);

        }
    }
    @Bind(R.id.view)
    View v;
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
            if(wenJien.size()==1){
                wenJien.get(0).setXuan(true);
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
                                zhuanCun.setGeshi("子目录");
                                zhuanCun.setType(LEIXING);
                                zhuanCun.setUrl(mulu);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DengLu event) {
        fan.setfanhui();
    }

    String mulu;
    WenJianAdapter adapter ;
    ListView yidong_list;
     PopupWindow bang;
    private void setZhuancun(){
        bang = new PopupWindow(getActivity());
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_zhuancun, null);
        bang.setContentView(localView);
        bang.showAtLocation(tansuo_linear, Gravity.CENTER,0,0);
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
        localView.findViewById(R.id.zhuancun_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int b = 0;
                for (int i = 0; i < wenJien.size(); i++) {
                    if(wenJien.get(i).isXuan()){
                        b = 1;
                    }
                }
                if(b == 0){
                    AppToast.showToast("请选择转存文件");
                }else {
                    setZhuancunLujing();
                }
            }
        });
        TextView lujing = (TextView) localView.findViewById(R.id.lujing);
        zhuancun_lujing = (TextView) localView.findViewById(R.id.zhuancun_lujing);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
        LinearLayout zhuan_lin = (LinearLayout) localView.findViewById(R.id.zhuan_lin);
        LinearLayout baocun_linear = (LinearLayout) localView.findViewById(R.id.baocun_linear);
        LinearLayout zhuancun_linear = (LinearLayout) localView.findViewById(R.id.zhuancun_linear);

        View v1 = (View) localView.findViewById(R.id.v1);
        View v2 = (View) localView.findViewById(R.id.v2);
        View v3 = (View) localView.findViewById(R.id.v3);

        if(isBaitian()){
            setTextBai(queding);
            setTextBai(quxiao);
            setListBai(yidong_list);
            setBai(zhuan_lin);
            setTextBai(zhuancun_lujing);
            setViewBai(v1);
            setViewBai(v2);
            setBai(baocun_linear);
            setViewBai(v3);
            setBai(zhuancun_linear);
        }else {
            setHei(zhuancun_linear);
            setHei(baocun_linear);
            setViewHei(v1);
            setViewHei(v2);
            setHei1(zhuan_lin);
            setViewHei(v3);
            setTextHei(queding);
            setTextHei(quxiao);
            setTextHei(zhuancun_lujing);
            setListHei(yidong_list);
        }
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wenJien1 = new ArrayList<String>();
                int b = 0;
                for (int i = 0; i < wenJien.size(); i++) {
                    if(wenJien.get(i).isXuan()){
                        wenJien1.add(wenJien.get(i).getPath());
                        b =1 ;
                    }
                }
                if(b== 0){
                    AppToast.showToast("请选择转存文件");
                }else {
                    Gson g = new Gson();
                    String s = g.toJson(wenJien1);
                    BaoCunEntity baoCunEntity = new BaoCunEntity();
                    baoCunEntity.setMulu(zhuancunlu);
                    baoCunEntity.setWenjianlujing(s);
                    baoCunEntity.setType(LEIXING);
                    EventBus.getDefault().post(baoCunEntity);
                }
            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });

    }
    TextView zhuancun_lujing;
    private void setZhuancunLujing(){

        final PopupWindow bang = new PopupWindow(getActivity());
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_yidong, null);
        bang.setContentView(localView);
        bang.showAtLocation(tansuo_linear, Gravity.CENTER,0,0);
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
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zhuancun_lujing.setText(zhuancunlu);
                bang.dismiss();
            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
        lujing  = (ListView) localView.findViewById(R.id.yidong_list);
        ZhuanCunLuJing zhuanCunLuJing = new ZhuanCunLuJing();
        zhuanCunLuJing.setPath("/");
        zhuanCunLuJing.setType(LEIXING);
        EventBus.getDefault().post(zhuanCunLuJing);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
        LinearLayout yidong_lin =(LinearLayout) localView.findViewById(R.id.yidong_lin);
        LinearLayout yidong_linear1 = (LinearLayout) localView.findViewById(R.id.yidong_linear1);


        if(isBaitian()){
            setBai(yidong_lin);
            setViewBai(v);
            setViewBai(v1);
            setListBai(lujing);
            setBai(yidong_linear1);

        }else {

            setHei(yidong_linear1);
            setHei1(yidong_lin);
            setViewHei(v);
            setViewHei(v1);
            setListHei(lujing);
        }


    }
    ListView lujing;
    String zhuancunlu="/";
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaoCunChengGong event) {
        if(event.getType().equals(LEIXING)){
            LogUtil.v("+++"+zhuancun.getItemId());
            OkHttpData.postZhuan(zhuancun.getItemId());
            bang.dismiss();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
        if(tanSuoAdapter != null){
            tanSuoAdapter.notifyDataSetChanged();
        }

    }
}
