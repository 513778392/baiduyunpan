package entity;

import java.io.Serializable;

/**
 * 电影的实体
 * @author Administrator
 *
 */
public class Film implements Serializable {
	private Integer id ; //主键
	private String title ; //标题
	private String imagePath ; //图片路径
	private String tiantangUrl ; //天堂网对应的url
	private String doubanUrl ; //豆瓣地址
	private Integer tiantangId ; //天堂网的id
	private String alias ; // 又名
	private String year ; //发布年份
	private String guojia ; //国家
	private String type ; //类型
	private String yuyan ;  // 语言
	private String releaseDate ;   //发布日期
	private Integer flag ;  // 标志位 0 代表非首页   1代表首页爬过来的
	private String pingfen ; //豆瓣的评分



	public String getPingfen() {
		return pingfen;
	}
	public void setPingfen(String pingfen) {
		this.pingfen = pingfen;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getYuyan() {
		return yuyan;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setYuyan(String yuyan) {
		this.yuyan = yuyan;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGuojia() {
		return guojia;
	}
	public void setGuojia(String guojia) {
		this.guojia = guojia;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getTiantangId() {
		return tiantangId;
	}
	public void setTiantangId(Integer tiantangId) {
		this.tiantangId = tiantangId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTiantangUrl() {
		return tiantangUrl;
	}
	public void setTiantangUrl(String tiantangUrl) {
		this.tiantangUrl = tiantangUrl;
	}
	public String getDoubanUrl() {
		return doubanUrl;
	}
	public void setDoubanUrl(String doubanUrl) {
		this.doubanUrl = doubanUrl;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
