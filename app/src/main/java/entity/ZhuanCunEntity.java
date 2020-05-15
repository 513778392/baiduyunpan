package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class ZhuanCunEntity implements Serializable {

    List<WenJian> data;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<WenJian> getData() {
        return data;
    }

    public void setData(List<WenJian> data) {
        this.data = data;
    }
}
