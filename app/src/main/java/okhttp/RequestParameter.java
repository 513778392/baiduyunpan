package okhttp;

/**
 * Created by Administrator on 2017/9/1 0001.
 */
public class RequestParameter {
    private String key;
    private Object obj;

    public RequestParameter(String key, Object obj) {
        this.key = key;
        this.obj = obj;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
