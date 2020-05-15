package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class BaoCunEntity implements Serializable {
     String wenjianlujing;
    String mulu;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWenjianlujing() {
        return wenjianlujing;
    }

    public void setWenjianlujing(String wenjianlujing) {
        this.wenjianlujing = wenjianlujing;
    }

    public String getMulu() {
        return mulu;
    }

    public void setMulu(String mulu) {
        this.mulu = mulu;
    }
}
