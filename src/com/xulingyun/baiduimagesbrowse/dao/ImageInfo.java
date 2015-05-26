package com.xulingyun.baiduimagesbrowse.dao;

import android.graphics.Bitmap;

public class ImageInfo {
	String abs;
	String thumbnail_url;
	String thumbnail_width;
	String thumbnail_height;
	Bitmap bitmap;

	public ImageInfo(String abs, String thumbnail_url, String thumbnail_width,
			String thumbnail_height) {
		this.abs = abs;
		this.thumbnail_url = thumbnail_url;
		this.thumbnail_width = thumbnail_width;
		this.thumbnail_height = thumbnail_height;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	public String getThumbnail_width() {
		return thumbnail_width;
	}

	public void setThumbnail_width(String thumbnail_width) {
		this.thumbnail_width = thumbnail_width;
	}

	public String getThumbnail_height() {
		return thumbnail_height;
	}

	public void setThumbnail_height(String thumbnail_height) {
		this.thumbnail_height = thumbnail_height;
	}

	@Override
	public String toString() {
		return "ImageInfo [abs=" + abs + ", thumbnail_url=" + thumbnail_url
				+ ", thumbnail_width=" + thumbnail_width
				+ ", thumbnail_height=" + thumbnail_height + "]";
	}
}
