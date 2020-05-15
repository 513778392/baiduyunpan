package adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import entity.BanBen;

/**
 * Created by Administrator on 2017/12/30 0030.
 */
public class BanBenAdapter extends BaseCommAdapter<BanBen> {
    public BanBenAdapter(List<BanBen> data){
        super(data);
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        BanBen item = getItem(position);
        TextView banben =  holder.getItemView(R.id.banben);
        banben.setText(item.getBanben());
        ImageView gouxuan = holder.getItemView(R.id.gouxuan);
        gouxuan.setVisibility(View.VISIBLE);
        TextView chakan = holder.getItemView(R.id.chakan);

        if(item.isGouXuan()){
            gouxuan.setImageResource(R.mipmap.gouxuan_1);
        }else {
            gouxuan.setImageResource(R.mipmap.gouxuan_2);
        }
        if(isBaitian()){
            setTextBai(banben);
        }else {
            setTextHei(banben);
        }
        if(item.getImg()!=null){
            gouxuan.setVisibility(View.GONE);
            chakan.setVisibility(View.VISIBLE);

        }else {
            gouxuan.setVisibility(View.VISIBLE);
            chakan.setVisibility(View.GONE);

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_banben;
    }
}
