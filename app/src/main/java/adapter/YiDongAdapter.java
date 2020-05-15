package adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import entity.WenJian;

/**
 * Created by Administrator on 2017/12/16 0016.
 */
public class YiDongAdapter  extends BaseCommAdapter<WenJian> {
    public YiDongAdapter (List<WenJian> data){
        super(data);
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        WenJian item = getItem(position);
        LinearLayout yidong_lin = holder.getItemView(R.id.yidong_lin);
        TextView yidong_name = holder.getItemView(R.id.yidong_name);
        yidong_name.setText(item.getName());

        if(isBaitian()){
            setBai(yidong_lin);
            setTextBai(yidong_name);
        }else {
            setHei(yidong_lin);
            setTextHei(yidong_name);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_yidong;
    }
    public interface OnClick{
        void setOnclick(View v, int position, View view);
    }
    OnClick onClick;
    public void setOnClick( OnClick onClick){
        this.onClick = onClick;
    }
    class MyOnClick implements View.OnClickListener{
        int position;
        View v;
        public MyOnClick( int position, View v){
            this.position = position;
            this.v = v;
        }
        @Override
        public void onClick(View view) {
            onClick.setOnclick(view,position,v);
        }
    }
}
