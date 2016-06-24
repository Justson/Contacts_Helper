package com.qypt.just_syn_asis_version1_0.model;
/**
 * 
 * @author Administrator justson
 *
 */
public class EventBussMessage {

	private int subscriber;
	private String message;
	private Object data;
	
	private EventBussMessage(Builder builder)
	{
		this.subscriber=builder.subscriber;
		this.message=builder.message;
		this.data=builder.data;
	}
	public Object getData()
	{
		return data;
	}
	public int getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(int subscriber) {
		this.subscriber = subscriber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static class Builder{
		
		private int subscriber;
		private String message;
		private Object data;
		public Builder()
		{
			
		}
		public int getSubscriber() {
			return subscriber;
		}
		public Builder setSubscriber(int subscriber) {
			this.subscriber = subscriber;
			return this;
		}
		public String getMessage() {
			return message;
		}
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public EventBussMessage build()
		{
			return new EventBussMessage(this);
		}
		public Builder setData(Object data)
		{
			this.data=data;
			return this;
		}
	}
	
}
