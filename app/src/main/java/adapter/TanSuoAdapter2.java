package adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import data.JavaScript;
import entity.SearchJson;

/**
 * Created by Administrator on 2017/12/29 0029.
 */
public class TanSuoAdapter2 extends BaseCommAdapter<SearchJson> {
    public TanSuoAdapter2 (List<SearchJson> data){
        super(data);
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        SearchJson item = getItem(position);
        ImageView wenjj = holder.getItemView(R.id.wenjj);
        if(item.getImg().isEmpty()){

        }else {
            Picasso
                    .with(context)
                    .load(item.getImg())
                    .placeholder(R.mipmap.wenjj)//图片加载中显示
                    .into(wenjj);
        }
        TextView name = holder.getItemView(R.id.name);
        name.setText(item.getFilename());
        TextView time = holder.getItemView(R.id.time);
        TextView time1 = holder.getItemView(R.id.time1);

           // wenjj.setImageResource(JavaScript.getTubiao1(item.getFilename()));

            wenjj.setImageResource(R.mipmap.bt);
            if(R.mipmap.wenjj != JavaScript.getTubiao1(item.getFilename())){
                time.setText("");
            }
        if(item.getFormat().equals("--")){
            time.setText("");
        }else {

            time.setText(item.getFormat());

        }
        if(item.getFormatFlag()){
            time1.setTextColor(context.getResources().getColor(R.color.qianshou));
            time.setTextColor(context.getResources().getColor(R.color.qianshou));
        }else {
            time1.setTextColor(context.getResources().getColor(R.color.zitiyanse9));
            time.setTextColor(context.getResources().getColor(R.color.zitiyanse9));
        }
        time1.setText(item.getSizeHuman()+"");

        TextView fen = holder.getItemView(R.id.fen);
        TextView zhuan = holder.getItemView(R.id.zhuan);
        fen.setOnClickListener(new MyOnClick(position,holder.getConverView()));


        zhuan.setOnClickListener(new MyOnClick(position,holder.getConverView()));
        holder.getItemView(R.id.tousuo1_linear).setOnClickListener(new MyOnClick(position,holder.getConverView()));
        LinearLayout tansuo1_linear = holder.getItemView(R.id.tansuo1_linear);



        if(isBaitian()){
            fen.setBackgroundResource(R.drawable.ziti);
            zhuan.setBackgroundResource(R.drawable.ziti);
            setBai(tansuo1_linear);
            setTextBai(name);
        }else {
            zhuan.setBackgroundResource(R.drawable.ziti1);
            fen.setBackgroundResource(R.drawable.ziti1);
            setHei(tansuo1_linear);
            setTextHei(name);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_tansuo2;
    }
    public interface OnItemClick{
        void onItemClick(View v, int position, View view);
    }
    OnItemClick onItemClick;
    public void setOnClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
    class MyOnClick implements View.OnClickListener{
        int position;
        View v;
        public MyOnClick(int position,View v){
            this.position = position;
            this.v = v;
        }
        @Override
        public void onClick(View view) {
            onItemClick.onItemClick(view,position,v);
        }
    }
}
