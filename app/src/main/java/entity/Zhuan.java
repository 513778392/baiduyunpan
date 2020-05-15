package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/6 0006.
 */
public class Zhuan implements Serializable {
    List<ZhuancunList> data;

    public List<ZhuancunList> getData() {
        return data;
    }

    public void setData(List<ZhuancunList> data) {
        this.data = data;
    }
}
