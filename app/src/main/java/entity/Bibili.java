package entity;

public class Bibili {
	private Integer id ;
	private String title ;
	private String imagePath ;
	private String href ;
	private String releaseDate ;
	public Integer getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Bibili{" +
				"id=" + id +
				", title='" + title + '\'' +
				", imagePath='" + imagePath + '\'' +
				", href='" + href + '\'' +
				", releaseDate='" + releaseDate + '\'' +
				'}';
	}
}
