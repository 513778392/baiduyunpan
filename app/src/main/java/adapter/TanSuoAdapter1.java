package adapter;

import android.content.Context;
import android.text.Html;
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
 * Created by Administrator on 2017/12/8 0008.
 */
public class TanSuoAdapter1 extends BaseCommAdapter<SearchJson> {
    public TanSuoAdapter1 (List<SearchJson> data){
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
        String str = item.getFilename().replace("^#__","<font color='#39d688'><span>").replace("__#$","</span></font>");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            name.setText(Html.fromHtml(str,Html.FROM_HTML_MODE_LEGACY));
        } else {
            name.setText(Html.fromHtml(str));
        }

        TextView time = holder.getItemView(R.id.time);
        TextView fen = holder.getItemView(R.id.fen);
        TextView zhuan = holder.getItemView(R.id.zhuan);

        if(item.getIsDir()){
            zhuan.setText("查看");
            fen.setVisibility(View.GONE);
            time.setText("磁力文件夹");
        }else {
            zhuan.setText("转存");
            fen.setVisibility(View.VISIBLE);
            wenjj.setImageResource(JavaScript.getTubiao1(item.getFilename()));
            time.setText("云盘文件夹");
            time.setVisibility(View.VISIBLE);
            if(R.mipmap.wenjj != JavaScript.getTubiao1(item.getFilename())){
                time.setText("");
            }
        }
        LinearLayout  tansuo1_linear = holder.getItemView(R.id.tansuo1_linear);
        LinearLayout linear_1 = holder.getItemView(R.id.linear_1);

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


        holder.getItemView(R.id.fen).setOnClickListener(new MyOnClick(position,holder.getConverView()));
        holder.getItemView(R.id.zhuan).setOnClickListener(new MyOnClick(position,holder.getConverView()));
        holder.getItemView(R.id.linear_1).setOnClickListener(new MyOnClick(position,holder.getConverView()));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_tansuo1;
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
