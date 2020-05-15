package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.AppToast;
import data.DataUtile;
import service.BaiTian;
import view.Daojishi;
import view.MyCountDownTimer;
import zhifubao.AuthResult;
import zhifubao.PayResult;

public class ZhiFuBaoActivity extends BaseActivity {

    @Bind(R.id.zhifu_relative)
    RelativeLayout zhifu_relative;
    @Bind(R.id.zhifu_re)
    RelativeLayout zhifu_re;
    @Bind(R.id.yigeyue)
    TextView yigeyue;
    @Bind(R.id.daoji)
    TextView daoji;
    @Bind(R.id.shuoming)
    TextView shuoming;
    @Bind(R.id.zhifu)
    TextView zhifu;
    @Bind(R.id.zhixu)
    TextView zhixu;
    @Bind(R.id.yuan)
    TextView yuan;
    @Bind(R.id.dengdai)
    TextView dengdai;
    @Bind(R.id.v3)
    View v3;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.v2)
    View v2;
    @Bind(R.id.v)
    View v;
    @Bind(R.id.fan)
    TextView fan;
    @Bind(R.id.deng)
    TextView deng;
    @Bind(R.id.yimai)
    TextView yimai;


    @Bind(R.id.huafei_linear)
    LinearLayout huafei_linear;

    @Bind(R.id.zhuanghao_linear)
    LinearLayout zhuanghao_linear;

    @Bind(R.id.yidong_linear1)
    LinearLayout yidong_linear1;

    @OnClick({R.id.fan,R.id.deng,R.id.yimai,R.id.shuoming,R.id.zhifu})
    public void setView(View v){
        switch (v.getId()){
            case R.id.fan:
                finish();
                break;
            case R.id.deng:
                setDenglu();
                break;
            case R.id.yimai:

                break;
            case R.id.shuoming:
                setShuoming();
                break;

            case R.id.zhifu:

                break;
        }
    }
    Daojishi dao = new Daojishi(300000,1000);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_fu_bao);
        ButterKnife.bind(this);
        setBaitian();
        dao.setBtn_djs(daoji);
        dao.start();
    }
    private void setBaitian(){
        if(isBaitian()){
            zhifu_re.setBackgroundResource(R.color.beiying);
            setBai(zhifu_relative);setTextBai(zhixu);setTextBai(deng);
            setTextBai(yigeyue);setTextBai(yuan);setTextBai(fan);setTextBai(yimai);
            shuoming.setBackgroundResource(R.drawable.ziti);
            zhifu.setBackgroundResource(R.drawable.ziti);
            setTextBai(shuoming);setTextBai(zhifu);setBai(yidong_linear1);
            setViewBai(v); setViewBai(v1); setViewBai(v2); setViewBai(v3);
        }else {
            setViewHei(v);  setViewHei(v1);  setViewHei(v2);  setViewHei(v3);
            shuoming.setTextColor(getResources().getColor(R.color.heiye));setHei(yidong_linear1);
            zhifu.setTextColor(getResources().getColor(R.color.heiye));
            zhifu.setBackgroundResource(R.drawable.ziti1);
            shuoming.setBackgroundResource(R.drawable.ziti1);
            setTextHei(yigeyue);setTextHei(zhixu);setTextHei(fan);setTextHei(deng);
            setHei1(zhifu_re);setTextHei(yuan);setTextHei(yimai);
            setHei(zhifu_relative);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
    }


    /*   @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OrderInfo event) {
        setZhifu(event.getOrderInfo());
    }
*/

    private void setDenglu(){
        final  PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        bang.setAnimationStyle(R.style.AnimationPreview);
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_denglu, null);

        bang.setContentView(localView);
        bang.showAtLocation(fan, Gravity.CENTER,0,0);
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        TextView bangding_text = (TextView) localView.findViewById(R.id.bangding_text);
        final EditText xingming = (EditText) localView.findViewById(R.id.xingming);
        final EditText yanzhengma = (EditText) localView.findViewById(R.id.yanzhengma);
        LinearLayout ding_linear = (LinearLayout) localView.findViewById(R.id.ding_linear);
        View v = (View) localView.findViewById(R.id.v);
        View v1 = (View) localView.findViewById(R.id.v1);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        TextView quxiao = (TextView) localView.findViewById(R.id.quxiao);
      final  TextView huoqu =(TextView) localView.findViewById(R.id.huoqu);

        if(isBaitian()){
            setTextBai(xingming);
            setTextBai(queding);
            setTextBai(quxiao);
            setViewBai(v);
            setViewBai(v1);
            xingming.setBackgroundResource(R.drawable.paihang4);
            yanzhengma.setBackgroundResource(R.drawable.paihang4);
            ding_linear.setBackgroundResource(R.drawable.paihang3);
        }else {
            setTextHei(queding);
            setTextHei(quxiao);
            setViewHei(v);
            setViewHei(v1);
            setTextHei(xingming);
            xingming.setBackgroundResource(R.drawable.paihang7);
            yanzhengma.setBackgroundResource(R.drawable.paihang7);
            ding_linear.setBackgroundResource(R.drawable.paihang6);
        }
        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataUtile.isMobile(xingming.getText().toString())){
                    myCountDownTimer = new MyCountDownTimer(30000,1000);
                    myCountDownTimer.setBtn_djs(huoqu);
                    myCountDownTimer.start();
                }else {
                    AppToast.showToast("请输入正确手机号");
                }
            }
        });

        localView.findViewById(R.id.queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    MyCountDownTimer myCountDownTimer ;
    private void setShuoming(){
        final PopupWindow bang = new PopupWindow(this);
        bang.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bang.setFocusable(true);
        //点击 back 键的时候，窗口会自动消失
        bang.setBackgroundDrawable(new BitmapDrawable());
        bang.setAnimationStyle(R.style.AnimationPreview);
        View localView = LayoutInflater.from(this).inflate(R.layout.popup_shouming, null);
        bang.setContentView(localView);
        bang.showAtLocation(fan, Gravity.CENTER,0,0);
        LinearLayout bangding_linear= (LinearLayout) localView.findViewById(R.id.bangding_linear);
        LinearLayout line= (LinearLayout) localView.findViewById(R.id.line);
        TextView wenben = (TextView) localView.findViewById(R.id.wenben);
        TextView wenben2 = (TextView) localView.findViewById(R.id.wenben2);
        TextView queding = (TextView) localView.findViewById(R.id.queding);
        View v1 = (View) localView.findViewById(R.id.v1);

        if(isBaitian()){
            setBai(line);setViewBai(v1);
            setTextBai(wenben);setTextBai(wenben2);
            bangding_linear.setBackgroundResource(R.drawable.paihang3);
        }else {
            setHei(line);setViewHei(v1);
            setTextHei(wenben);setTextHei(wenben2);
            bangding_linear.setBackgroundResource(R.drawable.paihang6);
        }


        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bang.dismiss();
            }
        });
        //更新窗口状态
        bang.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
       getWindow().setAttributes(lp);

        bang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade,R.anim.fade_out
        );
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ZhiFuBaoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                       /* OkHttpData.getData();
                        OkHttpData1.getChaxunyue();*/
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ZhiFuBaoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(ZhiFuBaoActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(ZhiFuBaoActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void setZhifu(final String orderInfo1){


        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ZhiFuBaoActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo1, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
}
