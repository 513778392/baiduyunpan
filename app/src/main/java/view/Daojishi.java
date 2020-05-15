package view;

import android.os.CountDownTimer;
import android.widget.TextView;

import data.LogUtil;

/**
 * Created by Administrator on 2018/1/23 0023.
 */
public class Daojishi extends CountDownTimer {

    TextView btn_djs;
    public Daojishi(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        btn_djs.setClickable(false);
        int i =(int) l/1000;
        btn_djs.setText(time(i));

    }
    public String time (int midTime){
        long hh = midTime / 60 / 60 % 60;
        long mm = midTime / 60 % 60;
        long ss = midTime % 60;
        String h = hh+"";
        if(hh<10){
            h = "0"+hh;
        }
        String m = mm+"";
        if(mm<10){
            m = "0"+mm;
        }
        String s = ss+"";
        if(ss<10){
            s = "0"+ss;
        }
        String sb =  h + ":" + m + ":" + s;
        LogUtil.v(sb);
        return sb;
    }
    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        btn_djs.setText("");
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
