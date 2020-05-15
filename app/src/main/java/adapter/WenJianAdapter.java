package adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import data.JavaScript;
import entity.WenJian;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class WenJianAdapter  extends BaseCommAdapter<WenJian> {


    public WenJianAdapter (List<WenJian> data){
        super(data);
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        WenJian item = getItem(position);
        LinearLayout list_wenjian= holder.getItemView(R.id.list_wenjian);
        ImageView wenjj = holder.getItemView(R.id.wenj);
      //  wenjj.setImageResource(item.getImage());
        TextView name = holder.getItemView(R.id.name);
        name.setText(setString(item.getName()));
        TextView time = holder.getItemView(R.id.time);

        time.setText(timet(item.getMtime()));
        RelativeLayout relativeLayout = holder.getItemView(R.id.wenjian_relative);
        ImageView gouxuan_1 = holder.getItemView(R.id.gouxuan_1);
        if(item.isXuan()){
            gouxuan_1.setImageResource(R.mipmap.gouxuan_1);
        }else {
            if(isBaitian()) {
                gouxuan_1.setImageResource(R.mipmap.gouxuan_2);
            }else {
                gouxuan_1.setImageResource(R.mipmap.gouxuan_3);
            }
        }
        relativeLayout.setOnClickListener(new MyOnClick(position,holder.getConverView()));
        holder.getItemView(R.id.wenjian_linear).setOnClickListener(new MyOnClick(position,holder.getConverView()));
        TextView size = holder.getItemView(R.id.size);
        if(item.isdir()){
            wenjj.setImageResource(R.mipmap.wenjj);
            size.setText("");
        }else{

            size.setText(item.getSizeHuman());
            if(item.getThumb().isEmpty()) {
                wenjj.setImageResource(JavaScript.getTubiao(item.getPath()));
            }else {
                Picasso
                        .with(context)
                        .load(item.getThumb())
                        .placeholder(R.mipmap.wenjj)//图片加载中显示
                        .into(wenjj);
            }
        }

        if(isBaitian()){
            setTextBai(name);
            setBai(list_wenjian);
        }else {
            setTextHei(name);
            setHei(list_wenjian);
        }
    }
    public static String timet(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
    private String setString (String s ){
        String ss = s.replace(" ","");
        return ss;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.list_wenjian;
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
