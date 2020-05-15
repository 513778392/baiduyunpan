package entity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class TanSuoEntity {

    String name;
    int image;
    List<TanSuoEntity> list;
    String url;
    String pwd;
    public List<TanSuoEntity> getList(){
        list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            TanSuoEntity entity = new TanSuoEntity();
            entity.setImage(R.mipmap.wenjj);
            entity.setName("秦时明月");

            list.add(entity);
        }

        return list;
    }
    public List<TanSuoEntity> wode(){
        list = new ArrayList<>();
        list.add(new TanSuoEntity("百度云盘", R.mipmap.baiduyun));
        list.add(new TanSuoEntity("新片预定", R.mipmap.yuding));
        list.add(new TanSuoEntity("鬼畜视频", R.mipmap.guicu));
      //  list.add(new TanSuoEntity("分享探索", R.mipmap.fxtansuo));
        //list.add(new TanSuoEntity("加QQ群", R.mipmap.qqqun));
       // list.add(new TanSuoEntity("支付宝红包", R.mipmap.zfb));


        return list;
    }

    public List<TanSuoEntity> guicu() {
        list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            TanSuoEntity entity = new TanSuoEntity();
            entity.setImage(R.mipmap.wenjj);
            entity.setUrl("https://pan.baidu.com/s/1gfo21qV?qq-pf-to=pcqq.c2c#list/path=%2F");
            entity.setName("秦时明月");
            entity.setPwd("");
            list.add(entity);
        }
        return list;
    }


    public TanSuoEntity(String name, int image){
        this.name = name;
        this.image = image;
    }
    public TanSuoEntity(){

    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
