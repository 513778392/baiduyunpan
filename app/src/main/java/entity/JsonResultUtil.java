package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28 0028.
 */
public class JsonResultUtil<T> implements Serializable {
    private List<T> data ;
    private Integer error ;
    private String msg ;
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public Integer getError() {
        return error;
    }
    public void setError(Integer error) {
        this.error = error;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JsonResultUtil{" +
                "data=" + data +
                ", error=" + error +
                ", msg='" + msg + '\'' +
                '}';
    }
}
