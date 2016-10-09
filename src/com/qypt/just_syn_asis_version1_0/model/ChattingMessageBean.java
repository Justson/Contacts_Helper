package com.qypt.just_syn_asis_version1_0.model;
/**
 * 
 * @author Administrator justson
 *time bean
 */
public class ChattingMessageBean {

	private String time;
	private ChattingType mType;
	private String message;

	
  public ChattingMessageBean(){
	  
  }
	
	public ChattingMessageBean(String time, ChattingType mType, String message) {
		super();
		this.time = time;
		this.mType = mType;
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setType(ChattingType m){
		this.mType=m;
	}
	public ChattingType getType(){
		return mType;
	}

	
	

}
