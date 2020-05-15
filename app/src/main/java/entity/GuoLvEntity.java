package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28 0028.
 */
public class GuoLvEntity implements Serializable {
    List<Integer> list;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
