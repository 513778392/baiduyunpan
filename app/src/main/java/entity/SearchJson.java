package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/28 0028.
 */
public class SearchJson implements Serializable {
    private Boolean formatFlag;
    private String doubanUrl ; //豆瓣链接
    private String fileext ; //文件的扩展名
    private Integer id ;  //id
    private boolean isDir ; //是否是目录
    private String filename ; //title加日期
    private String format ; //format
    private boolean goodSuccess ; //是否好评
    private String img ; //图片地址
    private String magnet ; //
    private String shareUrl ; //
    private Long size ; //大小
    private String sizeHuman ; //格式化大小

    public Boolean getFormatFlag() {
        return formatFlag;
    }

    public void setFormatFlag(Boolean formatFlag) {
        this.formatFlag = formatFlag;
    }

    public String getDoubanUrl() {
        return doubanUrl;
    }
    public void setDoubanUrl(String doubanUrl) {
        this.doubanUrl = doubanUrl;
    }
    public String getFileext() {
        return fileext;
    }
    public void setFileext(String fileext) {
        this.fileext = fileext;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setIsDir(boolean isDir) {
        this.isDir = isDir ;
    }
    public boolean getIsDir() {
        return this.isDir ;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public boolean isGoodSuccess() {
        return goodSuccess;
    }
    public void setGoodSuccess(boolean goodSuccess) {
        this.goodSuccess = goodSuccess;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getMagnet() {
        return magnet;
    }
    public void setMagnet(String magnet) {
        this.magnet = magnet;
    }
    public String getShareUrl() {
        return shareUrl;
    }
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public String getSizeHuman() {
        return sizeHuman;
    }

    public void setSizeHuman(String sizeHuman) {

        this.sizeHuman = sizeHuman;
    }

    @Override
    public String toString() {
        return "SearchJson{" +
                "formatFlag=" + formatFlag +
                ", doubanUrl='" + doubanUrl + '\'' +
                ", fileext='" + fileext + '\'' +
                ", id=" + id +
                ", isDir=" + isDir +
                ", filename='" + filename + '\'' +
                ", format='" + format + '\'' +
                ", goodSuccess=" + goodSuccess +
                ", img='" + img + '\'' +
                ", magnet='" + magnet + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", size=" + size +
                ", sizeHuman='" + sizeHuman + '\'' +
                '}';
    }
}
