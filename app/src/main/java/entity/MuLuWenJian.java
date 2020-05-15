package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class MuLuWenJian implements Serializable {
    String path;
    String share;
    String fs_id;

    @Override
    public String toString() {
        return "MuLuWenJian{" +
                "path='" + path + '\'' +
                ", share='" + share + '\'' +
                ", fs_id='" + fs_id + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getFs_id() {
        return fs_id;
    }

    public void setFs_id(String fs_id) {
        this.fs_id = fs_id;
    }
}
