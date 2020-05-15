package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class ZhuanCunLuJing implements Serializable {
    String path;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
