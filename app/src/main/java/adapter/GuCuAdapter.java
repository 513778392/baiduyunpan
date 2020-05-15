package adapter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import entity.Bibili;
import view.RoundedImageView;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class GuCuAdapter extends BaseCommAdapter<Bibili> {
    public GuCuAdapter (List<Bibili> data){
        super(data);
    }
    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        Bibili item = getItem(position);
        RoundedImageView sunwukong = holder.getItemView(R.id.sunwukong);


        RelativeLayout guicu_relative = holder.getItemView(R.id.guicu_relative);

        TextView time = holder.getItemView(R.id.time);
        TextView name = holder.getItemView(R.id.name);
        time.setText(item.getReleaseDate());
        name.setText(item.getTitle());
        if(isBaitian()) {
            guicu_relative.setBackgroundResource(R.mipmap.guicu_bg);
            Picasso
                    .with(context)
                    .load(item.getImagePath())
                    .placeholder(R.mipmap.img_moren)//图片加载中显示
                    .into(sunwukong);

        }else {

            guicu_relative.setBackgroundResource(R.mipmap.guicu_bg2);
            Picasso
                    .with(context)
                    .load(item.getImagePath())
                    .placeholder(R.mipmap.img_moren_yejian)//图片加载中显示
                    .into(sunwukong);

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_guicu;
    }
}
