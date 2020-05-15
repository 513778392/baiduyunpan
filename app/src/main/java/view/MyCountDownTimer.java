package view;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class MyCountDownTimer extends CountDownTimer {

    TextView btn_djs;
    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        btn_djs.setClickable(false);
        btn_djs.setText(l/1000+"s");

    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        btn_djs.setText("获取验证码");
        //设置可点击
        btn_djs.setClickable(true);
    }

    public TextView getBtn_djs() {
        return btn_djs;
    }

    public void setBtn_djs(TextView btn_djs) {
        this.btn_djs = btn_djs;
    }
}
