package com.xulingyun.baiduimagesbrowse.dao;

public class Photo {
	int id;
	String kind;
	String src;
	int userId;
	String name;
	int commentCount;
	int supportCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", kind=" + kind + ", src=" + src
				+ ", userId=" + userId + ", name=" + name + ", commentCount="
				+ commentCount + ", supportCount=" + supportCount + "]";
	}

}
