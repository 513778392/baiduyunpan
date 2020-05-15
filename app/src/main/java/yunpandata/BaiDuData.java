package yunpandata;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/20 0020.
 */
public class BaiDuData implements Serializable{
    int erron;
    String guid_info;
    List<ListData> list;
    long request_id;
    int guid;

    @Override
    public String toString() {
        return "BaiDuData{" +
                "erron=" + erron +
                ", guid_info='" + guid_info + '\'' +
                ", list=" + list +
                ", request_id=" + request_id +
                ", guid=" + guid +
                ", page=" + page +
                '}';
    }

    int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getGuid_info() {
        return guid_info;
    }

    public void setGuid_info(String guid_info) {
        this.guid_info = guid_info;
    }

    public List<ListData> getList() {
        return list;
    }

    public void setList(List<ListData> list) {
        this.list = list;
    }

    public long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(long request_id) {
        this.request_id = request_id;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public int getErron() {
        return erron;
    }

    public void setErron(int erron) {
        this.erron = erron;
    }
}
