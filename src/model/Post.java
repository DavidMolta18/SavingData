package model;

import java.io.Serializable;

public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text;

	public Post() {

	}

	public Post(String text) {

		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
