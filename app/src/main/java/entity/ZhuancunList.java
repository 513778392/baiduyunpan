package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/6 0006.
 */
public class ZhuancunList implements Serializable {
    private Integer id ;
    private String title ;
    private String ext ;
    private String url ;
    private Integer isDir ;
    private Integer flag ;
    private Integer count ;
    private String releaseDate;
    private Long size ;
    private Integer filmId ;
    private Integer itemId ;
    private String formatTime ;

    public String getFormatTime() {
        return formatTime;
    }
    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getExt() {
        return ext;
    }
    public void setExt(String ext) {
        this.ext = ext;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getIsDir() {
        return isDir;
    }
    public void setIsDir(Integer isDir) {
        this.isDir = isDir;
    }
    public Integer getFlag() {
        return flag;
    }
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public Integer getFilmId() {
        return filmId;
    }
    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }
    /**
     * 返回当前时间距离转存时间后3天的秒数
     * @return
     */
    public long getLife() {
        long dateTemp1 = 0L;
        try {
            Date release = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(releaseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(release);
            calendar.add(Calendar.DATE, 3);
            long reset = calendar.getTime().getTime();

            long dateDiff = reset - System.currentTimeMillis()  ;

            dateTemp1  = dateDiff / 1000; //获取秒数

        }catch (Exception e){

        }
        return dateTemp1 ;
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

    @Override
    public String toString() {
        return "ZhuancunList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ext='" + ext + '\'' +
                ", url='" + url + '\'' +
                ", isDir=" + isDir +
                ", flag=" + flag +
                ", count=" + count +
                ", releaseDate='" + releaseDate + '\'' +
                ", size=" + size +
                ", filmId=" + filmId +
                ", itemId=" + itemId +
                ", formatTime='" + formatTime + '\'' +
                '}';
    }
}
