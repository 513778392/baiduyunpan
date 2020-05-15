package yunpandata;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/20 0020.
 */
public class YunDengLu implements Serializable {
    String mulu;
    int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getMulu() {
        return mulu;
    }

    public void setMulu(String mulu) {
        this.mulu = mulu;
    }
}
