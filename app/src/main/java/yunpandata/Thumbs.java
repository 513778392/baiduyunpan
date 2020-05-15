package yunpandata;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/20 0020.
 */
public class Thumbs implements Serializable {
    String icon;
    String url1;
    String url2;
    String url3;

    @Override
    public String toString() {
        return "Thumbs{" +
                "icon='" + icon + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                '}';
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
