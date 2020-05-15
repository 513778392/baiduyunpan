package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/29 0029.
 */
public class ZhongZiEntity implements Serializable {
    String type;
    List<SearchJson> data;

    public List<SearchJson> getData() {
        return data;
    }

    public void setData(List<SearchJson> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
