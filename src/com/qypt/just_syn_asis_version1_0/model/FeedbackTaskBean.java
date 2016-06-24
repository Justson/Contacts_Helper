package com.qypt.just_syn_asis_version1_0.model;
/**
 * 
 * @author Administrator justson
 *
 */
public class FeedbackTaskBean  implements TaskBean{

	
	private String name;
	private String title;
	private String content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
