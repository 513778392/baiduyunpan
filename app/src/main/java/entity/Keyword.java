package entity;

import java.io.Serializable;

public class Keyword implements Serializable {
	
	private Integer id ;
	private String keyword ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Keyword{" +
				"id=" + id +
				", keyword='" + keyword + '\'' +
				'}';
	}
}
