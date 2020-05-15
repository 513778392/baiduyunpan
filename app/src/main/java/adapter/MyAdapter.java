package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import data.LogUtil;
import data.SharedPreferencesUtils;
import entity.Bibili;
import okhttp.OkHttpData;
import view.RoundedImageView;

/**
 * Created by Administrator on 2017/10/24 0024.
 */
public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private List<Bibili> mDatas;

    //创建构造参数
    public MyAdapter(Context context, List<Bibili> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;

        inflater = LayoutInflater.from(context);
    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = inflater.inflate(R.layout.list_guicu, parent, false);
        viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //为textview 赋值
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.name.setText(mDatas.get(position).getTitle());
        myViewHolder.guicu_relative.setOnClickListener(new MyOnClick(position, myViewHolder.guicu_relative));
        myViewHolder.time.setText(mDatas.get(position).getReleaseDate());
        if ((boolean) SharedPreferencesUtils.getBean(mContext, App.BAITIAN)) {
            myViewHolder.guicu_relative.setBackgroundResource(R.mipmap.guicu_bg);
            Picasso
                    .with(mContext)
                    .load(OkHttpData.wang + mDatas.get(position).getImagePath())
                    .placeholder(R.mipmap.img_moren)//图片加载中显示
                    .into(myViewHolder.tv);
            myViewHolder.name.setTextColor(mContext.getResources().getColor(R.color.zitiyanse3));
        } else {
            myViewHolder.guicu_relative.setBackgroundResource(R.mipmap.guicu_bg2);
            myViewHolder.name.setTextColor(mContext.getResources().getColor(R.color.white));
            Picasso
                    .with(mContext)
                    .load(OkHttpData.wang + mDatas.get(position).getImagePath())
                    .placeholder(R.mipmap.img_moren_yejian)//图片加载中显示
                    .into(myViewHolder.tv);
        }
        LogUtil.v(OkHttpData.wang + mDatas.get(position).getImagePath());

        if (position != mDatas.size() - 1) {

        } else {

            //最后一个控件进行的数据操作，就是回调接口,Activity加载数据
            if (onRecyclerBottomListener != null)
                onRecyclerBottomListener.onRecyclerBottomListener();
        }
    }

    @Override
    public int getItemCount() {
        //Log.i("TAG", "mDatas "+mDatas);
        return mDatas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, View v);
    }

    OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class MyOnClick implements View.OnClickListener {

        int position;
        View v;

        public MyOnClick(int position, View v) {
            this.v = v;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, position, v);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView tv;
        TextView time, name;
        RelativeLayout guicu_relative;

        public MyViewHolder(View itemView) {
            super(itemView);
            guicu_relative = (RelativeLayout) itemView.findViewById(R.id.guicu_relative);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            tv = (RoundedImageView) itemView.findViewById(R.id.sunwukong);

        }
    }

    //滑动到底部的监听接口
    public interface OnRecyclerBottomListener {
        void onRecyclerBottomListener();
    }

    private OnRecyclerBottomListener onRecyclerBottomListener;

    public void setOnRecyclerViewBottomListener(OnRecyclerBottomListener onRecyclerBottomListener) {
        this.onRecyclerBottomListener = onRecyclerBottomListener;
    }
}


