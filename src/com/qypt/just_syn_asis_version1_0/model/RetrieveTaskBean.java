package com.qypt.just_syn_asis_version1_0.model;

import java.io.Serializable;
//cxz      
public class RetrieveTaskBean  implements TaskBean,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String task;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}

}
