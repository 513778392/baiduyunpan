package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import adapter.YiDongAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import entity.WenJian;

public class YiDongActivity extends AppCompatActivity {

    @Bind(R.id.yidong_list)
    ListView yidong_list;
    List<WenJian> list;
    YiDongAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_dong);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        setList();
        adapter = new YiDongAdapter(list);
        yidong_list.setAdapter(adapter);
    }

    private void setList(){
        for (int i = 0; i <10 ; i++) {
            WenJian wenjian = new WenJian();
            wenjian.setName("宝贝"+i);
            list.add(wenjian);
        }
    }
}
