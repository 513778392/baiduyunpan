package adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import entity.BanBen;

/**
 * Created by Administrator on 2018/1/16 0016.
 */
public class DaKaiAdapter extends BaseCommAdapter<BanBen> {


    public DaKaiAdapter(List<BanBen> data){
        super(data);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        BanBen item = getItem(position);
        ImageView tubiao = holder.getItemView(R.id.tubiao);
        tubiao.setImageResource(item.getImage());
        TextView xunlei = holder.getItemView(R.id.xunlei);
        xunlei.setText(item.getBanben());
        LinearLayout beiying = holder.getItemView(R.id.beiying);

        if(isBaitian()){
            setTextBai(xunlei);
            setBai(beiying);
        }else {
            setHei(xunlei);
            setHei(beiying);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_dakai;
    }
}
