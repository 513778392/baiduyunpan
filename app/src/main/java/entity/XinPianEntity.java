package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class XinPianEntity implements Serializable {
   PageBean<Douban> data;
    int pageNumber;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageBean<Douban> getData() {
        return data;
    }

    public void setData(PageBean<Douban> data) {
        this.data = data;
    }
}
