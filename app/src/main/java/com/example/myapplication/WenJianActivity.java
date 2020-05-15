package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import adapter.WenJianAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import entity.TanSuoEntity;

public class WenJianActivity extends BaseActivity {

    @Bind(R.id.wenjian_list)
    ListView wenjian_list;

    TanSuoEntity entity = new TanSuoEntity();
    List<TanSuoEntity> list;
    WenJianAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_jian);
        ButterKnife.bind(this);
        list = entity.getList();
      /*  adapter = new WenJianAdapter(list);
        wenjian_list.setAdapter(adapter);*/

    }
}
