package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16 0016.
 */
public class WenjianEntity<T> implements Serializable {
    int errno;
    String msg;
    List<T> data;
    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
