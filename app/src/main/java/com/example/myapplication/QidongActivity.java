package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QidongActivity extends BaseActivity {
    boolean dingshi = true;
    RelativeLayout qidong;
    int  i = 4;
    TextView daojishi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qidong);
        qidong = (RelativeLayout) findViewById(R.id.qidong);
        daojishi = (TextView) findViewById(R.id.daojishi);
        if(isBaitian()){
            qidong.setBackgroundResource(R.mipmap.baitian);
            setTextBai(daojishi);
        }else {
            qidong.setBackgroundResource(R.mipmap.heiye);
            setTextHei(daojishi);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (dingshi){
                    try {
                        i--;
                        if(i<=0){
                            handler.sendMessage(new Message());
                            dingshi = false;
                            startActivity(new Intent(QidongActivity.this,MainActivity.class));
                            finish();
                        }
                        handler.sendMessage(new Message());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            daojishi.setText("跳转倒计时"+i);
        }
    };
}
