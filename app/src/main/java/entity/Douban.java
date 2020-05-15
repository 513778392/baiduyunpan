package entity;

import java.io.Serializable;

/**
 * 该Entity主要描述的豆瓣网的一些即将上映的电影
 * @author TianPeng
 *
 */
public class Douban implements Serializable {
	private Integer id ;
	private String href ;
	private String imagePath ;
	private String title ;
	private String year ;
	private String type ;
	private String guojia ;
	private String yuyan ;
	private String releaseDate ;
	private String pingfen ;
	private boolean flag;
	private String version;
	private String uVersion;

	public String getuVersion() {
		return uVersion;
	}

	public void setuVersion(String uVersion) {
		this.uVersion = uVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGuojia() {
		return guojia;
	}
	public void setGuojia(String guojia) {
		this.guojia = guojia;
	}
	public String getYuyan() {
		return yuyan;
	}
	public void setYuyan(String yuyan) {
		this.yuyan = yuyan;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getPingfen() {
		return pingfen;
	}
	public void setPingfen(String pingfen) {
		this.pingfen = pingfen;
	}

	@Override
	public String toString() {
		return "Douban{" +
				"id=" + id +
				", href='" + href + '\'' +
				", imagePath='" + imagePath + '\'' +
				", title='" + title + '\'' +
				", year='" + year + '\'' +
				", type='" + type + '\'' +
				", guojia='" + guojia + '\'' +
				", yuyan='" + yuyan + '\'' +
				", releaseDate='" + releaseDate + '\'' +
				", pingfen='" + pingfen + '\'' +
				", flag=" + flag +
				", version=" + version +
				'}';
	}
}
