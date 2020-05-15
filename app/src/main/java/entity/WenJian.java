package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/16 0016.
 */
public class WenJian implements Serializable {
    long fs_id;
    boolean isdir;
    String name;
    String mtime;
    String path;
    int size;
    String thumb;
    boolean isXuan;

    @Override
    public String toString() {
        return "WenJian{" +
                "fs_id='" + fs_id + '\'' +
                ", isdir=" + isdir +
                ", name='" + name + '\'' +
                ", mtime='" + mtime + '\'' +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", thumb='" + thumb + '\'' +
                ", isXuan=" + isXuan +
                '}';
    }

    public boolean isXuan() {
        return isXuan;
    }

    public void setXuan(boolean xuan) {
        isXuan = xuan;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public long getFs_id() {
        return fs_id;
    }

    public void setFs_id(long fs_id) {
        this.fs_id = fs_id;
    }

    public boolean isdir() {
        return isdir;
    }

    public void setIsdir(boolean isdir) {
        this.isdir = isdir;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getSizeHuman() {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        //df.format(((float)getSize()) / 1024 /  1024) ;
        if(getSize() == 0) {
            return "0b" ;
        }
        if((getSize()) < 1000) {
            return (df.format(((float)getSize())) + "B");
        }else if((getSize() / 1024) < 1000) { //KB
            return (df.format(((float)getSize()) / 1024) + "KB");
        }else if((getSize() / 1024 / 1024) < 1000) { //MB
            return (df.format(((float)getSize()) / 1024 /  1024) + "MB");
        }else if((getSize() / 1024 / 1024 / 1024) < 1000) {
            return (df.format(((float)getSize()) / 1024 /  1024 / 1024) + "GB");
        }

        return "--" ;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
