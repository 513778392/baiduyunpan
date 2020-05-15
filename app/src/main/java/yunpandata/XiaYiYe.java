package yunpandata;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/22 0022.
 */
public class XiaYiYe implements Serializable{
    int page ;
    String mulu;

    public String getMulu() {
        return mulu;
    }

    public void setMulu(String mulu) {
        this.mulu = mulu;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
