package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28 0028.
 */
public class SearchJieguo implements Serializable {
    List<SearchJson> data;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SearchJson> getData() {
        return data;
    }

    public void setData(List<SearchJson> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchJieguo{" +
                "data=" + data +
                '}';
    }
}
