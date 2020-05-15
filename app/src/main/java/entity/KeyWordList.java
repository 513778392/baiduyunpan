package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/9 0009.
 */
public class KeyWordList implements Serializable {
    List<Keyword> data;

    public List<Keyword> getData() {
        return data;
    }

    public void setData(List<Keyword> data) {
        this.data = data;
    }
}
