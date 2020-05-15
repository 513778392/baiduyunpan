package yunpandata;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/20 0020.
 */
public class ListData implements Serializable {
    long server_mtime;
    int category;
    int unlist;
    long fs_id;
    long oper_id;
    long server_ctime;
    int isdir;
    long local_mtime;
    long size;
    Thumbs thumbs;
    int share;
    String md5;
    String path;
    long local_ctime;
    String server_filename;
    boolean isXuan;
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
    public boolean isXuan() {
        return isXuan;
    }

    public void setXuan(boolean xuan) {
        isXuan = xuan;
    }

    @Override
    public String toString() {
        return "ListData{" +
                "server_mtime=" + server_mtime +
                ", category=" + category +
                ", unlist=" + unlist +
                ", fs_id=" + fs_id +
                ", oper_id=" + oper_id +
                ", server_ctime=" + server_ctime +
                ", isdir=" + isdir +
                ", local_mtime=" + local_mtime +
                ", size=" + size +
                ", thumbs=" + thumbs +
                ", share=" + share +
                ", md5='" + md5 + '\'' +
                ", path='" + path + '\'' +
                ", local_ctime=" + local_ctime +
                ", server_filename='" + server_filename + '\'' +
                '}';
    }

    public long getServer_mtime() {
        return server_mtime;
    }

    public void setServer_mtime(long server_mtime) {
        this.server_mtime = server_mtime;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getUnlist() {
        return unlist;
    }

    public void setUnlist(int unlist) {
        this.unlist = unlist;
    }

    public long getFs_id() {
        return fs_id;
    }

    public void setFs_id(long fs_id) {
        this.fs_id = fs_id;
    }

    public long getOper_id() {
        return oper_id;
    }

    public void setOper_id(long oper_id) {
        this.oper_id = oper_id;
    }

    public long getServer_ctime() {
        return server_ctime;
    }

    public void setServer_ctime(long server_ctime) {
        this.server_ctime = server_ctime;
    }

    public int getIsdir() {
        return isdir;
    }

    public void setIsdir(int isdir) {
        this.isdir = isdir;
    }

    public long getLocal_mtime() {
        return local_mtime;
    }

    public void setLocal_mtime(long local_mtime) {
        this.local_mtime = local_mtime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Thumbs getThumbs() {
        return thumbs;
    }

    public void setThumbs(Thumbs thumbs) {
        this.thumbs = thumbs;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLocal_ctime() {
        return local_ctime;
    }

    public void setLocal_ctime(long local_ctime) {
        this.local_ctime = local_ctime;
    }

    public String getServer_filename() {
        return server_filename;
    }

    public void setServer_filename(String server_filename) {
        this.server_filename = server_filename;
    }
}
