package adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import entity.Keyword;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
public class SousuoAdapter extends BaseCommAdapter<Keyword> {

    public SousuoAdapter(List<Keyword> data){
        super(data);
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        Keyword item = getItem(position);
        TextView tv = holder.getItemView(R.id.tv);
        tv.setText(item.getKeyword());
        LinearLayout sousuo_lin = holder.getItemView(R.id.sousuo_lin);

        if(isBaitian()){
            setBai(sousuo_lin);
            setTextBai(tv);
        }else {
            setHei(sousuo_lin);
            setTextHei(tv);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_sousuo;
    }
    /**在头部增加一条数据
     * @param info
     */
    List<String> list;
    public void addData(String info){
        list.add(0, info);
    }

    /**在头部增加多条数据
     * @param infos
     */
    public void addAllData(List<String> infos){
        list = new ArrayList<>();
        this.list.addAll(0, infos);
    }
}
