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
import entity.Douban;
import view.MLImageView;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class XinPianAdapter extends BaseCommAdapter<Douban> {
    public XinPianAdapter(List<Douban> data){
        super(data);
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        Douban item = getItem(position);
        TextView name= holder.getItemView(R.id.name);
        name.setText(item.getTitle());
        TextView pinfen = holder.getItemView(R.id.pinfen);
        if(item.getPingfen().isEmpty()){
            pinfen.setText("");
        }else {
            pinfen.setText("豆瓣 " + item.getPingfen());
        }
        TextView time = holder.getItemView(R.id.shijian);
        time.setText(item.getReleaseDate());
        MLImageView imageurl = holder.getItemView(R.id.imageurl);

        TextView yituisong = holder.getItemView(R.id.yituisong);
        ImageView istuisong = holder.getItemView(R.id.istuisong);
        if(item.isFlag()){
            yituisong.setVisibility(View.VISIBLE);
            istuisong.setImageResource(R.mipmap.gouxuan_1);
        }else {
            istuisong.setImageResource(R.mipmap.gouxuan_2);
            yituisong.setVisibility(View.GONE);
        }

        LinearLayout xin_linear = holder.getItemView(R.id.xin_linear);
        if(isBaitian()){
            setBai(xin_linear);
            setTextBai(name);

            Picasso
                    .with(context)
                    .load(item.getImagePath())
                    .placeholder(R.mipmap.img_moren1)//图片加载中显示
                    .into(imageurl);
        }else {
            setTextHei(name);
            setHei(xin_linear);

            Picasso
                    .with(context)
                    .load(item.getImagePath())
                    .placeholder(R.mipmap.img_moren1_yejian)//图片加载中显示
                    .into(imageurl);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_xinpian;
    }
   /* public interface OnItemClick{
        void onItemClick(View v, int position,View view);
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
    }*/

}
