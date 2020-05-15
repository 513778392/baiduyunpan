package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class MuLuEntity implements Serializable {
    String errno;
    String guid_info;
    List<MuLuWenJian> list;

    @Override
    public String toString() {
        return "MuLuEntity{" +
                "errno='" + errno + '\'' +
                ", guid_info='" + guid_info + '\'' +
                ", list=" + list +
                '}';
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getGuid_info() {
        return guid_info;
    }

    public void setGuid_info(String guid_info) {
        this.guid_info = guid_info;
    }

    public List<MuLuWenJian> getList() {
        return list;
    }

    public void setList(List<MuLuWenJian> list) {
        this.list = list;
    }
}
