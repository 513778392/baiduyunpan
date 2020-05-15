package adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import data.JavaScript;
import entity.ZhuancunList;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class TanSuoAdapter extends BaseCommAdapter<ZhuancunList> {
    public TanSuoAdapter (List<ZhuancunList> data){
        super(data);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        ZhuancunList item = getItem(position);
        LinearLayout tansuo_lin = holder.getItemView(R.id.tansuo_lin);
        TextView weizhi = holder.getItemView(R.id.weizhi);
        weizhi.setText((position+1)+"");
        ImageView wenjj = holder.getItemView(R.id.wenjj);
        TextView time = holder.getItemView(R.id.time);
        TextView size = holder.getItemView(R.id.size);
        if(item.getIsDir() == 0){
            wenjj.setImageResource(JavaScript.getTubiao1(item.getTitle()));

        }else {

            wenjj.setImageResource(R.mipmap.wenjj);
        }
        size.setText("");
        time.setText(setTime(item.getLife()));
        TextView name = holder.getItemView(R.id.name);
        name.setText(item.getTitle());
        TextView zhuan = holder.getItemView(R.id.zhuan);
        holder.getItemView(R.id.linear).setOnClickListener(new MyOnClick(position,holder.getConverView()));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_tansuo;
    }

    public interface OnItemClick{
        void onItemClick(View v, int position, View view);
    }
    OnItemClick onItemClick;
    public void setOnClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
    class MyOnClick implements View.OnClickListener {
        int position;
        View v;

        public MyOnClick(int position, View v) {
            this.position = position;
            this.v = v;
        }

        @Override
        public void onClick(View view) {
            onItemClick.onItemClick(view, position, v);
        }
    }
    private String setTime(long totalMiao) {
        long miao = totalMiao % 60; //取得显示的秒

        long totalFen = totalMiao / 60; //总分数
        long fen = totalFen % 60; //显示的分

        long totalShi = totalFen / 60; //总小时

        if (totalShi >= 24) {
            //大于一天
            long tian = totalShi / 24;
            return tian + "天";
        } else {
            String miaoStr = miao + "";
            String fenStr = fen + "";
            String shiStr = totalShi + "";
            if (miao < 10) {
                miaoStr = "0" + miaoStr;
            }
            if (fen < 10) {
                fenStr = "0" + fenStr;
            }
            if (totalShi < 10) {
                shiStr = "0" + shiStr;
            }
            return shiStr + ":" + fenStr + ":" + miaoStr;
        }
    }



}
