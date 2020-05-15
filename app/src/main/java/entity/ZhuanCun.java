package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class ZhuanCun implements Serializable {
    String url;
    String pwd;
    //类型
    String type;
    //格式
    String geshi;

    public String getGeshi() {
        return geshi;
    }

    public void setGeshi(String geshi) {
        this.geshi = geshi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
