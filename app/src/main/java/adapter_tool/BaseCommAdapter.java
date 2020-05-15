package adapter_tool;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.App;
import com.example.myapplication.R;

import java.util.List;

import data.SharedPreferencesUtils;

/**
 * Created by admin on 2017/7/19.
 */

public abstract  class BaseCommAdapter<T> extends BaseAdapter
{
    private List<T> mDatas;

    public BaseCommAdapter(List<T> datas)
    {
        mDatas = datas;
    }
    @Override
    public int getCount()
    {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = ViewHolder
                .newsInstance(convertView, parent.getContext(), getLayoutId(),parent);

        setUI(holder,position,parent.getContext());

        return holder.getConverView();
    }

    protected abstract void setUI(ViewHolder holder, int position, Context context);

    protected abstract int getLayoutId();


   public boolean isBaitian(){
        return (boolean)SharedPreferencesUtils.getBean(App.app, App.BAITIAN);
    }

    public void setBai (View v){
        v.setBackgroundResource(R.color.white);
    }
    public void setHei (View v){
        v.setBackgroundResource(R.color.zitiyanse3);
    }

    public void setViewBai(View v){
        v.setBackgroundResource(R.color.beiyingd);
    }
    public void setViewHei(View v){
        v.setBackgroundResource(R.color.beiyingdd);
    }
    public void setTextBai(TextView v){
        v.setTextColor(App.app.getResources().getColor(R.color.zitiyanse6));
    }
    public void setTextHei(TextView v){
        v.setTextColor(App.app.getResources().getColor(R.color.white));
    }

}

