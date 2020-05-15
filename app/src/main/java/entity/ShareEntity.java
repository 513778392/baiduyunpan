package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25 0025.
 */
public class ShareEntity implements Serializable {
    String shareId;
    String shareUrl;
    String pwd;

    @Override
    public String toString() {
        return "ShareEntity{" +
                "shareId='" + shareId + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
