package fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.App;
import com.example.myapplication.R;

import data.SharedPreferencesUtils;
import view.TypefaceUtil;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TypefaceUtil.replaceFont(getActivity(), "fonts/MS.ttf");
        return container;
        }

    /**是否登录成功*/
    public boolean isDenglu (){
        return (boolean) SharedPreferencesUtils.getBean(getContext(),"user");
    }
     boolean isBaitian(){
        return (boolean) SharedPreferencesUtils.getBean(getActivity(), App.BAITIAN);
    }
      void setBai (View v){
        v.setBackgroundResource(R.color.white);
    }
    public void setTextBai(TextView v){
        v.setTextColor(App.app.getResources().getColor(R.color.zitiyanse6));
    }
    public void setTextHei(TextView v){
        v.setTextColor(App.app.getResources().getColor(R.color.white));
    }
      void setHei (View v){
        v.setBackgroundResource(R.color.zitiyanse3);
    }
    void setHei1 (View v){
        v.setBackgroundResource(R.color.heiye);
    }

    void setViewBai(View v){
        v.setBackgroundResource(R.color.beiyingd);
    }
    void setViewHei(View v){
        v.setBackgroundResource(R.color.beiyingdd);
        }
    void setListBai(ListView v){
        v.setDivider(new ColorDrawable(getResources().getColor(R.color.beiyingd)));
        v.setDividerHeight(1);
    }
    void setListHei(ListView v){
        v.setDivider(new ColorDrawable(getResources().getColor(R.color.beiyingdd)));
        v.setDividerHeight(1);
    }

    }

