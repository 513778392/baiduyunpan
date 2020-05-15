package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/16 0016.
 */
public class BaiduUser implements Serializable {
    String name;
    String touxiang;

    @Override
    public String toString() {
        return "BaiduUser{" +
                "name='" + name + '\'' +
                ", touxiang='" + touxiang + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }
}
