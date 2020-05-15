package com.example.myapplication;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import adapter.SousuoAdapter;
import adapter.TanSuoAdapter1;
import adapter.TanSuoAdapter2;
import adapter.WenJianAdapter;
import adapter.YiDongAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.LogUtil;
import data.SharedPreferencesUtils;
import entity.BanBen;
import entity.BaoCunChengGong;
import entity.BaoCunEntity;
import entity.DengLu;
import entity.GuoLvEntity;
import entity.HitKeywork;
import entity.KeyWordList;
import entity.Keyword;
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
import view.LiShiLineWrapLayout;

public class TanSuoActivity extends BaseActivity {

   public static String LEIXING = "探索";
    @Bind(R.id.sousuo_edit)
    EditText sousuo_edit;

    @Bind(R.id.lisisousuo)
    LiShiLineWrapLayout lisisousuo;

    @Bind(R.id.remensousuo)
    LiShiLineWrapLayout remensousuo;

    String LISHI = "历史";
    @Bind(R.id.sousuo_text)
    TextView sousuo_text;
    List<String> lishi = new ArrayList<>();
    @OnClick(R.id.sousuo_text)
    void setSousuo_text() {
        if (sousuo_text.getText().toString().equals("搜索")) {
            if (sousuo_edit.getText().toString().isEmpty()) {
                AppToast.showToast("请输入搜索电影");
            } else {

                setTianjiasousuo();
                rotview.setFocusable(true);
                rotview.setFocusableInTouchMode(true);
                rotview.requestFocus();
                sousuo_linear.setVisibility(View.GONE);
                sousuo_list.setVisibility(View.GONE);
                tansuo_list1.setVisibility(View.VISIBLE);
                OkHttpData.getSousuo(sousuo_edit.getText().toString());
            }
        } else {
            finish();
        }
    }
    @OnClick(R.id.shanchulishi)
    void setShanchulishi(){
        SharedPreferencesUtils.remove(this,LISHI);
        lisisousuo.removeAllViews();
    }
    private void setTianjiasousuo(){
        lishi.clear();
        lisisousuo.removeAllViews();
        if (SharedPreferencesUtils.contains(this, LISHI)) {
            lishi.addAll((List<String>) SharedPreferencesUtils.getBean(this, LISHI));
            if (lishi.size() > 0) {
                for (int i = 0; i < lishi.size(); i++) {
                    if (lishi.get(i).equals(sousuo_edit.getText().toString())) {
                        lishi.remove(sousuo_edit.getText().toString());
                    }
                }
            }
            lishi.add(0, sousuo_edit.getText().toString());

        } else {
            lishi.add(sousuo_edit.getText().toString());
        }
        if (lishi.size() > 50) {
            lishi.remove(lishi.size() - 1);
        }
        SharedPreferencesUtils.putBean(this, LISHI, lishi);
        lisisousuo.setData(lishi);
        lisisousuo.setOnItemClickListener(new LiShiLineWrapLayout.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                c = 1;
                TextView tv = (TextView) view;
                sousuo_edit.setText(tv.getText().toString());
            }
        });
    }
    @Bind(R.id.sousuo_linear)
    LinearLayout sousuo_linear;
    @Bind(R.id.tansuo_list1)
    ListView tansuo_list1;

    List<SearchJson> lists = new ArrayList<>();

    TanSuoAdapter1 adapter1;

    @Bind(R.id.rootview)
    RelativeLayout rotview;
    @Bind(R.id.guolv_relative)
    LinearLayout guolv_relative;
    @Bind(R.id.gulv)
    TextView gulv;
    int gv = 0;
    @OnClick(R.id.gulv)
    void setGuolv(){
        gulv.setText("过滤中");
        StringBuffer sb  = new StringBuffer();
        if(lists != null&& lists.size()>0) {
            for (int i = 0; i < lists.size(); i++) {
                if (!lists.get(i).getIsDir()) {
                    sb.append(lists.get(i).getId() + "_");
                    gv = 1;
                }
            }
        }
        if(gv == 1) {
            String s = sb.toString().substring(0, sb.length() - 1);
            LogUtil.v(s);
            OkHttpData.getGuolv(sb.toString());
        }else {
            gulv.setText("过滤完成");
        }
        gv = 0;
    }
    @Bind(R.id.qiangli)
    TextView qiangli;
    @OnClick(R.id.qiangli)
    void setQiangli(){
        shishi.setText("大约需要30秒");
        qiangli.setText("正在搜索");
        OkHttpData.getQiangli(sousuo_edit.getText().toString());
    }
    @Bind(R.id.tansuo_linear)
    LinearLayout tansuo_linear;
    List<String> strings = new ArrayList<>();
    @Bind(R.id.sousuo_list)
    ListView sousuo_list;
    SousuoAdapter adapterdd ;
    @Bind(R.id.shanchu)
    ImageView shanchu;
    @OnClick(R.id.shanchu)
    void  setShanchu(){
        sousuo_edit.setText("");
    }
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_suo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if(SharedPreferencesUtils.contains(this,LISHI)){
            lishi.addAll((List<String>)SharedPreferencesUtils.getBean(this,LISHI));
            lisisousuo.setData(lishi);

        }
        getList();

        setBaitian();
        setList();
        OkHttpData.getHit();
    }


        private void setList(){
            for (int i = 0; i <20 ; i++) {
                strings.add("一个人"+i);
            }
    }
    @Bind(R.id.shishi_relative)
    RelativeLayout shishi_relative;
    @Bind(R.id.v5)
    View v5;
    @Bind(R.id.v6)
    View v6;
    @Bind(R.id.shishi)
    TextView shishi;
    private void setBaitian(){
        if(isBaitian()){
            setViewBai(v5);
            setViewBai(v6);
            shanchu.setImageResource(R.mipmap.cha);
            rotview.setBackgroundResource(R.color.beiying);
            setListBai(tansuo_list1);
            setBai(guolv_relative);
            setTextBai(gulv);
            setTextBai(shishi);
            setViewBai(v3);
            setViewBai(v2);
            setViewBai(v1);
            setTextBai(sousuo_edit);
            setTextBai(qiangli);
            setBai(tansuo_linear);
        }else {
            setViewHei(v5);
            shanchu.setImageResource(R.mipmap.cha1);
            setTextHei(sousuo_edit);
            setHei(tansuo_linear);
            setViewHei(v3);
            setViewHei(v2);
            setViewHei(v6);
            setViewHei(v1);
            setTextHei(gulv);
            setTextHei(shishi);
            setTextHei(qiangli);
            setHei(guolv_relative);
            setHei1(rotview);
            setListHei(tansuo_list1);
        }
    }
    @Bind(R.id.v2)
    View v2;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.v3)
    View v3;
    List<Keyword> keywords = new ArrayList<>();
    int c = 0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(KeyWordList event) {
        keywords.clear();
        keywords.addAll(event.getData());
        if(adapterdd == null) {
            adapterdd = new SousuoAdapter(keywords);
        }
        sousuo_list.setAdapter(adapterdd);
        sousuo_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                rotview.setFocusable(true);
                rotview.setFocusableInTouchMode(true);
                rotview.requestFocus();

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        sousuo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c = 1;
                sousuo_edit.setText(keywords.get(i).getKeyword());
                sousuo_edit.setSelection(sousuo_edit.getText().length());
                rotview.setFocusable(true);
                rotview.setFocusableInTouchMode(true);
                rotview.requestFocus();
                sousuo_linear.setVisibility(View.GONE);
                tansuo_list1.setVisibility(View.VISIBLE);
                sousuo_list.setVisibility(View.GONE);
                setTianjiasousuo();
                OkHttpData.getSousuo(sousuo_edit.getText().toString());
            }
        });
        sousuo_list.setOverScrollMode(View.OVER_SCROLL_NEVER);
        sousuo_list.setSelection(adapterdd.getCount());
        adapterdd.notifyDataSetChanged();
}

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(GuoLvEntity event) {
        LogUtil.v(event.getList().size()+"==================");
        List<SearchJson> search = new ArrayList<>();
        if(event != null&&event.getList().size()>0) {
            for (int i = 0; i < event.getList().size(); i++) {
                for (int j = 0; j < lists.size(); j++) {
                    LogUtil.v(event.getList().get(i)+"+====="+lists.get(j).getId());
                    if (event.getList().get(i).equals(lists.get(j).getId())) {
                        LogUtil.v(event.getList().get(i) + "++++" + lists.get(j).getId());
                        search.add(lists.get(j));
                    }
                }
            }
        }
        LogUtil.v(search.size()+"==================");
        gulv.setText("过滤完成");
        lists.removeAll(search);
        LogUtil.v(lists.size()+"=============");
        adapter1.notifyDataSetChanged();
    }
    String WENJIAN = "文件夹";
    int zhong = 0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZhongZiEntity event) {
        if(zhong == 0){
            setZhongzi(event.getData());
        }
    }
    int posi;
    String title;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchJieguo event) {
        if(shishi_relative.getVisibility() == View.VISIBLE){
            shishi_relative.setVisibility(View.GONE);
        }
        guolv_relative.setVisibility(View.VISIBLE);
            if(qiangli.getText().toString().equals("正在搜索")){
                qiangli.setText("强力搜索");
            }
            lists.clear();

        if(event.getData() != null &&event.getData().size()>0){
            lists.addAll(event.getData());
            shishi_relative.setVisibility(View.GONE);
            gulv.setClickable(true);
        }else {
            gulv.setClickable(false);
            shishi_relative.setVisibility(View.VISIBLE);
        }
        if(adapter1 == null) {
            adapter1 = new TanSuoAdapter1(lists);
        }
            tansuo_list1.setAdapter(adapter1);
            adapter1.setOnClick(new TanSuoAdapter1.OnItemClick() {
                @Override
                public void onItemClick(View v, int position, View view) {
                    posi = position;
                    title = lists.get(position).getFilename();
                    switch (v.getId()) {
                        case R.id.fen:
                            StringBuffer sb = new StringBuffer();

                            sb.append(lists.get(position).getMagnet()+"\n" +
                                    title+"\n"+
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
                            TextView tv = (TextView) view.findViewById(R.id.zhuan);
                            if(tv.getText().toString().equals("查看")){
                                if (lists.get(position).getIsDir()) {

                                    OkHttpData.getZhongzi(lists.get(position).getId() + "", WENJIAN);
                                } else {
                                    ZhuanCun entity1 = new ZhuanCun();
                                    entity1.setGeshi("文件列表");
                                    entity1.setType(LEIXING);
                                    entity1.setUrl(lists.get(position).getShareUrl());
                                    entity1.setPwd("");
                                    LogUtil.v(lists.get(position).getShareUrl());
                                    EventBus.getDefault().post(entity1);
                                }
                            }else {
                                if (isDenglu()) {

                                    ZhuanCun entity = new ZhuanCun();
                                    entity.setGeshi("文件列表");
                                    entity.setType(LEIXING);
                                    entity.setUrl(lists.get(position).getShareUrl());
                                    entity.setPwd("");
                                    EventBus.getDefault().post(entity);
                                } else {
                                    AppToast.showToast("请先登录");
                                    finish();
                                    EventBus.getDefault().post(new DengLu());
                                }
                            }
                           /* try {
                                String url = URLDecoder.decode(lists.get(position).getShareUrl(),"utf-8");
                                LogUtil.v(lists.get(position).getShareUrl()+"========================"+url);

                            }catch (Exception e){

                            }*/

                            break;
                        case R.id.linear_1:
                            if (lists.get(position).getIsDir()) {
                                OkHttpData.getZhongzi(lists.get(position).getId() + "", WENJIAN);
                            } else {
                                if(isDenglu()) {

                                    ZhuanCun entity1 = new ZhuanCun();
                                    entity1.setGeshi("文件列表");
                                    entity1.setType(LEIXING);
                                    entity1.setUrl(lists.get(position).getShareUrl());
                                    entity1.setPwd("");
                                    LogUtil.v(lists.get(position).getShareUrl());
                                    EventBus.getDefault().post(entity1);
                                }else {
                                    AppToast.showToast("请先登录");
                                    finish();
                                    EventBus.getDefault().post(new DengLu());
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
    }
    private void setZhongzi(final List<SearchJson> data){
        zhong = 1;
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

        bang.showAtLocation(sousuo_edit, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                zhong = 0;
            }
        });
        localView.findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
        LinearLayout zhongzi_lin = (LinearLayout) localView.findViewById(R.id.zhongzi_lin);
        LinearLayout zhong_li = (LinearLayout) localView.findViewById(R.id.zhong_li);
        TextView fan = (TextView) localView.findViewById(R.id.fanhui);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView jiaocheng = (TextView) localView.findViewById(R.id.jiaocheng);
        jiaocheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://jingyan.baidu.com/article/e8cdb32b1eb0d337042bad60.html?qq-pf-to=pcqq.c2c");
                intent.setData(content_url);
                startActivity(intent);

            }
        });
        if(isBaitian()){
            setTextBai(jiaocheng);
            setViewBai(v1);
            setBai(zhongzi_lin);
            setViewBai(v);
            setBai(zhong_li);
            setListBai(zhongzi_list);
            setTextBai(fan);
        }else {
            setTextHei(jiaocheng);
            setViewHei(v1);
            setTextHei(fan);
            setViewHei(v);
            setHei1(zhongzi_lin);
           setHei(zhong_li);
            setListHei(zhongzi_list);
        }

        TanSuoAdapter2 tansuo = new TanSuoAdapter2(data);
        zhongzi_list.setAdapter(tansuo);
        tansuo.setOnClick(new TanSuoAdapter2.OnItemClick() {
            @Override
            public void onItemClick(View v, int position, View view) {
                switch (v.getId()) {
                    case R.id.fen:
                        StringBuffer sb = new StringBuffer();

                        sb.append(data.get(position).getMagnet()+"\n" +
                                title+"\n"+
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
                        setDakai(data.get(position));
                        /*TextView tv = (TextView) view.findViewById(R.id.zhuan);
                        if(tv.getText().toString().equals("复制")){

                                // 从API11开始android推荐使用android.content.ClipboardManager
                                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(data.get(position).getMagnet());
                                AppToast.showToast("已复制");




                        }else {
                            setZhuancun(lists.get(position));
                            ZhuanCun entity = new ZhuanCun();
                            entity.setGeshi("文件列表");
                            entity.setType(LEIXING);
                            entity.setUrl(data.get(position).getShareUrl());
                            entity.setPwd("");
                            LogUtil.v(data.get(position).getShareUrl());
                            EventBus.getDefault().post(entity);
                        }*/

                        break;
                    case R.id.tousuo1_linear:
                        /*if (lists.get(position).getShareUrl().isEmpty()) {
                            OkHttpData.getZhongzi(data.get(position).getId() + "", WENJIAN);
                        } else {
                            AppToast.showToast("当前已是文件");
                        }*/
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    //当editview为空时的
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(sousuo_edit.getText().toString().isEmpty()){
                sousuo_text.setText("返回");

                sousuo_linear.setVisibility(View.VISIBLE);
                tansuo_list1.setVisibility(View.GONE);
                gulv.setText("一键过滤");
                guolv_relative.setVisibility(View.GONE);
                sousuo_list.setVisibility(View.GONE);
                c = 0;
            }else {
                sousuo_text.setText("搜索");
                if(c == 1){

                }else {
                    LogUtil.v("dddddddd");
                    sousuo_list.setVisibility(View.VISIBLE);
                    OkHttpData.getKeyword(sousuo_edit.getText().toString());
                }
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub


        //   mTextView.setText(s);//将输入的内容实时显示
        }
        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    private void getList(){

        lisisousuo.setOnItemClickListener(new LiShiLineWrapLayout.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv  = (TextView) view;
                c = 1;
                sousuo_edit.setText(tv.getText().toString());
                sousuo_edit.setSelection(sousuo_edit.getText().length());
                rotview.setFocusable(true);
                rotview.setFocusableInTouchMode(true);
                rotview.requestFocus();
                sousuo_linear.setVisibility(View.GONE);
                tansuo_list1.setVisibility(View.VISIBLE);
                OkHttpData.getSousuo(sousuo_edit.getText().toString());
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sousuo_edit.setFocusable(true);
                sousuo_edit.setFocusableInTouchMode(true);
                sousuo_edit.requestFocus();
                InputMethodManager inputManager = (InputMethodManager)sousuo_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(sousuo_edit, 0);
            }
        },100);

        sousuo_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(b){
                    //  imm.showSoftInput(view,0);

                }else {

                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                }
            }
        });
        sousuo_edit.addTextChangedListener(mTextWatcher);
        rotview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                rotview.setFocusable(true);
                rotview.setFocusableInTouchMode(true);
                rotview.requestFocus();
                return false;
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HitKeywork event) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <event.getData().size() ; i++) {
            list.add(event.getData().get(i).getKeyword());
        }
        remensousuo.setData(list);
        remensousuo.setOnItemClickListener(new LiShiLineWrapLayout.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) view;
                c = 1;
                sousuo_edit.setText(tv.getText().toString());
                sousuo_edit.setSelection(sousuo_edit.getText().length());
                rotview.setFocusable(true);
                rotview.setFocusableInTouchMode(true);
                rotview.requestFocus();
                sousuo_linear.setVisibility(View.GONE);
                tansuo_list1.setVisibility(View.VISIBLE);
                OkHttpData.getSousuo(sousuo_edit.getText().toString());
                setTianjiasousuo();

            }
        });
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                closeSoftKeyboard(TanSuoActivity.this);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }*/
    private void closeSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    List<WenJian> wenJien = new ArrayList<>();
    List<String> wenJien1;
    //获取需要转存的目录文件列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZhuanCunEntity event) {
        if(event.getType().equals(LEIXING)) {
            setZhuancun(lists.get(posi));
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
    SearchJson searchJson;
    private void setZhuancun(SearchJson json){
        searchJson = json;
        bang= new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_zhuancun, null);
        bang.setContentView(localView);
        bang.showAtLocation(sousuo_edit, Gravity.CENTER,0,0);
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

        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wenJien1 = new ArrayList<String>();
                int b = 0;
                for (int i = 0; i < wenJien.size(); i++) {
                    if(wenJien.get(i).isXuan()){
                        wenJien1.add(wenJien.get(i).getPath());
                        b = 1;
                    }
                }
                if(b == 0){
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaoCunChengGong event) {
        if(event.getType().equals(LEIXING)){

            OkHttpData.postZhuan(searchJson.getId());
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
        bang.showAtLocation(sousuo_edit, Gravity.CENTER,0,0);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
        if(adapter1!=null) {
            adapter1.notifyDataSetChanged();
        }

    }
    private void setDakai(final SearchJson data){
        final PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        bang.setAnimationStyle(R.style.AnimationPreview);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_dakai, null);
        bang.setContentView(localView);
        bang.showAtLocation(rotview, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        LinearLayout black = (LinearLayout) localView.findViewById(R.id.kai_lin);
        black.setBackgroundResource(R.color.black);
        black.setAlpha(0.5f);

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
                        startActivityForPackage(TanSuoActivity.this,"com.xunlei.downloadprovider");
                        break;
                    case 1:
                        startActivityForPackage(TanSuoActivity.this,"com.baidu.netdisk");


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
