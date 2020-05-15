package fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.BaiDuWenJianAdapter;
import adapter.YiDongAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.JavaScript;
import data.LogUtil;
import data.SharedPreferencesUtils;
import data.YongHuBiao;
import entity.BaiduUser;
import entity.BaoCunChengGong;
import entity.BaoCunEntity;
import entity.JiLuWeiZhi;
import entity.ShareEntity;
import entity.TuiChu;
import entity.WenJian;
import entity.ZhuanCun;
import entity.ZhuanCunEntity;
import entity.ZhuanCunLuJing;
import entity.ZhuanCunLuJingEntity;
import service.BaiTian;
import yunpandata.BaiDuData;
import yunpandata.ListData;
import yunpandata.YunDengLu;


/**
 * Created by Administrator on 2017/8/17 0017.
 * 百度文件列表
 */

public class WenJianFragment extends MyFragment {
    @Bind(R.id.webview)
    WebView mWeb;
    @Bind(R.id.wenjian_list)
    ListView wenjian_list;
    boolean isXuan = true;
    List<ListData> shanchus ;
    @OnClick({R.id.quanxuan, R.id.yidong, R.id.chongmingming, R.id.fenxiang, R.id.yunpan_linear1})
    void setQuanxuan(View v){
        switch (v.getId()){
            case R.id.quanxuan:
              setShanchu();
                break;
            case R.id.yidong:
                yidongLujing = "/";
                setChongzhi();
                break;
            case R.id.chongmingming:
                setBang();
                break;
            case R.id.fenxiang:
                setFenxiangzhong();
                break;
            case R.id.yunpan_linear1:
                if(yunpan_linear.getVisibility() == View.VISIBLE) {
                    yunpan_linear.setVisibility(View.GONE);
                }
                mWeb.setVisibility(View.VISIBLE);

                break;
        }
    }
    @Bind(R.id.chongmingming)
    TextView chongmingming;
    @Bind(R.id.tansuo_linear)
    LinearLayout tansuo_linear;
    @Bind(R.id.yunpan_linear1)
    LinearLayout yunpan_linear;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fanhui)
    TextView fanhui;
    @Bind(R.id.jindutiao)
    ProgressBar mProgressbar;
    @Bind(R.id.wenjian_linearlayout)
    LinearLayout wenjian_linearlayout;
    @Bind(R.id.view5)
    View view5;
    @Bind(R.id.wenjian_re)
    RelativeLayout wenjian_re;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.wenjian_layout,container,false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);

        type = "wenjian";
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
       /* if(YongHuBiao.contains(getContext(),mulu)){
            if(yunpan_linear.getVisibility() == View.VISIBLE) {
                yunpan_linear.setVisibility(View.GONE);
            }

            wenjian_linearlayout.setVisibility(View.VISIBLE);
            tansuo_linear.setVisibility(View.GONE);
            listDatas.addAll((List<ListData>) YongHuBiao.getBean(getContext(),mulu));
            if(baiDuWenJianAdapter == null){
                baiDuWenJianAdapter = new BaiDuWenJianAdapter(listDatas);
            }
            wenjian_list.setAdapter(baiDuWenJianAdapter);
            setAdapter();
            // SharedPreferencesUtils.remove(getContext(),"listdata");
        }*/
        setWeb();
        //getWenBen();
        setBaitian();
        //findview

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                denglu();
            }
        });
        return view;
    }
    private void denglu(){
        YunDengLu yunDengLu = new YunDengLu();
        yunDengLu.setPage(page);
        yunDengLu.setMulu(mulu);
        EventBus.getDefault().post(yunDengLu);
        shuanxin =1;
        wenjian_list.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wenjian_list.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }
    int shuanxin= 0;
    /*android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light*/
    void setBaitian(){
        if(isBaitian()){
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.white));
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
            wenjian_re.setBackgroundResource(R.color.beiying);
            setBai(tansuo_linear);
            setListBai(wenjian_list);
            setViewBai(view5);
            setBai(wen_relative);
            setTextBai(fanhui);
            setTextBai(quanxuan);
            setTextBai(yidong);
            setTextBai(chongmingming);
            setTextBai(fenxiang);
            setViewBai(v);
            setViewBai(view1);
            setViewBai(view2);



        }else {
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.zitiyanse3));
            swipeRefreshLayout.setColorSchemeResources(R.color.white);
            setHei1(wenjian_re);
            setHei(tansuo_linear);
            setListHei(wenjian_list);
            setViewHei(view5);
            setHei(wen_relative);
            setTextHei(fanhui);
            setTextHei(quanxuan);
            setTextHei(yidong);
            setTextHei(chongmingming);
            setTextHei(fenxiang);
            setViewHei(v);
            setViewHei(view1);
            setViewHei(view2);
        }
    }
    @Bind(R.id.quanxuan)
    TextView quanxuan;
    @Bind(R.id.view)
    View v;
    @Bind(R.id.yidong)
    TextView yidong;
    @Bind(R.id.view1)
    View view1;

    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.fenxiang)
    TextView fenxiang;
    @Bind(R.id.wen_relative)
    RelativeLayout wen_relative;
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    String mulu = "/";
    //选择的文件
    String xuan = "";
    String typeMuLu="移动";
    List<JiLuWeiZhi> jilus = new ArrayList<>();

    int jinqu = 0;
    List<ListData> listDatas = new ArrayList<>();
    BaiDuWenJianAdapter baiDuWenJianAdapter ;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiDuData event) {
        swipeRefreshLayout.setRefreshing(false);
        wenjian_list.setVisibility(View.VISIBLE);
        listDatas.clear();
        listDatas.addAll(event.getList());
        YongHuBiao.putBean(getContext(),mulu,listDatas);
        if(baiDuWenJianAdapter == null){
            baiDuWenJianAdapter = new BaiDuWenJianAdapter(listDatas);
        }
        if(yunpan_linear.getVisibility() == View.VISIBLE) {
            yunpan_linear.setVisibility(View.GONE);
        }
        if(mulu.equals("/")){
            fanhui.setVisibility(View.GONE);
        }else {
            fanhui.setVisibility(View.VISIBLE);
        }

        tansuo_linear.setVisibility(View.GONE);

        wenjian_list.setAdapter(baiDuWenJianAdapter);
        setAdapter();
    }
    private void setAdapter(){
        wenjian_list.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setRefreshing(false);
        wenjian_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * 滚动状态改变时调用
             */
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (listDatas != null) {
                        JiLuWeiZhi weiZhi = new JiLuWeiZhi();
                        View v=wenjian_list .getChildAt(0);
                        weiZhi.setScrolledX(wenjian_list.getFirstVisiblePosition());
                        weiZhi.setScrolledY((v==null)?0:v.getTop());
                        weiZhi.setType(mulu);
                        if(jilus != null&& jilus.size()>0){
                            for (int j = 0; j <jilus.size() ; j++) {
                                if (jilus.get(j).getType().equals(mulu)) {
                                    jilus.set(j, weiZhi);
                                } else {
                                    jilus.add(weiZhi);
                                }
                            }
                        }else {
                            jilus.add(weiZhi);
                        }
                       /* if(absListView.getLastVisiblePosition() == absListView.getCount()-1){
                            LogUtil.v("dibu");
                            int b = page*100;
                            if(absListView.getCount()==100){

                            }
                        }*/
                    }
                }
            }
            /**
             * 滚动时调用
             */

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        baiDuWenJianAdapter.setOnClick(new BaiDuWenJianAdapter.OnClick() {
            @Override
            public void setOnclick(View v, int position, View view) {
                switch (v.getId()){
                    case R.id.wenjian_relative:
                        if(listDatas.get(position).isXuan()){
                            listDatas.get(position).setXuan(false);
                        }else {
                            listDatas.get(position).setXuan(true);
                        }
                        tansuo_linear.setVisibility(View.GONE);
                        List<ListData> wenJien = new ArrayList<ListData>();
                        for (int i = 0; i < listDatas.size(); i++) {
                            if(listDatas.get(i).isXuan()){
                                tansuo_linear.setVisibility(View.VISIBLE);

                                wenJien.add(listDatas.get(i));
                            }
                        }
                        if(wenJien.size()>1){
                            chongmingming.setClickable(false);
                            chongmingming.setTextColor(getResources().getColor(R.color.zitiyanse9));
                        }else {
                            chongmingming.setClickable(true);
                            if(isBaitian()){
                                setTextBai(chongmingming);
                            }else {
                                setTextHei(chongmingming);
                            }
                        }
                        break;
                    case R.id.wenjian_linear:

                        if (listDatas.get(position).getIsdir()==0) {
                            if(listDatas.get(position).getThumbs()!=null){
                                if(listDatas.get(position).getThumbs().getUrl3()!=null){
                                    String path1 = listDatas.get(position).getThumbs().getUrl3().replace("\\","").replace("amp;","");
                                    new DownloadImageTask().execute(path1);

                                }
                            }
                        } else {
                            mulu = listDatas.get(position).getPath();
                            wenjian_list.setVisibility(View.GONE);
                            if(YongHuBiao.contains(getContext(),mulu)){
                                if(mulu.equals("/")){
                                    fanhui.setVisibility(View.GONE);
                                }else {
                                    fanhui.setVisibility(View.VISIBLE);
                                }

                                listDatas.clear();
                                listDatas.addAll((List<ListData>) YongHuBiao.getBean(getContext(),mulu));
                                setAdapter();
                            }else {
                                swipeRefreshLayout.setRefreshing(true);

                                if (listDatas != null && listDatas.size() > 0) {
                                    for (int i = 0; i < listDatas.size(); i++) {
                                        if (listDatas.get(i).isXuan()) {
                                            listDatas.get(i).setXuan(false);
                                        }
                                    }
                                }

                                YunDengLu yunDengLu = new YunDengLu();
                                yunDengLu.setPage(page);
                                yunDengLu.setMulu(mulu);
                                EventBus.getDefault().post(yunDengLu);
                            }
                        }

                        break;
                    default:
                        break;
                }
                baiDuWenJianAdapter.notifyDataSetChanged();
            }
        });
        baiDuWenJianAdapter.notifyDataSetChanged();
        if(jilus.size()>0) {
            for (int i = 0; i < jilus.size(); i++) {
                LogUtil.v(jilus.get(i).getType() + "+++" + mulu);
                if (jilus.get(i).getType().equals(mulu)) {
                    LogUtil.v(jilus.get(i).getScrolledX() + "========" + jilus.get(i).getScrolledY() + jilus.get(i).getType());
                    wenjian_list.setSelectionFromTop(jilus.get(i).getScrolledX(), jilus.get(i).getScrolledY());
                }
            }
        }
        fanhui.setClickable(true);
    }
    private void setImag(Bitmap url){
        final PopupWindow bang = new PopupWindow(getActivity());
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_image, null);
        bang.setContentView(localView);
        bang.showAtLocation(tansuo_linear, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        final    ImageView imageView = (ImageView) localView.findViewById(R.id.img);
        imageView.setImageBitmap(url);
        /*Picasso
                .with(getActivity())
                .load(url)
                .placeholder(R.mipmap.wenjj)//图片加载中显示
                .into(imageView);*/


    }




    class DownloadImageTask extends AsyncTask<String, Integer,Boolean> {

        Bitmap mDownloadImage;
        public DownloadImageTask(){

        }
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            System.out.println("[downloadImageTask->]doInBackground "
                    + params[0]);
            mDownloadImage = getNetWorkBitmap(params[0]);
            return true;
        }

        // 下载完成回调
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            if(mDownloadImage != null){
                setImag(mDownloadImage);
            }

            super.onPostExecute(result);
        }

        // 更新进度回调
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

    }
    public static Bitmap getNetWorkBitmap(String urlString) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(urlString);
            // 使用HttpURLConnection打开连接
            HttpURLConnection urlConn = (HttpURLConnection) imgUrl
                    .openConnection();
            urlConn.setDoInput(true);

            urlConn.connect();
            // 将得到的数据转化成InputStream
            LogUtil.v(urlConn.getResponseCode()+"-------------");
            if(urlConn.getResponseCode()==200) {
                InputStream is = urlConn.getInputStream();
                // 将InputStream转换成Bitmap
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("[getNetWorkBitmap->]MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[getNetWorkBitmap->]IOException"+e.toString());
            e.printStackTrace();
        }
        return bitmap;
    }



    int page = 1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  0:

                    break;
                case  1:
                    /**获取目录列表*/
                  final List<WenJian> wenjian  =(List<WenJian>) msg.getData().getSerializable("lists");
                    yidongadapter = new YiDongAdapter(wenjian);
                    yidong_list.setAdapter(yidongadapter);
                    yidong_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            yidongLujing = wenjian.get(i).getPath();
                            typeMuLu="移动";
                            mWeb.loadUrl(JavaScript.getListDir(yidongLujing));
                        }
                    });
                    break;
                case 4:
                    mWeb.loadUrl(JavaScript.message);
                    try {
                        Thread.sleep(1000);
                        mWeb.loadUrl(JavaScript.getListFile("/"));
                    }catch (Exception e){

                    }

                    break;
                default:
                    break;

            }
        }
    };

    public  String message ="";
    String yidongLujing = "/";
    /**移动目录窗口*/
    private void setChongzhi(){
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
        LinearLayout yidong_linear = (LinearLayout) localView.findViewById(R.id.yidong_linear);
        LinearLayout yidong_linear1 = (LinearLayout) localView.findViewById(R.id.yidong_linear1);
        yidong_list = (ListView) localView.findViewById(R.id.yidong_list);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);

        if(isBaitian()){
            setTextBai(queding);
            setTextBai(quxiao);
            setViewBai(v);
            setViewBai(v1);
            setBai(yidong_linear);
            setBai(yidong_linear1);
            setListBai(yidong_list);
        }else {
            setTextHei(queding);
            setTextHei(quxiao);
            setViewHei(v);
            setViewHei(v1);
            setHei(yidong_linear1);
            setHei1(yidong_linear);
            setListHei(yidong_list);
        }
        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        typeMuLu ="移动";
        mWeb.loadUrl(JavaScript.getListDir(yidongLujing));
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> string  = new ArrayList<String>();
                for (int i = 0; i < listDatas.size(); i++) {
                    if(listDatas.get(i).isXuan()){
                        string.add(listDatas.get(i).getPath());
                    }
                }
                Gson gson  = new Gson();
               String s =  gson.toJson(string);
                LogUtil.v(JavaScript.getMove(s,yidongLujing));
                mWeb.loadUrl(JavaScript.getMove(s,yidongLujing));
                bang.dismiss();
            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
    }
    String paNa ="";
    String type;
    ListView yidong_list;
    YiDongAdapter yidongadapter;
    /**分享链接窗口*/
    private void setFenxiangzhong(){
        final  PopupWindow bang = new PopupWindow(getActivity());
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        bang.setAnimationStyle(R.style.AnimationPreview);
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_fenxiang, null);

        bang.setContentView(localView);
        bang.showAtLocation(tansuo_linear, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT,"This is my text to send");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1,"share"));*/
                List<Long> string  = new ArrayList<Long>();
                for (int i = 0; i < listDatas.size(); i++) {
                    if(listDatas.get(i).isXuan()){
                        string.add(listDatas.get(i).getFs_id());
                        LogUtil.v(listDatas.get(i).getFs_id()+"====");
                    }
                }
                Gson gson  = new Gson();
                String s =  gson.toJson(string);
                LogUtil.v(s+"========="+fenxiang1+"=="+ JavaScript.getFenxiang(s,fenxiang1));

                mWeb.loadUrl(JavaScript.getFenxiang(s,fenxiang1));
                bang.dismiss();
            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
        LinearLayout gongkai = (LinearLayout) localView.findViewById(R.id.gongkai);
        LinearLayout jiami = (LinearLayout) localView.findViewById(R.id.jiami);
    final     ImageView gouxuan_2 = (ImageView) localView.findViewById(R.id.gouxuan_2);
    final     ImageView gouxuan_1 = (ImageView) localView.findViewById(R.id.gouxuan_1);


        LinearLayout paihang = (LinearLayout) localView.findViewById(R.id.ding_linear);
        TextView gong = (TextView) localView.findViewById(R.id.gong);
        TextView jia = (TextView) localView.findViewById(R.id.jia);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);

        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);

        if(isBaitian()){
            setTextBai(gong);
            setViewBai(v);
            setViewBai(v1);
            setTextBai(jia);
            paihang.setBackgroundResource(R.drawable.paihang3);
        }else {
            setViewHei(v);
            setViewHei(v1);
            setTextHei(gong);
            setTextHei(jia);
            paihang.setBackgroundResource(R.drawable.paihang6);
        }
        if(isBaitian()) {
            gouxuan_2.setImageResource(R.mipmap.gouxuan_2);
        }else {
            gouxuan_2.setImageResource(R.mipmap.gouxuan_3);
        }
        gongkai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fenxiang1 = false;
                if(isBaitian()) {
                    gouxuan_2.setImageResource(R.mipmap.gouxuan_2);
                }else {
                    gouxuan_2.setImageResource(R.mipmap.gouxuan_3);
                }
                gouxuan_1.setImageResource(R.mipmap.gouxuan_1);
            }
        });
        jiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fenxiang1 = true;
                if(isBaitian()) {
                    gouxuan_1.setImageResource(R.mipmap.gouxuan_2);
                }else {
                    gouxuan_1.setImageResource(R.mipmap.gouxuan_3);
                }
                gouxuan_2.setImageResource(R.mipmap.gouxuan_1);

            }
        });
    }
    boolean fenxiang1;
    /**重命名窗口*/
    private void setBang(){
        final  PopupWindow bang = new PopupWindow(getActivity());
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        bang.setAnimationStyle(R.style.AnimationPreview);
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_gaiqianming, null);

        bang.setContentView(localView);
        bang.showAtLocation(tansuo_linear, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);

        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);
        final EditText xingming = (EditText) localView.findViewById(R.id.xingming);
        LinearLayout ding_linear = (LinearLayout) localView.findViewById(R.id.ding_linear);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);

        if(isBaitian()){
            setTextBai(xingming);
            setTextBai(queding);
            setTextBai(quxiao);
            setViewBai(v);
            setViewBai(v1);
            xingming.setBackgroundResource(R.drawable.paihang4);
            ding_linear.setBackgroundResource(R.drawable.paihang3);
        }else {
            setTextHei(queding);
            setTextHei(quxiao);
            setViewHei(v);
            setViewHei(v1);
            setTextHei(xingming);
            xingming.setBackgroundResource(R.drawable.paihang7);
            ding_linear.setBackgroundResource(R.drawable.paihang6);
        }


        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i <listDatas.size() ; i++) {
                    if(listDatas.get(i).isXuan()){
                        xuan = listDatas.get(i).getPath();
                        if(listDatas.get(i).getIsdir()==0){
                            houzui = xuan.substring(xuan.lastIndexOf(".")+1);
                            sb.append(xingming.getText().toString()+"."+houzui);
                        }else {
                            sb.append(xingming.getText().toString());
                        }
                    }
                }
             mWeb.loadUrl(JavaScript.getRename(xuan,sb.toString()));
                bang.dismiss();
            }
        });
        localView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });

    }
    String houzui;
    private void setShanchu(){
        final  PopupWindow bang = new PopupWindow(getActivity());
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        bang.setAnimationStyle(R.style.AnimationPreview);
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_shanchu, null);

        bang.setContentView(localView);
        bang.showAtLocation(tansuo_linear, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);

        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);
        final TextView xingming = (TextView) localView.findViewById(R.id.xingming);
        LinearLayout ding_linear = (LinearLayout) localView.findViewById(R.id.ding_linear);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);

        if(isBaitian()){
            setTextBai(xingming);
            setTextBai(queding);
            setTextBai(quxiao);
            setViewBai(v);
            setViewBai(v1);
            ding_linear.setBackgroundResource(R.drawable.paihang3);
        }else {
            setTextHei(queding);
            setTextHei(quxiao);
            setViewHei(v);
            setViewHei(v1);
            setTextHei(xingming);
            ding_linear.setBackgroundResource(R.drawable.paihang6);
        }
        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> delete = new ArrayList<>();
                shanchus = new ArrayList<>();
                for (int i = 0; i <listDatas.size() ; i++) {
                    if(listDatas.get(i).isXuan()){
                        shanchus.add(listDatas.get(i));
                        delete.add(listDatas.get(i).getPath());
                    }
                }
                Gson g = new Gson();
                mWeb.loadUrl(JavaScript.getShanchuWenjian(g.toJson(delete)));
                bang.dismiss();
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
     * 设置webview参数
     * */
    int denglu = 0;
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
        settings.setBuiltInZoomControls(false);  //不启用内置缩放装置
        settings.setJavaScriptEnabled(true);    //启用JS脚
        settings.setUserAgentString(null);
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
                    if(s.length()>=30) {
                        String ss = s.substring(0, 30);
                        if (ss.equals("https://pan.baidu.com/wap/home")) {
                            denglu = 1;
                            SharedPreferencesUtils.putBean(getActivity(),"user",true);
                            settings.setUserAgentString("电脑");
                            mWeb.setVisibility(View.GONE);
                            yunpan_linear.setVisibility(View.GONE);
                            wenjian_linearlayout.setVisibility(View.VISIBLE);
                            denglu();
                        }
                    }
                    return super.shouldOverrideUrlLoading(webView, s);
                }
            });
        }catch (Exception e){
        }
    /**
     * 访问服务器返回的数据处理
     * action：返回数据类型
     * error：返回是否成功
     * data：返回的数据,格式Json
     * */
        mWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                LogUtil.v("onReceivedTitleon=="+s);
                if(s.equals("网页无法打开")){
                    mWeb.setVisibility(View.VISIBLE);
                    wenjian_linearlayout.setVisibility(View.GONE);
                    mWeb.loadUrl("file:///android_asset/webpage/error.html");
                }else if(s.equals("百度网盘-我的文件")){
                    denglu();
                }

            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {

                    mProgressbar.setVisibility(View.GONE);
                        Message message = new Message();
                        message.what = 4;
                        handler.sendMessage(message);

                } else {
                    if (mProgressbar.getVisibility() == View.GONE)
                        mProgressbar.setVisibility(View.VISIBLE);
                    mProgressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                    //解析参数message
                    //调用java方法并得到结果
              final  Message m = new Message();
              LogUtil.c(message);
              final  Bundle b = new Bundle();
                    try {
                        jb = new JSONObject(message);
                        String action = jb.getString("action");
                        String error = jb.getString("error");
                        String msg = jb.getString("msg");
                        LogUtil.v(action+"=========action=========="+jb.getString("data"));

                        if(action.equals("listFile")) {
                            if(error.equals("0")) {
                                denglu();
                            }
                        }else if(action.equals("listDir")){
                            if(error.equals("0")) {
                                List<WenJian> entity = (List<WenJian>) JavaScript.getObjectList(jb.getString("data"), WenJian.class);
                                if (typeMuLu.equals("移动")) {
                                    b.putSerializable("lists", (Serializable) entity);
                                    m.what = 1;
                                    m.setData(b);
                                    handler.sendMessage(m);
                                } else {
                                    ZhuanCunLuJingEntity zhuanCunLuJingEntity = new ZhuanCunLuJingEntity();
                                    zhuanCunLuJingEntity.setData(entity);
                                    zhuanCunLuJingEntity.setType(LEIXING);
                                    EventBus.getDefault().post(zhuanCunLuJingEntity);
                                }
                            }
                        }else if(action.equals("move")){
                            if(error.equals("0")) {
                                shuanxin = 1;
                                AppToast.showToast("移动成功");
                               YunDengLu yunDengLu = new YunDengLu();
                                yunDengLu.setMulu(mulu);
                                EventBus.getDefault().post(yunDengLu);
                            }else {
                                AppToast.showToast("移动失败");
                            }
                        }else if(action.equals("rename")){
                            if(error.equals("0")) {
                                shuanxin = 1;
                                AppToast.showToast("重命名成功");
                                YunDengLu yunDengLu = new YunDengLu();
                                yunDengLu.setMulu(mulu);
                                EventBus.getDefault().post(yunDengLu);
                            }else {
                                AppToast.showToast("重命名失败");
                            }
                        }else if(action.equals("delete")) {
                            if(error.equals("0")) {
                                shuanxin = 1;
                                AppToast.showToast("删除成功");
                                YunDengLu yunDengLu = new YunDengLu();
                                yunDengLu.setMulu(mulu);
                                EventBus.getDefault().post(yunDengLu);
                            }else {
                                AppToast.showToast("删除失败");
                            }
                        }else if(action.equals("share")){
                            if(error.equals("0")) {
                                AppToast.showToast("链接已生成");
                                Gson gson = new Gson();
                                ShareEntity sn =(ShareEntity) gson.fromJson(jb.getString("data"), ShareEntity.class);
                                StringBuffer sb = new StringBuffer();

                                if(sn.getPwd().isEmpty()){
                                    sb.append("链接:"+sn.getShareUrl()+"\n" +
                                            "探索云盘搜索\n"+
                                            "tansuo233.com \n");
                                }else {
                                    sb.append("链接:"+sn.getShareUrl()+"\n" +
                                            " 密码:"+sn.getPwd()+"\n" +
                                            "探索云盘搜索\n" +
                                            "tansuo233.com \n");
                                }

                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                                intent.setType("text/plain");
                                //设置分享列表的标题，并且每次都显示分享列表
                                startActivity(Intent.createChooser(intent, "分享到"));
                            }else {
                                AppToast.showToast("分享失败");
                            }
                        }else if(action.equals("enterShare")){
                            if(error.equals("0")) {
                                List<WenJian> entity = (List<WenJian>) JavaScript.getObjectList(jb.getString("data"), WenJian.class);
                                ZhuanCunEntity zhuan = new ZhuanCunEntity();
                                zhuan.setData(entity);
                                zhuan.setType(LEIXING);
                                EventBus.getDefault().post(zhuan);
                            }else {
                                AppToast.showToast("分享文件已取消");
                            }
                        }else if(action.equals("enterShare_listFile")){
                            List<WenJian> entity = (List<WenJian>) JavaScript.getObjectList(jb.getString("data"), WenJian.class);
                            ZhuanCunEntity zhuan = new ZhuanCunEntity();
                            zhuan.setData(entity);
                            zhuan.setType(LEIXING);
                            EventBus.getDefault().post(zhuan);
                        }else if(action.equals("enterShare_transfer")){
                            if(error.equals("0")){
                                shuanxin = 1;
                                YunDengLu yunDengLu = new YunDengLu();
                                yunDengLu.setMulu(mulu);
                                EventBus.getDefault().post(yunDengLu);
                                BaoCunChengGong chengGong = new BaoCunChengGong();
                                chengGong.setType(LEIXING);
                                LogUtil.v(LEIXING+"============");
                                EventBus.getDefault().post(chengGong);
                                AppToast.showToast("转存成功");
                            }else {
                                AppToast.showToast(msg);
                            }
                        }
                    }catch (Exception e){

                    }

                //返回结果
                result.confirm("result");

                return true;
            }
        });

        mWeb.loadUrl(JavaScript.path);

    }
    JSONObject jb;

    List<BaiduUser> list = new ArrayList<>();


    @OnClick(R.id.fanhui)
    void setFanhui(){
        fanhui.setClickable(false);
            String[] suoyou = mulu.split("/");
            StringBuffer sb = new StringBuffer();
            if (suoyou.length > 1) {
                jinqu = 0;

                for (int i = 0; i < suoyou.length - 1; i++) {
                    sb.append(suoyou[i] + "/");
                }
                mulu = sb.toString();
                if(YongHuBiao.contains(getContext(),mulu)){
                    listDatas.clear();
                    listDatas.addAll((List<ListData>) YongHuBiao.getBean(getContext(),mulu));
                    if(mulu.equals("/")){
                        fanhui.setVisibility(View.GONE);
                    }else {
                        fanhui.setVisibility(View.VISIBLE);
                    }

                    setAdapter();
                }else {
                    YunDengLu dengLu = new YunDengLu();
                    dengLu.setMulu(mulu);
                    EventBus.getDefault().post(dengLu);
                }
               /* String s = shouci;
                Message m = new Message();
                Bundle b = new Bundle();
                m.what = 0;
                b.putString("s",s);
                m.setData(b);
                handler.sendMessage(m);*/
               // mWeb.loadUrl(JavaScript.getListFile(mulu));
            }

    }
    private boolean isShouci(String shou){
        return SharedPreferencesUtils.contains(getContext(),shou);
    }
    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */

    // 返回键按下时会被调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
                String [] suoyou = mulu.split("/");
                StringBuffer sb = new StringBuffer();
                if(suoyou.length>1){


                    jinqu = 0;
                for (int i = 0; i <suoyou.length-1 ; i++) {
                    sb.append(suoyou[i] + "/");
                }

                    mulu = sb.toString();
                    if(YongHuBiao.contains(getContext(),mulu)){
                        listDatas.clear();
                        listDatas.addAll((List<ListData>) YongHuBiao.getBean(getContext(),mulu));
                        if(mulu.equals("/")){
                            fanhui.setVisibility(View.GONE);
                        }else {
                            fanhui.setVisibility(View.VISIBLE);
                        }

                        setAdapter();
                    }else {
                        YunDengLu dengLu = new YunDengLu();
                        dengLu.setMulu(mulu);
                        EventBus.getDefault().post(dengLu);
                    }
            }else {
                    Intent home = new Intent(Intent.ACTION_MAIN);
                    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    home.addCategory(Intent.CATEGORY_HOME);
                    startActivity(home);
                }
          //  path = "http://pan.baidu.com/api/list?dir=%2F"+pathName;

          //  mWeb.loadUrl(path);
            // TODO
            return true;
        }
        return false;
    }

    String LEIXING = "";
    //转存文件列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZhuanCun event) {
        LEIXING = event.getType();
        if(event.getGeshi().equals("文件列表")) {
            LogUtil.v(JavaScript.getFenxiangWenjian(event.getUrl(), event.getPwd()));
            mWeb.loadUrl(JavaScript.getFenxiangWenjian(event.getUrl(), event.getPwd()));
        }else {
            mWeb.loadUrl(JavaScript.getHuoquFenxiangWenjian(event.getUrl()));
        }

    }
     @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZhuanCunLuJing event) {
         typeMuLu="磁力";
         LEIXING = event.getType();
         mWeb.loadUrl(JavaScript.getListDir(event.getPath()));
     }
    //转存是保存路径
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaoCunEntity event) {
        LEIXING = event.getType();
        mWeb.loadUrl(JavaScript.getZhuancunWenjian(event.getWenjianlujing(),event.getMulu()));
    }
    /**是否白天返回监听
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();

        if(baiDuWenJianAdapter !=null){
            baiDuWenJianAdapter.notifyDataSetChanged();
        }


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TuiChu event) {
       setWeb();

        mWeb.setVisibility(View.VISIBLE);
        yunpan_linear.setVisibility(View.GONE);
        wenjian_linearlayout.setVisibility(View.GONE);
    }
}

