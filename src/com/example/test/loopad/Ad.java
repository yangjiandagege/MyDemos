package com.example.test.loopad;

public class Ad {
	private int imageId;
	private String text;
	
	public Ad(int imageId, String text) {
		super();
		this.imageId = imageId;
		this.text = text;
	}

	public final int getImageId() {
		return imageId;
	}

	public final void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}
}
