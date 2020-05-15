package adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import data.JavaScript;
import entity.SearchJson;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
public class ChaKanAdapter extends BaseCommAdapter<SearchJson> {
    public ChaKanAdapter (List<SearchJson> data){
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
        name.setText(setString(item.getFilename()));
        TextView time = holder.getItemView(R.id.time);
        TextView fen = holder.getItemView(R.id.fen);
        TextView zhuan = holder.getItemView(R.id.zhuan);
        zhuan.setText("复制");

        wenjj.setImageResource(R.mipmap.bt);

         //   wenjj.setImageResource(JavaScript.getTubiao1(item.getFilename()));
            if(R.mipmap.wenjj != JavaScript.getTubiao1(item.getFilename())){
                time.setText("");
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
    private String setString (String s ){
        String ss = s.replace(" ","");
        return ss;
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

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


}
