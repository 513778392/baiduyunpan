package entity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/30 0030.
 */
public class BanBen {
    String banben;
    String img;
    boolean isGouXuan;
    int image;
    public boolean isGouXuan() {
        return isGouXuan;
    }

    public void setGouXuan(boolean gouXuan) {
        isGouXuan = gouXuan;
    }

    List<BanBen> list;

    public List<BanBen> getList() {
        list = new ArrayList<>();
        list.add(new BanBen("枪版（一周左右）"));
        list.add(new BanBen("1080P版（一个月以上）"));
        list.add(new BanBen("蓝光版（6GB以上大小）"));
        return list;
    }
    public BanBen(String banben ){
        this.banben = banben;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public BanBen(String banben , int image){
        this.banben = banben;
        this.image = image;
    }
    public BanBen(){

    }
    public List<BanBen> getData() {
        list = new ArrayList<>();
        list.add(new BanBen("迅雷", R.mipmap.xunlei));
        list.add(new BanBen("百度云盘", R.mipmap.big_bdy_2));
        list.add(new BanBen("复制磁力链接", R.mipmap.lianjie));
        return list;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBanben() {
        return banben;
    }

    public void setBanben(String banben) {
        this.banben = banben;
    }
}
