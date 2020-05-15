import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import data.LogUtil;
import entity.TanSuoEntity;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class WoDeAdapter extends BaseCommAdapter<TanSuoEntity> {
    public WoDeAdapter(List<TanSuoEntity> data){
        super(data);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        TanSuoEntity item = getItem(position);
        LinearLayout list_linear = holder.getItemView(R.id.list_linear);
        TextView mingcheng = holder.getItemView(R.id.mingcheng);
        mingcheng.setText(item.getName());
        ImageView tubiao = holder.getItemView(R.id.tubiao);
        tubiao.setImageResource(item.getImage());

        if(isBaitian()){
            setTextBai(mingcheng);
            setBai(list_linear);
        }else {
            setHei(list_linear);
            setTextHei(mingcheng);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_wode;
    }
}
